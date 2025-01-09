package com.musinsa.platform.biz.core.api.common.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@Table(name = "brand")
@Entity
public class Brand extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_no", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long brandNo;

    @Setter
    @NotEmpty
    @Column(name = "brand_name", columnDefinition = "varchar(30)", nullable = false)
    private String brandName;

    @Setter
    @Column(name = "use_yn", columnDefinition = "char(1) default 'Y'", nullable = false)
    private String useYn;

    @Builder
    private Brand(String brandName) {
        this.brandName = brandName;
    }
}
