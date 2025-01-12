package com.musinsa.platform.biz.core.api.goods.controller;

import com.musinsa.platform.biz.core.api.goods.dto.GoodsDto;
import com.musinsa.platform.biz.core.api.common.dto.Response;
import com.musinsa.platform.biz.core.api.goods.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "3. 상품 관리 API")
@RequiredArgsConstructor
@RequestMapping("/api/goods")
@RestController
public class GoodsController {

    private final GoodsService goodsService;

    @Operation(summary = "전체 상품 조회")
    @GetMapping
    public Response<List<GoodsDto>> getAllGoods() {
        var result = goodsService.findAllGoods();

        return Response.success(result);
    }

    @Operation(summary = "상품 추가")
    @PostMapping
    public Response<GoodsDto> createGoods(@RequestBody @Valid GoodsDto dto) {
        var result = goodsService.createGoods(dto);

        return Response.success(result);
    }

    @Operation(summary = "상품 업데이트")
    @PutMapping("/{goodsNo}")
    public Response<GoodsDto> updateGoods(@PathVariable("goodsNo") Long goodsNo, @RequestBody @Valid GoodsDto dto) {
        var result = goodsService.updateGoods(goodsNo, dto);

        return Response.success(result);
    }

    @Operation(summary = "상품 삭제")
    @DeleteMapping("/{goodsNo}")
    public Response<Void> deleteGoods(@PathVariable("goodsNo") Long goodsNo) {
        goodsService.deleteGoods(goodsNo);

        return Response.success(null);
    }
}
