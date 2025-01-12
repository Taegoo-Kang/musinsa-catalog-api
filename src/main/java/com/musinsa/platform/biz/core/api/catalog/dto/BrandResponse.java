package com.musinsa.platform.biz.core.api.catalog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BrandResponse(

    @Schema(description = "브랜드 카탈로그")
    BrandCatalogVo brandCatalog

) {}
