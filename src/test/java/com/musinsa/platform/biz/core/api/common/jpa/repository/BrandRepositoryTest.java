package com.musinsa.platform.biz.core.api.common.jpa.repository;

import com.musinsa.platform.biz.core.api.common.config.JpaConfiguration;
import com.musinsa.platform.biz.core.api.common.config.QuerydslConfiguration;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@ImportAutoConfiguration({JpaConfiguration.class, QuerydslConfiguration.class})
@DataJpaTest
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("브랜드 저장")
    void test_saveBrand() {

        // given
        var brand = Brand.builder()
                .brandName("테스트브랜드")
                .build();

        // when
        var saved = brandRepository.save(brand);

        // then
        assertNotNull(saved.getBrandNo());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());

        assertEquals(brand.getBrandName(), saved.getBrandName());
        assertEquals("Y", saved.getUseYn());
    }

    @Test
    @DisplayName("브랜드 전체 조회")
    void test_findAllBrands() {

        // given
        var brand = Brand.builder()
                .brandName("테스트브랜드")
                .build();

        brandRepository.save(brand);

        // when
        var brandList = brandRepository.findAllBrands();
        var saved  = brandList.getLast();

        // then
        assertEquals(brand.getBrandName(), saved.getBrandName());
        assertTrue(brandList.stream()
                .allMatch(find -> "Y".equals(brand.getUseYn())));
    }

    @Test
    @DisplayName("브랜드 번호로 조회")
    void test_findByBrandNo() {

        // given
        var brand = Brand.builder()
                .brandName("테스트브랜드")
                .build();

        brandRepository.save(brand);

        // when
        var saved = brandRepository.findByBrandNo(brand.getBrandNo())
                .orElse(null);

        // then
        assert(saved != null);
        assertNotNull(saved.getBrandNo());
        assertEquals(brand.getBrandNo(), saved.getBrandNo());
        assertEquals(brand.getBrandName(), saved.getBrandName());
        assertEquals("Y", saved.getUseYn());
    }

    @Test
    @DisplayName("브랜드명 중복 체크")
    void test_checkBrandNameDuplicate() {

        // given
        String existBrandName = "A";
        String nonExistBrandName = "Z";

        // when
        boolean exist = brandRepository.existsByBrandName(existBrandName);
        boolean nonExist = brandRepository.existsByBrandName(nonExistBrandName);

        // then
        assertTrue(exist);
        assertFalse(nonExist);
    }
}
