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
@Table(name = "brand")
@Entity
public class Brand extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_no", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long brandNo;

    @Setter
    @NotEmpty(message = "브랜드명은 필수입니다.")
    @Column(name = "brand_name", columnDefinition = "varchar(30)", nullable = false)
    private String brandName;

    @Column(name = "use_yn", columnDefinition = "char(1) default 'Y'", nullable = false)
    private String useYn;

    @Builder
    private Brand(String brandName) {
        this.brandName = brandName;
        this.useYn = "Y";
    }

    public void delete() {
        this.useYn = "N";
    }
}
