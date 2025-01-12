package com.musinsa.platform.biz.core.api.catalog.service;

import com.musinsa.platform.biz.core.api.catalog.dto.*;
import com.musinsa.platform.biz.core.api.common.jpa.repository.BrandRepository;
import com.musinsa.platform.biz.core.api.common.jpa.repository.CategoryRepository;
import com.musinsa.platform.biz.core.api.common.jpa.repository.GoodsRepository;
import com.musinsa.platform.biz.core.api.common.model.PriceSortType;
import com.musinsa.platform.biz.core.api.common.utils.NumberUtils;
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
     * 1. 카테고리 별 최저/최고 가격 브랜드와 상품 가격, 총액을 조회
     *
     * @return 카테고리 별 최저/최고 가격의 상품
     */
    public CategoriesResponse getCategoriesCatalog(PriceSortType priceSortType) {

        var goodsList = categoryRepository.findAllCategories().stream()
                .map(category -> {

                    var goods = goodsRepository.findOneByCategory(category, priceSortType);

                    return GoodsVo.builder()
                            .categoryName(category.getCategoryName())
                            .brandName(goods.getBrand().getBrandName())
                            .salePrice(goods.getSalePrice())
                            .salePriceStr(NumberUtils.formatDecimal(goods.getSalePrice()))
                            .build();

                })
                .toList();

        var totalPrice = goodsList.stream()
                .mapToLong(GoodsVo::salePrice)
                .sum();

        return CategoriesResponse.builder()
                .goodsList(goodsList)
                .totalPrice(totalPrice)
                .totalPriceStr(NumberUtils.formatDecimal(totalPrice))
                .build();
    }

    /**
     * 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저/최고 가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회
     *
     * @return 최저/최고 가격의 단일 브랜드
     */
    public BrandResponse getBrandCatalog(PriceSortType priceSortType) {

        // 최저가 또는 최고가 정렬
        var comparator = switch (priceSortType) {
            case LOW -> Comparator.comparing(BrandCatalogVo::totalPrice);
            case HIGH -> Comparator.comparing(BrandCatalogVo::totalPrice, Comparator.reverseOrder());
        };

        // 브랜드별 모든 카테고리 상품 조회
        var categoryList = categoryRepository.findAllCategories();
        var brandCatalog = brandRepository.findAllBrands().stream()
                .map(brand -> {
                    // 브랜드별 모든 카테고리 상품 조회
                    var categoryGoodsList = categoryList.stream()
                            .map(category -> {
                                var goods = goodsRepository.findOneByBrandAndCategory(brand, category, priceSortType);

                                return GoodsVo.builder()
                                        .categoryName(category.getCategoryName())
                                        .salePrice(goods.getSalePrice())
                                        .salePriceStr(NumberUtils.formatDecimal(goods.getSalePrice()))
                                        .build();
                            })
                            .toList();

                    var totalPrice = categoryGoodsList.stream()
                            .mapToLong(GoodsVo::salePrice)
                            .sum();

                    return BrandCatalogVo.builder()
                            .brandName(brand.getBrandName())
                            .categoryGoodsList(categoryGoodsList)
                            .totalPrice(totalPrice)
                            .totalPriceStr(NumberUtils.formatDecimal(totalPrice))
                            .build();

                })
                .min(comparator)
                .orElse(null);

        return BrandResponse.builder()
                .brandCatalog(brandCatalog)
                .build();
    }

    /**
     * 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회
     *
     * @param categoryName 카테고리 이름
     * @return 최저, 최고 가격의 상품
     */
    public LowHighPriceGoodsResponse getLowHighPriceGoodsByCategory(String categoryName) {
        // 카테고리 조회
        var optCategory = categoryRepository.findByCategoryName(categoryName);
        // 카테고리 없음
        if (optCategory.isEmpty()) {
            return LowHighPriceGoodsResponse.builder()
                    .categoryName(categoryName)
                    .lowPriceGoodsList(Collections.emptyList())
                    .highPriceGoodsList(Collections.emptyList())
                    .build();
        }

        var category = optCategory.get();

        // 최저가 상품
        var lowPriceGoods = goodsRepository.findAllByCategory(category, PriceSortType.LOW).stream()
                .map(goods -> GoodsVo.builder()
                        .brandName(goods.getBrand().getBrandName())
                        .salePrice(goods.getSalePrice())
                        .salePriceStr(NumberUtils.formatDecimal(goods.getSalePrice()))
                        .build())
                .toList();

        // 최고가 상품
        var highPriceGoods = goodsRepository.findAllByCategory(category, PriceSortType.HIGH).stream()
                .map(goods -> GoodsVo.builder()
                        .brandName(goods.getBrand().getBrandName())
                        .salePrice(goods.getSalePrice())
                        .salePriceStr(NumberUtils.formatDecimal(goods.getSalePrice()))
                        .build())
                .toList();

        return LowHighPriceGoodsResponse.builder()
                .categoryName(categoryName)
                .lowPriceGoodsList(lowPriceGoods)
                .highPriceGoodsList(highPriceGoods)
                .build();
    }
}
