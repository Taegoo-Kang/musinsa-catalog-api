package com.musinsa.platform.biz.core.api.common.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
@Table(name = "category"
    , indexes = {
        @Index(name = "idx_category_name", columnList = "category_name")
})
@Entity
public class Category extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long categoryNo;

    @NotEmpty
    @Column(name = "category_name", columnDefinition = "varchar(30)", nullable = false)
    private String categoryName;

    @Column(name = "use_yn", columnDefinition = "char(1) default 'Y'", nullable = false)
    private String useYn;
}
