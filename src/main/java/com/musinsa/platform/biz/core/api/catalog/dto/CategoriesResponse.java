package com.musinsa.platform.biz.core.api.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoriesResponse(

    @Schema(description = "상품 목록")
    List<GoodsVo> goodsList,

    @Schema(description = "총액", defaultValue = "0")
    String totalPrice

) {}
