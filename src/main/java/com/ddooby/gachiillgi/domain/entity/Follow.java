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
    @JoinColumn(name = "follow_user_id")
    private User followUser;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_user_id")
    private User followedUser;

}