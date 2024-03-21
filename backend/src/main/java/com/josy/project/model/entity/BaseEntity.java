package com.josy.project.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    protected LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected LocalDate updatedAt;

    @Override
    @SuppressWarnings("com.haulmont.jpb.EqualsDoesntCheckParameterClass")
    public final boolean equals(Object object) {
        return EqualsAndHashCodeUtils.checkEquality(this, object);
    }

    @Override
    public final int hashCode() {
        return EqualsAndHashCodeUtils.getHashCode(this);
    }

}

