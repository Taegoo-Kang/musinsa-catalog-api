package com.musinsa.platform.biz.core.api.admin.brand.service;

import com.musinsa.platform.biz.core.api.admin.brand.dto.BrandDto;
import com.musinsa.platform.biz.core.api.admin.brand.service.BrandService;
import com.musinsa.platform.biz.core.api.common.exception.DuplicateException;
import com.musinsa.platform.biz.core.api.common.exception.NotFoundException;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Brand;
import com.musinsa.platform.biz.core.api.common.jpa.repository.BrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    @DisplayName("전체 브랜드 조회")
    void test_findAllBrands() {

        var brandEntity1 = Brand.builder()
                .brandName("테스트브랜드1")
                .build();

        var brandEntity2 = Brand.builder()
                .brandName("테스트브랜드2")
                .build();

        given(brandRepository.findAllBrands()).willReturn(List.of(brandEntity1, brandEntity2));

        var brandDtoList = brandService.findAllBrands();

        assertEquals(2, brandDtoList.size());
        assertEquals(brandEntity1.getBrandName(), brandDtoList.getFirst().getBrandName());
        assertEquals(brandEntity2.getBrandName(), brandDtoList.getLast().getBrandName());
    }

    @Test
    @DisplayName("브랜드 추가")
    void test_createBrand() {

        var brandEntity = Brand.builder()
                .brandName("테스트브랜드")
                .build();

        given(brandRepository.save(any(Brand.class))).willReturn(brandEntity);

        var brandDto = BrandDto.builder()
                .brandName(brandEntity.getBrandName())
                .build();

        var saved = brandService.createBrand(brandDto);

        assertEquals("Y", brandEntity.getUseYn());
        assertEquals(brandDto.getBrandName(), saved.getBrandName());
    }

    @Test
    @DisplayName("브랜드 업데이트")
    void test_updateBrand() {

        var brandEntity = Brand.builder()
                .brandName("테스트브랜드")
                .build();

        given(brandRepository.findByBrandNo(anyLong())).willReturn(Optional.of(brandEntity));

        var brandDto = BrandDto.builder()
                .brandName("업데이트브랜드")
                .build();

        var updated = brandService.updateBrand(anyLong(), brandDto);

        assertEquals(brandDto.getBrandName(), brandEntity.getBrandName());
        assertEquals(brandDto.getBrandName(), updated.getBrandName());
    }

    @Test
    @DisplayName("브랜드 삭제")
    void test_deleteBrand() {

        var brandEntity = Brand.builder().build();

        given(brandRepository.findByBrandNo(anyLong())).willReturn(Optional.of(brandEntity));

        brandService.deleteBrand(anyLong());

        assertEquals("N", brandEntity.getUseYn());
    }

    @Test
    @DisplayName("브랜드 조회 실패")
    void test_notFoundBrand() {

        given(brandRepository.findByBrandNo(anyLong())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> brandService.deleteBrand(anyLong()));
    }

    @Test
    @DisplayName("브랜드명 중복")
    void test_duplicateBrandName() {

        given(brandRepository.existsByBrandName(anyString())).willReturn(true);

        assertThrows(DuplicateException.class, () -> brandService.createBrand(BrandDto.builder()
                .brandName("중복브랜드")
                .build()));
    }
}
