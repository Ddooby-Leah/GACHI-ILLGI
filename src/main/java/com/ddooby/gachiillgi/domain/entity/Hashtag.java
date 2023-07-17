package com.ddooby.gachiillgi.domain.entity;

import com.ddooby.gachiillgi.base.entity.BaseInsertEntity;
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
@Entity(name = "Hashtag")
@Table(name = "hashtag")
public class Hashtag extends BaseInsertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

}