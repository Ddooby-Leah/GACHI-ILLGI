package com.ddooby.gachiillgi.domain.entity;

import com.ddooby.gachiillgi.base.entity.BaseUpdateEntity;
import com.ddooby.gachiillgi.base.enums.UserStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
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

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "activated")
    @Enumerated(EnumType.STRING)
    private UserStatusEnum activated;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Size(max = 10)
    @NotNull
    @Column(name = "sex", nullable = false, length = 10)
    private String sex;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Size(max = 100)
    @Column(name = "profile_image", length = 100)
    private String profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<UserAuthority> userAuthoritySet = new HashSet<>();

    public void updateAuthority(UserAuthority userAuthority) {
        //TODO 삭제 기능
        userAuthoritySet.add(userAuthority);
        userAuthority.setUser(this);
    }
}
