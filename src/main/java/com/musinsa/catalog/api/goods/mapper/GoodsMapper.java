package com.musinsa.catalog.api.goods.mapper;

import com.musinsa.catalog.api.common.dto.GoodsDto;
import com.musinsa.catalog.api.common.exception.NotFoundException;
import com.musinsa.catalog.api.common.jpa.entity.Goods;
import com.musinsa.catalog.api.common.jpa.repository.BrandRepository;
import com.musinsa.catalog.api.common.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GoodsMapper {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public Goods toEntity(GoodsDto dto) {
        var brand = brandRepository.findByBrandNo(dto.getBrandNo())
                .orElseThrow(() -> new NotFoundException("브랜드", dto.getBrandNo()));

        var category = categoryRepository.findByCategoryNo(dto.getCategoryNo())
                .orElseThrow(() -> new NotFoundException("카테고리", dto.getCategoryNo()));

        return Goods.builder()
                .brand(brand)
                .category(category)
                .goodsName(dto.getGoodsName())
                .salePrice(dto.getSalePrice())
                .build();
    }

    public GoodsDto toDto(Goods entity) {
        return GoodsDto.builder()
                .goodsNo(entity.getGoodsNo())
                .brandNo(entity.getBrand().getBrandNo())
                .brandName(entity.getBrand().getBrandName())
                .categoryNo(entity.getCategory().getCategoryNo())
                .categoryName(entity.getCategory().getCategoryName())
                .goodsName(entity.getGoodsName())
                .salePrice(entity.getSalePrice())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
