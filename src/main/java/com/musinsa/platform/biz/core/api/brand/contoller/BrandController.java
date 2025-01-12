package com.musinsa.platform.biz.core.api.brand.contoller;

import com.musinsa.platform.biz.core.api.brand.service.BrandService;
import com.musinsa.platform.biz.core.api.brand.dto.BrandDto;
import com.musinsa.platform.biz.core.api.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2. 브랜드 관리 API")
@RequiredArgsConstructor
@RequestMapping("/api/brand")
@RestController
public class BrandController {

    private final BrandService brandService;

    @Operation(summary = "전체 브랜드 조회")
    @GetMapping
    public Response<List<BrandDto>> getAllBrands() {
        var result = brandService.findAllBrands();

        return Response.success(result);
    }

    @Operation(summary = "브랜드 추가")
    @PostMapping
    public Response<BrandDto> createBrand(@RequestBody @Valid BrandDto brand) {
        var result = brandService.createBrand(brand);

        return Response.success(result);
    }

    @Operation(summary = "브랜드 업데이트")
    @PutMapping("/{brandNo}")
    public Response<BrandDto> updateBrand(@PathVariable("brandNo") Long brandNo, @RequestBody @Valid BrandDto brand) {
        var result = brandService.updateBrand(brandNo, brand);

        return Response.success(result);
    }

    @Operation(summary = "브랜드 삭제")
    @DeleteMapping("/{brandNo}")
    public Response<Void> deleteBrand(@PathVariable("brandNo") Long brandNo) {
        brandService.deleteBrand(brandNo);

        return Response.success(null);
    }
}
