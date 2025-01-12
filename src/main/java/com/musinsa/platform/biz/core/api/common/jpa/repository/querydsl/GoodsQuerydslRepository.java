package com.musinsa.platform.biz.core.api.common.jpa.repository.querydsl;

import com.musinsa.platform.biz.core.api.common.jpa.entity.Brand;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Category;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Goods;
import com.musinsa.platform.biz.core.api.common.model.PriceSortType;

import java.util.List;

public interface GoodsQuerydslRepository {

    Goods findOneByCategory(Category category, PriceSortType priceSortType);

    Goods findOneByBrandAndCategory(Brand brand, Category category, PriceSortType priceSortType);

    List<Goods> findAllByCategory(Category category, PriceSortType priceSortType);
}
