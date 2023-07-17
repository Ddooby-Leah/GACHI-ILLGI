package com.ddooby.gachiillgi.domain.entity;

import com.ddooby.gachiillgi.base.entity.BaseUpdateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "DiaryImage")
@Table(name = "diary_image")
public class DiaryImage extends BaseUpdateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diray_image_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "diary_detail_id", nullable = false)
    private DiaryDetail diaryDetail;

}