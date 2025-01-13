package com.musinsa.platform.biz.core.api.admin.brand.mapper;

import com.musinsa.platform.biz.core.api.admin.brand.dto.BrandDto;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Brand;

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
