package com.musinsa.platform.biz.core.api.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record LowHighPriceBrandResponse(

    @Schema(description = "카테고리명", defaultValue = "카테고리")
    String categoryName,

    @Schema(description = "최저가 브랜드")
    List<BrandGoodsVo> lowPriceBrandList,

    @Schema(description = "최고가 브랜드")
    List<BrandGoodsVo> highPriceBrandList

) {}
