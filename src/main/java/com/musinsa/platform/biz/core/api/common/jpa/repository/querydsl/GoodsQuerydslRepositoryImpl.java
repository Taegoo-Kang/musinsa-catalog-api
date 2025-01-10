package com.musinsa.platform.biz.core.api.common.jpa.repository.querydsl;

import com.musinsa.platform.biz.core.api.common.jpa.entity.Goods;
import com.musinsa.platform.biz.core.api.common.jpa.entity.QGoods;
import com.musinsa.platform.biz.core.api.common.model.PriceSortType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class GoodsQuerydslRepositoryImpl implements GoodsQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Goods findOneByCategory(Long categoryNo, PriceSortType priceSortType) {
        var goods = QGoods.goods;

        var salePriceOrder = PriceSortType.LOW.equals(priceSortType)
                ? goods.salePrice.asc() : goods.salePrice.desc();

        return jpaQueryFactory
                .selectFrom(goods)
                .where(goods.category.categoryNo.eq(categoryNo)
                        .and(goods.category.useYn.eq("Y"))
                        .and(goods.brand.useYn.eq("Y"))
                        .and(goods.useYn.eq("Y")))
                .orderBy(salePriceOrder, goods.brand.brandNo.desc())
                .fetchFirst();
    }

    @Override
    public Goods findOneByBrandAndCategory(Long brandNo, Long categoryNo, PriceSortType priceSortType) {
        var goods = QGoods.goods;

        var salePriceOrder = PriceSortType.LOW.equals(priceSortType)
                ? goods.salePrice.asc() : goods.salePrice.desc();

        return jpaQueryFactory
                .selectFrom(goods)
                .where(goods.brand.brandNo.eq(brandNo)
                        .and(goods.category.categoryNo.eq(categoryNo))
                        .and(goods.brand.useYn.eq("Y"))
                        .and(goods.category.useYn.eq("Y"))
                        .and(goods.useYn.eq("Y")))
                .orderBy(salePriceOrder)
                .fetchFirst();
    }

    @Override
    public List<Goods> findAllByCategory(Long categoryNo, PriceSortType priceSortType) {
        var goods = QGoods.goods;

        var salePriceOrder = PriceSortType.LOW.equals(priceSortType)
                ? goods.salePrice.asc() : goods.salePrice.desc();

        // 카테고리의 최저가 또는 최고가 조회
        Long salePrice = jpaQueryFactory
                .select(goods.salePrice)
                .from(goods)
                .where(goods.category.categoryNo.eq(categoryNo)
                        .and(goods.category.useYn.eq("Y"))
                        .and(goods.useYn.eq("Y")))
                .orderBy(salePriceOrder)
                .fetchFirst();

        if (salePrice == null) {
            return Collections.emptyList();
        }

        return jpaQueryFactory
                .selectFrom(goods)
                .where(goods.category.categoryNo.eq(categoryNo)
                        .and(goods.salePrice.eq(salePrice))
                        .and(goods.category.useYn.eq("Y"))
                        .and(goods.useYn.eq("Y")))
                .orderBy(goods.brand.brandNo.desc())
                .fetch();
    }
}
