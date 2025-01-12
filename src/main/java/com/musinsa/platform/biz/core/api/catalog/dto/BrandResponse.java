package com.musinsa.platform.biz.core.api.catalog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BrandResponse(

    @Schema(description = "최저가")
    BrandCatalogVo lowBrandCatalog,

    @Schema(description = "최고가")
    BrandCatalogVo highBrandCatalog

) {}
