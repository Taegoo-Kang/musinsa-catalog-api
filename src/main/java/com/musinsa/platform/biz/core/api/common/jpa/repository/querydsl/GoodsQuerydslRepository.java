package com.musinsa.platform.biz.core.api.common.jpa.repository.querydsl;

import com.musinsa.platform.biz.core.api.common.jpa.entity.Goods;
import com.querydsl.core.types.Order;

import java.util.List;

public interface GoodsQuerydslRepository {

    Goods findLowPriceByCategory(Long categoryNo);

    Goods findLowPriceByBrandAndCategory(Long brandNo, Long categoryNo);

    List<Goods> findByCategory(Long categoryNo, Order order);
}
