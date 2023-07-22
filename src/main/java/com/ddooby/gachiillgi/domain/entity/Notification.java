package com.ddooby.gachiillgi.domain.entity;

import com.ddooby.gachiillgi.base.entity.BaseInsertEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Notification")
@Table(name = "notification")
public class Notification extends BaseInsertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "method", nullable = false, length = 50)
    private String method;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_comment_like_id")
    private UserCommentLike userCommentLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_comment_tag_id")
    private UserCommentTag userCommentTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_diary_like_id")
    private UserDiaryLike userDiaryLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_diary_tag_id")
    private UserDiaryTag userDiaryTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_id")
    private Follow follow;
}