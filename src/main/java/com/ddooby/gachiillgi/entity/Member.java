package com.ddooby.gachiillgi.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member
//        extends BaseUpdateEntity
{

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
    private boolean activated;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberAuthority> memberAuthoritySet = new ArrayList<>();

    public void updateAuthority(MemberAuthority memberAuthority) {
        //TODO 삭제 기능
        memberAuthoritySet.add(memberAuthority);
        memberAuthority.setMember(this);
    }
}
