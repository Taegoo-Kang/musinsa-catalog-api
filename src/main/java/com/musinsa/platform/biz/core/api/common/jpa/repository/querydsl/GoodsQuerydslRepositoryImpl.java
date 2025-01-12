package com.musinsa.platform.biz.core.api.common.jpa.repository.querydsl;

import com.musinsa.platform.biz.core.api.common.jpa.entity.*;
import com.musinsa.platform.biz.core.api.common.model.PriceSortType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class GoodsQuerydslRepositoryImpl implements GoodsQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Goods findOneByCategory(Category category, PriceSortType priceSortType) {
        var goods = QGoods.goods;

        var salePriceOrder = PriceSortType.LOW.equals(priceSortType)
                ? goods.salePrice.asc() : goods.salePrice.desc();

        return jpaQueryFactory
                .selectFrom(goods)
                    .join(goods.category, QCategory.category)
                    .join(goods.brand, QBrand.brand)
                .where(goods.category.eq(category)
                    .and(goods.useYn.eq("Y"))
                    .and(goods.category.useYn.eq("Y"))
                    .and(goods.brand.useYn.eq("Y")))
                .orderBy(salePriceOrder, goods.brand.brandNo.desc())
                .fetchJoin()
                .fetchFirst();
    }

    @Override
    public Goods findOneByBrandAndCategory(Brand brand, Category category, PriceSortType priceSortType) {
        var goods = QGoods.goods;

        // 과제에서는 상품이 하나이지만 해당 카테고리, 브랜드의 상품이 여러개일 수 있음을 가정
        var salePriceOrder = PriceSortType.LOW.equals(priceSortType)
                ? goods.salePrice.asc() : goods.salePrice.desc();

        return jpaQueryFactory
                .selectFrom(goods)
                        .join(goods.category, QCategory.category)
                        .join(goods.brand, QBrand.brand)
                .where(goods.brand.eq(brand)
                        .and(goods.category.eq(category))
                        .and(goods.brand.useYn.eq("Y"))
                        .and(goods.category.useYn.eq("Y"))
                        .and(goods.useYn.eq("Y")))
                .orderBy(salePriceOrder)
                .fetchJoin()
                .fetchFirst();
    }

    @Override
    public List<Goods> findAllByCategory(Category category, PriceSortType priceSortType) {
        var goods = QGoods.goods;

        var salePriceOrder = PriceSortType.LOW.equals(priceSortType)
                ? goods.salePrice.asc() : goods.salePrice.desc();

        // 카테고리의 최저가 또는 최고가 조회
        Long salePrice = jpaQueryFactory
                .select(goods.salePrice)
                .from(goods)
                        .join(goods.category, QCategory.category)
                .where(goods.category.eq(category)
                        .and(goods.category.useYn.eq("Y"))
                        .and(goods.useYn.eq("Y")))
                .orderBy(salePriceOrder)
                .fetchFirst();

        if (salePrice == null) {
            return Collections.emptyList();
        }

        return jpaQueryFactory
                .selectFrom(goods)
                        .join(goods.category, QCategory.category)
                        .join(goods.brand, QBrand.brand)
                .where(goods.category.eq(category)
                        .and(goods.salePrice.eq(salePrice))
                        .and(goods.category.useYn.eq("Y"))
                        .and(goods.useYn.eq("Y")))
                .orderBy(goods.brand.brandNo.desc())
                .fetchJoin()
                .fetch();
    }
}
