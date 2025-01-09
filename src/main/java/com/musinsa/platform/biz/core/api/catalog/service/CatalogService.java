package com.musinsa.platform.biz.core.api.catalog.service;

import com.musinsa.platform.biz.core.api.catalog.dto.*;
import com.musinsa.platform.biz.core.api.common.jpa.repository.BrandRepository;
import com.musinsa.platform.biz.core.api.common.jpa.repository.CategoryRepository;
import com.musinsa.platform.biz.core.api.common.jpa.repository.GoodsRepository;
import com.musinsa.platform.biz.core.api.common.utils.NumberUtils;
import com.querydsl.core.types.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;

@RequiredArgsConstructor
@Service
public class CatalogService {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final GoodsRepository goodsRepository;

    /**
     * 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회
     *
     * @return 카테고리 별 최저가격
     */
    public LowPriceCategoriesResponse getLowPriceCategories() {

        var lowPriceGoodsList = categoryRepository.findAll().stream()
                .map(category -> {

                    var goods = goodsRepository.findLowPriceByCategory(category.getCategoryNo());

                    return GoodsVo.builder()
                            .categoryName(category.getCategoryName())
                            .brandName(goods.getBrand().getBrandName())
                            .salePrice(goods.getSalePrice())
                            .salePriceStr(NumberUtils.formatDecimal(goods.getSalePrice()))
                            .build();

                })
                .toList();

        var totalPrice = lowPriceGoodsList.stream()
                .mapToLong(GoodsVo::salePrice)
                .sum();

        return LowPriceCategoriesResponse.builder()
                .lowPriceGoodsList(lowPriceGoodsList)
                .totalPrice(NumberUtils.formatDecimal(totalPrice))
                .build();
    }

    /**
     * 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회
     *
     * @return 최저가격의 단일 브랜드
     */
    public LowPriceBrandResponse getLowPriceBrand() {

        var categoryList = categoryRepository.findAll();

        var lowPriceBrand = brandRepository.findAll().stream()
                .map(brand -> {
                    var lowPriceCategoryGoodsList = categoryList.stream()
                            .map(category -> {
                                var goods = goodsRepository.findLowPriceByBrandAndCategory(brand.getBrandNo(), category.getCategoryNo());

                                return CategoryGoodsVo.builder()
                                        .categoryName(category.getCategoryName())
                                        .salePrice(goods.getSalePrice())
                                        .salePriceStr(NumberUtils.formatDecimal(goods.getSalePrice()))
                                        .build();
                            })
                            .toList();

                    var totalPrice = lowPriceCategoryGoodsList.stream()
                            .mapToLong(CategoryGoodsVo::salePrice)
                            .sum();

                    return BrandCatalogVo.builder()
                            .brandName(brand.getBrandName())
                            .categoryGoodsList(lowPriceCategoryGoodsList)
                            .totalPrice(NumberUtils.formatDecimal(totalPrice))
                            .build();
                })
                .min(Comparator.comparing(BrandCatalogVo::totalPrice))
                .orElse(null);

        return LowPriceBrandResponse.builder()
                .lowPriceBrand(lowPriceBrand)
                .build();
    }

    /**
     * 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회
     *
     * @param categoryName 카테고리 이름
     * @return 최저, 최고 가격 브랜드
     */
    public LowHighPriceBrandResponse getCategoryLowHighPrice(String categoryName) {
        var optCategory = categoryRepository.findByCategoryName(categoryName);

        if (optCategory.isEmpty()) {
            return LowHighPriceBrandResponse.builder()
                    .categoryName(categoryName)
                    .lowPriceBrandList(Collections.emptyList())
                    .highPriceBrandList(Collections.emptyList())
                    .build();
        }

        var category = optCategory.get();

        var lowPriceBrands = goodsRepository.findByCategory(category.getCategoryNo(), Order.ASC).stream()
                .map(goods -> BrandGoodsVo.builder()
                        .brandName(goods.getBrand().getBrandName())
                        .salePrice(goods.getSalePrice())
                        .salePriceStr(NumberUtils.formatDecimal(goods.getSalePrice()))
                        .build())
                .toList();

        var highPriceBrands = goodsRepository.findByCategory(category.getCategoryNo(), Order.DESC).stream()
                .map(goods -> BrandGoodsVo.builder()
                        .brandName(goods.getBrand().getBrandName())
                        .salePrice(goods.getSalePrice())
                        .salePriceStr(NumberUtils.formatDecimal(goods.getSalePrice()))
                        .build())
                .toList();

        return LowHighPriceBrandResponse.builder()
                .categoryName(categoryName)
                .lowPriceBrandList(lowPriceBrands)
                .highPriceBrandList(highPriceBrands)
                .build();
    }
}
