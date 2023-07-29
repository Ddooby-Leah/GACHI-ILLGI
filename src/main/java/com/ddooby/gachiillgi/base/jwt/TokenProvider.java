package com.ddooby.gachiillgi.base.jwt;

import com.ddooby.gachiillgi.base.enums.exception.AuthErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.ddooby.gachiillgi.base.enums.TokenEnum.AUTHORITIES_KEY;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private final String tokenSecretKey;
    private final String mailTokenSecretKey;
    private final long tokenValidityInMilliseconds;
    private final long authLinkValidityInSeconds;
    private Key tokenKey;
    private Key mailTokenKey;

    public TokenProvider(
            @Value("${jwt.token-secret-key}") String tokenSecretKey,
            @Value("${jwt.mail-token-secret-key}") String mailTokenSecretKey,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
            @Value("${jwt.link-validity-in-seconds}") long authLinkValidityInSeconds) {
        this.tokenSecretKey = tokenSecretKey;
        this.mailTokenSecretKey = mailTokenSecretKey;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.authLinkValidityInSeconds = authLinkValidityInSeconds;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(tokenSecretKey);
        byte[] mailKeyBytes = Decoders.BASE64.decode(mailTokenSecretKey);
        this.tokenKey = Keys.hmacShaKeyFor(keyBytes);
        this.mailTokenKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY.getName(), authorities)
                .signWith(tokenKey, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createTemporaryLink(String email) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusSeconds(authLinkValidityInSeconds);

        Date issuedAt = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        Date expirationDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(mailTokenKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String verifyTemporaryLink(String link) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(mailTokenKey)
                    .build()
                    .parseClaimsJws(link)
                    .getBody();

            String email = claims.getSubject();
            Date expiration = claims.getExpiration();

            LocalDateTime expirationTime = LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault());
            if (LocalDateTime.now().isAfter(expirationTime)) {
                throw new BizException(AuthErrorCodeEnum.EXPIRED_MAIL_LINK);
            }
            return email;
        } catch (Exception e) {
            // 유효하지 않은 링크 처리할 내용
            throw new BizException(AuthErrorCodeEnum.INVALID_MAIL_LINK);
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(tokenKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        log.debug(claims.toString());

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY.getName()).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(tokenKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | io.jsonwebtoken.security.SignatureException e) {
            throw new BizException(AuthErrorCodeEnum.INVALID_TOKEN_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new BizException(AuthErrorCodeEnum.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new BizException(AuthErrorCodeEnum.INVALID_TOKEN);
        }
    }
}
