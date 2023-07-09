package com.ddooby.gachiillgi.domain.entity;

import com.ddooby.gachiillgi.base.entity.BaseUpdateEntity;
import com.ddooby.gachiillgi.base.enums.UserStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseUpdateEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "activated")
    @Enumerated(EnumType.STRING)
    private UserStatusEnum activated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<UserAuthority> userAuthoritySet = new HashSet<>();

    public void updateAuthority(UserAuthority userAuthority) {
        //TODO 삭제 기능
        userAuthoritySet.add(userAuthority);
        userAuthority.setUser(this);
    }
}
