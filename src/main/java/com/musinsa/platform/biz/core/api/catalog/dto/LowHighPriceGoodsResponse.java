package com.musinsa.platform.biz.core.api.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record LowHighPriceGoodsResponse(

    @Schema(description = "카테고리명", defaultValue = "카테고리")
    String categoryName,

    @Schema(description = "최저가 상품")
    List<GoodsVo> lowPriceGoodsList,

    @Schema(description = "최고가 상품")
    List<GoodsVo> highPriceGoodsList

) {}
