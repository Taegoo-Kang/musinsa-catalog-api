package com.musinsa.platform.biz.core.api.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record LowPriceBrandResponse(

    @Schema(description = "최저가 브랜드")
    BrandCatalogVo lowPriceBrand

) {}
