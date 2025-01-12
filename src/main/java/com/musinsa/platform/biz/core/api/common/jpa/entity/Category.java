package com.musinsa.platform.biz.core.api.common.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@ToString
@Getter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @NotEmpty(message = "카테고리명은 필수입니다.")
    @Column(name = "category_name", columnDefinition = "varchar(30)", nullable = false)
    private String categoryName;

    @Column(name = "use_yn", columnDefinition = "char(1) default 'Y'", nullable = false)
    private String useYn;

    @Builder
    private Category(String categoryName) {
        this.categoryName = categoryName;
        this.useYn = "Y";
    }
}
