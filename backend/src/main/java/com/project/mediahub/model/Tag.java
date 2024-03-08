package com.project.mediahub.model;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {
    @Column(name = "label", nullable = false)
    private String label;

    @ManyToMany
    private Set<Notice> notices;

}
