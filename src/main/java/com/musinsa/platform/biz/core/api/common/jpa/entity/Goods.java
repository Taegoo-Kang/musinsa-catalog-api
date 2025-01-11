package com.musinsa.platform.biz.core.api.common.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@ToString
@Getter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "goods"
    , indexes = {
        @Index(name = "idx_sale_price", columnList = "sale_price")
})
@Entity
public class Goods extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_no", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long goodsNo;

    @Setter
    @NotNull(message = "브랜드 정보는 필수입니다.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_no", nullable = false)
    private Brand brand;

    @Setter
    @NotNull(message = "카테고리 정보는 필수입니다.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_no", nullable = false)
    private Category category;

    @Setter
    @NotEmpty(message = "상품명은 필수입니다.")
    @Column(name = "goods_name", columnDefinition = "varchar(100)", nullable = false)
    private String goodsName;

    @Setter
    @NotNull(message = "상품 가격은 필수입니다.")
    @Column(name = "sale_price", columnDefinition = "bigint", nullable = false)
    private Long salePrice;

    @Column(name = "use_yn", columnDefinition = "char(1) default 'Y'", nullable = false)
    private String useYn;

    @Builder
    private Goods(Brand brand, Category category, String goodsName, Long salePrice) {
        this.brand = brand;
        this.category = category;
        this.goodsName = goodsName;
        this.salePrice = salePrice;
        this.useYn = "Y";
    }

    public void delete() {
        this.useYn = "N";
    }
}
