package com.josy.project.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Upload extends BaseEntity {
    @Column(name = "filename", nullable = false, unique = true)
    private String filename;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
}
