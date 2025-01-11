package com.musinsa.platform.biz.core.api.common.jpa.repository;

import com.musinsa.platform.biz.core.api.common.config.JpaConfiguration;
import com.musinsa.platform.biz.core.api.common.config.QuerydslConfiguration;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Brand;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Category;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Goods;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@ImportAutoConfiguration({JpaConfiguration.class, QuerydslConfiguration.class})
@DataJpaTest
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("상품 저장")
    void test_saveGoods() {

        // given
        var category = Category.builder()
                .categoryName("테스트카테고리")
                .build();
        categoryRepository.save(category);

        var brand = Brand.builder()
                .brandName("테스트브랜드")
                .build();
        brandRepository.save(brand);

        var goods = Goods.builder()
                .category(category)
                .brand(brand)
                .goodsName("테스트상품")
                .salePrice(10000L)
                .build();

        // when
        var saved = goodsRepository.save(goods);

        // then
        assertNotNull(saved.getGoodsNo());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());

        assertEquals(category, saved.getCategory());
        assertEquals(brand, saved.getBrand());
        assertEquals(goods.getGoodsName(), saved.getGoodsName());
        assertEquals(goods.getSalePrice(), saved.getSalePrice());
        assertEquals("Y", saved.getUseYn());
    }

    @Test
    @DisplayName("상품 전체 조회")
    void test_findAllGoods() {

        // given
        var category = Category.builder()
                .categoryName("테스트카테고리")
                .build();
        categoryRepository.save(category);

        var brand = Brand.builder()
                .brandName("테스트브랜드")
                .build();
        brandRepository.save(brand);

        var goods = Goods.builder()
                .category(category)
                .brand(brand)
                .goodsName("테스트상품")
                .salePrice(10000L)
                .build();

        goodsRepository.save(goods);

        // when
        var goodsList = goodsRepository.findAllGoods();
        var saved  = goodsList.getLast();

        // then
        assertEquals(category, saved.getCategory());
        assertEquals(brand, saved.getBrand());
        assertEquals(goods.getGoodsName(), saved.getGoodsName());
        assertTrue(goodsList.stream()
                .allMatch(find -> "Y".equals(goods.getUseYn())));
    }

    @Test
    @DisplayName("상품 번호로 조회")
    void test_findByGoodsNo() {

        // given
        var category = Category.builder()
                .categoryName("테스트카테고리")
                .build();
        categoryRepository.save(category);

        var brand = Brand.builder()
                .brandName("테스트브랜드")
                .build();
        brandRepository.save(brand);

        var goods = Goods.builder()
                .category(category)
                .brand(brand)
                .goodsName("테스트상품")
                .salePrice(10000L)
                .build();

        goodsRepository.save(goods);

        // when
        var saved = goodsRepository.findByGoodsNo(goods.getGoodsNo())
                .orElse(null);

        // then
        assert(saved != null);
        assertNotNull(saved.getGoodsNo());
        assertEquals(goods.getGoodsNo(), saved.getGoodsNo());
        assertEquals(category, saved.getCategory());
        assertEquals(brand, saved.getBrand());
        assertEquals(goods.getGoodsName(), saved.getGoodsName());
        assertEquals(goods.getSalePrice(), saved.getSalePrice());
        assertEquals("Y", saved.getUseYn());
    }
}
