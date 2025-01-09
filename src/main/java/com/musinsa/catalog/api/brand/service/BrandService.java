package com.musinsa.catalog.api.brand.service;

import com.musinsa.catalog.api.brand.mapper.BrandMapper;
import com.musinsa.catalog.api.common.dto.BrandDto;
import com.musinsa.catalog.api.common.exception.NotFoundException;
import com.musinsa.catalog.api.common.jpa.repository.BrandRepository;
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

        brand.setBrandName(brandDto.getBrandName());

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

        brand.setUseYn("N");
    }
}
