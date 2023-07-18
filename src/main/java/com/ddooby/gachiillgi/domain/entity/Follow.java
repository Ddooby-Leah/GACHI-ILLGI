package com.ddooby.gachiillgi.domain.entity;

import com.ddooby.gachiillgi.base.entity.BaseInsertEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "Follow")
@Table(name = "follow")
public class Follow extends BaseInsertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "follower_id")
    private User follower;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id")
    private User followee;

    @Builder.Default
    @Column(name = "is_delete")
    private Boolean isDelete = false;

    public void setFollower(User follower) {
        if (this.follower!= null) {
            this.follower.getFollowingList().remove(this);
        }

        this.follower = follower;
        if (!follower.getFollowingList().contains(this)) {
            follower.getFollowingList().add(this);
        }
    }

    public void setFollowee(User followee) {
        if (this.followee!= null) {
            this.followee.getFollowerList().remove(this);
        }

        this.followee = followee;
        if (!followee.getFollowerList().contains(this)) {
            followee.getFollowerList().add(this);
        }
    }
}