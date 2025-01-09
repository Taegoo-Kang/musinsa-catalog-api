package com.musinsa.platform.biz.core.api.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CategoryGoodsVo(

    @Schema(description = "카테고리명", defaultValue = "카테고리")
    String categoryName,

    @JsonIgnore
    Long salePrice,

    @JsonProperty("salePrice")
    @Schema(description = "가격", defaultValue = "0")
    String salePriceStr

) {}
