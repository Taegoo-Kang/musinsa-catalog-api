package com.musinsa.platform.biz.core.api.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BrandGoodsVo(

    @Schema(description = "브랜드명", defaultValue = "브랜드")
    String brandName,

    @JsonIgnore
    Long salePrice,

    @JsonProperty("salePrice")
    @Schema(description = "가격", defaultValue = "0")
    String salePriceStr

) {}
