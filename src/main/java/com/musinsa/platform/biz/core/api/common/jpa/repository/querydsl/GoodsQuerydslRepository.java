package com.musinsa.platform.biz.core.api.common.jpa.repository.querydsl;

import com.musinsa.platform.biz.core.api.common.jpa.entity.Goods;
import com.musinsa.platform.biz.core.api.common.model.PriceSortType;

import java.util.List;

public interface GoodsQuerydslRepository {

    Goods findOneByCategory(Long categoryNo, PriceSortType priceSortType);

    Goods findOneByBrandAndCategory(Long brandNo, Long categoryNo, PriceSortType priceSortType);

    List<Goods> findAllByCategory(Long categoryNo, PriceSortType priceSortType);
}
