package com.musinsa.platform.biz.core.api.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record BrandCatalogVo(

    @Schema(description = "브랜드명", defaultValue = "브랜드")
    String brandName,

    @Schema(description = "카테고리 상품 목록")
    List<GoodsVo> categoryGoodsList,

    @JsonIgnore
    Long totalPrice,

    @Schema(description = "총액", defaultValue = "0")
    @JsonProperty("totalPrice")
    String totalPriceStr

) {}
