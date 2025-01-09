package com.musinsa.catalog.api.common.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_no", nullable = false)
    private Brand brand;

    @Setter
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_no", nullable = false)
    private Category category;

    @Setter
    @NotEmpty
    @Column(name = "goods_name", columnDefinition = "varchar(100)", nullable = false)
    private String goodsName;

    @Setter
    @Min(1)
    @NotNull
    @Column(name = "sale_price", columnDefinition = "bigint", nullable = false)
    private Long salePrice;

    @Setter
    @Column(name = "use_yn", columnDefinition = "char(1) default 'Y'", nullable = false)
    private String useYn;

    @Builder
    private Goods(Brand brand, Category category, String goodsName, Long salePrice) {
        this.brand = brand;
        this.category = category;
        this.goodsName = goodsName;
        this.salePrice = salePrice;
    }
}
