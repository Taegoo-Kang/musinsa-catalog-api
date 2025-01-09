package com.musinsa.platform.biz.core.api.common.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AuditEntity {

    @CreatedDate
    @Column(name = "create_dt", columnDefinition = "datetime default CURRENT_TIMESTAMP", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "update_dt", columnDefinition = "datetime default CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

}
