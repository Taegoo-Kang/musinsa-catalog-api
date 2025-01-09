package com.musinsa.platform.biz.core.api.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GoodsVo (

    @Schema(description = "카테고리명", defaultValue = "카테고리")
    String categoryName,

    @Schema(description = "브랜드명", defaultValue = "브랜드")
    String brandName,

    @JsonIgnore
    Long salePrice,

    @Schema(description = "가격", defaultValue = "0")
    @JsonProperty("salePrice")
    String salePriceStr

) {}
