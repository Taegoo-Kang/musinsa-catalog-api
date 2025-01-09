package com.musinsa.platform.biz.core.api.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record BrandCatalogVo(

    @Schema(description = "브랜드명", defaultValue = "브랜드")
    String brandName,

    @Schema(description = "카테고리 목록")
    List<CategoryGoodsVo> categoryGoodsList,

    @Schema(description = "총액", defaultValue = "0")
    String totalPrice

) {}
