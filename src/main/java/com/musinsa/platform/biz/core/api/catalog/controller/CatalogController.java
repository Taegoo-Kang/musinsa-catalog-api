package com.musinsa.platform.biz.core.api.catalog.controller;

import com.musinsa.platform.biz.core.api.catalog.dto.LowHighPriceGoodsResponse;
import com.musinsa.platform.biz.core.api.catalog.dto.BrandResponse;
import com.musinsa.platform.biz.core.api.catalog.dto.CategoriesResponse;
import com.musinsa.platform.biz.core.api.catalog.service.CatalogService;
import com.musinsa.platform.biz.core.api.common.model.PriceSortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "1. 상품 카탈로그 API")
@RequiredArgsConstructor
@RequestMapping("/api/catalog")
@RestController
public class CatalogController {

    private final CatalogService catalogService;

    @Operation(summary = "과제1. 카테고리 별 최저가 조회", description = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회")
    @GetMapping("/low/categories")
    public CategoriesResponse getLowPriceByCategories() {
        return catalogService.getCategoriesCatalog(PriceSortType.LOW);
    }

    @Operation(summary = "과제2. 최저가 브랜드 조회", description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회")
    @GetMapping("/low/brand")
    public BrandResponse getLowPriceBrand() {
        return catalogService.getBrandCatalog(PriceSortType.LOW);
    }

    @Operation(summary = "과제3. 최저, 최고 가격 브랜드 조회", description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회")
    @GetMapping("/low-high/category/{categoryName}")
    public LowHighPriceGoodsResponse getLowHighPriceCategory(@PathVariable("categoryName") String categoryName) {
        return catalogService.getLowHighPriceGoodsByCategory(categoryName);
    }
}
