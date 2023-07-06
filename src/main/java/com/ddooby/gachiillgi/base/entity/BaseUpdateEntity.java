package com.ddooby.gachiillgi.base.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseUpdateEntity {

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @Column(nullable = false)
    private String modifiedBy;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Date();
        this.modifiedAt = this.createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedAt = new Date();
    }

}
