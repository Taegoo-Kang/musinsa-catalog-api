package com.musinsa.catalog.api.brand.mapper;

import com.musinsa.catalog.api.common.dto.BrandDto;
import com.musinsa.catalog.api.common.jpa.entity.Brand;

public interface BrandMapper {

    static Brand toEntity(BrandDto dto) {
        return Brand.builder()
                .brandName(dto.getBrandName())
                .build();
    }

    static BrandDto toDto(Brand brand) {
        return BrandDto.builder()
                .brandNo(brand.getBrandNo())
                .brandName(brand.getBrandName())
                .createdAt(brand.getCreatedAt())
                .updatedAt(brand.getUpdatedAt())
                .build();
    }
}
