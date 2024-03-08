package com.project.mediahub.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {
    @Column(name = "label", nullable = false)
    private String label;

    @ManyToMany
    @ToString.Exclude
    private Set<Notice> notices;

}
