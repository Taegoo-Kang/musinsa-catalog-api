package com.musinsa.platform.biz.core.api.common.jpa.repository.querydsl;

import com.musinsa.platform.biz.core.api.common.jpa.entity.Goods;
import com.musinsa.platform.biz.core.api.common.jpa.entity.QGoods;
import com.querydsl.core.types.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class GoodsQuerydslRepositoryImpl implements GoodsQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Goods findLowPriceByCategory(Long categoryNo) {
        var goods = QGoods.goods;

        return jpaQueryFactory
                .selectFrom(goods)
                .where(goods.category.categoryNo.eq(categoryNo)
                        .and(goods.category.useYn.eq("Y"))
                        .and(goods.brand.useYn.eq("Y"))
                        .and(goods.useYn.eq("Y")))
                .orderBy(goods.salePrice.asc(), goods.brand.brandNo.desc())
                .fetchFirst();
    }

    @Override
    public Goods findLowPriceByBrandAndCategory(Long brandNo, Long categoryNo) {
        var goods = QGoods.goods;

        return jpaQueryFactory
                .selectFrom(goods)
                .where(goods.brand.brandNo.eq(brandNo)
                        .and(goods.category.categoryNo.eq(categoryNo))
                        .and(goods.brand.useYn.eq("Y"))
                        .and(goods.category.useYn.eq("Y"))
                        .and(goods.useYn.eq("Y")))
                .orderBy(goods.salePrice.asc())
                .fetchFirst();
    }

    @Override
    public List<Goods> findByCategory(Long categoryNo, Order order) {
        var goods = QGoods.goods;

        var orderBy = Order.ASC.equals(order) ? goods.salePrice.asc() : goods.salePrice.desc();

        // 카테고리의 최저가 또는 최고가 조회
        Long salePrice = jpaQueryFactory
                .select(goods.salePrice)
                .from(goods)
                .where(goods.category.categoryNo.eq(categoryNo)
                        .and(goods.category.useYn.eq("Y"))
                        .and(goods.useYn.eq("Y")))
                .orderBy(orderBy)
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
                .fetch();
    }
}
