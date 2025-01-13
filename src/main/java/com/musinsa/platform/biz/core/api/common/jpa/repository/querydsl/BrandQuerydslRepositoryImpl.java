package com.musinsa.platform.biz.core.api.common.jpa.repository.querydsl;

import com.musinsa.platform.biz.core.api.common.jpa.entity.QBrand;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandQuerydslRepositoryImpl implements BrandQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsByBrandName(String brandName) {
        QBrand brand = QBrand.brand;

        var find = jpaQueryFactory
                .selectFrom(brand)
                .where(brand.brandName.eq(brandName)
                        .and(brand.useYn.eq("Y")))
                .fetchFirst();

        return find != null;
    }
}
