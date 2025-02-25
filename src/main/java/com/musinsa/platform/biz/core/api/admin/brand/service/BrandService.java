package com.musinsa.platform.biz.core.api.admin.brand.service;

import com.musinsa.platform.biz.core.api.admin.brand.mapper.BrandMapper;
import com.musinsa.platform.biz.core.api.admin.brand.dto.BrandDto;
import com.musinsa.platform.biz.core.api.common.exception.DuplicateException;
import com.musinsa.platform.biz.core.api.common.exception.NotFoundException;
import com.musinsa.platform.biz.core.api.common.jpa.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandService {

    private final BrandRepository brandRepository;

    /**
     * 전체 브랜드 조회
     *
     * @return 브랜드 목록
     */
    public List<BrandDto> findAllBrands() {
        return brandRepository.findAllBrands().stream()
                .map(BrandMapper::toDto)
                .toList();
    }

    /**
     * 브랜드 추가
     *
     * @param brandDto 브랜드 정보
     * @return 추가 된 브랜드 정보
     */
    @Transactional
    public BrandDto createBrand(BrandDto brandDto) {
        // 브랜드명 중복 체크
        this.checkDuplicateBrandName(brandDto.getBrandName());

        var brand = brandRepository.save(BrandMapper.toEntity(brandDto));

        return BrandMapper.toDto(brand);
    }

    /**
     * 브랜드 업데이트
     *
     * @param brandDto 브랜드 정보
     * @return 업데이트 된 브랜드 정보
     */
    @Transactional
    public BrandDto updateBrand(Long brandNo, BrandDto brandDto) {
        var brand = brandRepository.findByBrandNo(brandNo)
                .orElseThrow(() -> new NotFoundException("브랜드", brandNo));

        if (!brand.getBrandName().equals(brandDto.getBrandName())) {
            // 브랜드명 중복 체크
            this.checkDuplicateBrandName(brandDto.getBrandName());

            brand.setBrandName(brandDto.getBrandName());
        }

        return BrandMapper.toDto(brand);
    }

    /**
     * 브랜드 삭제
     * 
     * @param brandNo 삭제 할 브랜드 번호
     */
    @Transactional
    public void deleteBrand(Long brandNo) {
        var brand = brandRepository.findByBrandNo(brandNo)
                .orElseThrow(() -> new NotFoundException("브랜드", brandNo));

        brand.delete();
    }

    /**
     * 같은 브랜드명이 존재하는지 체크
     *
     * @param brandName 브랜드명
     */
    private void checkDuplicateBrandName(String brandName) {
        if (brandRepository.existsByBrandName(brandName)) {
            throw new DuplicateException("브랜드명", brandName);
        }
    }
}
