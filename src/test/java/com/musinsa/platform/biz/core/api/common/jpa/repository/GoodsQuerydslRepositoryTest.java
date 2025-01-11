package com.musinsa.platform.biz.core.api.common.jpa.repository;

import com.musinsa.platform.biz.core.api.common.config.JpaConfiguration;
import com.musinsa.platform.biz.core.api.common.config.QuerydslConfiguration;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Goods;
import com.musinsa.platform.biz.core.api.common.model.PriceSortType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Comparator;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;
import static org.junit.jupiter.api.Assertions.*;

@ImportAutoConfiguration({JpaConfiguration.class, QuerydslConfiguration.class})
@DataJpaTest
public class GoodsQuerydslRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("카테고리로 최저/최고 가격 상품 조회")
    void test_findOneByCategory() {

        // given
        /* init data by data.sql */

        // when - 최저/최고 가격 상품 조회
        var sneakers = categoryRepository.findByCategoryName("스니커즈")
                .orElse(null);
        var lowGoods = goodsRepository.findOneByCategory(sneakers, PriceSortType.LOW);
        var highGoods = goodsRepository.findOneByCategory(sneakers, PriceSortType.HIGH);

        // then
        var goodsList = goodsRepository.findAllGoods();
        var findLowGoods = goodsList.stream()
                .filter(goods -> goods.getCategory() == sneakers)
                .sorted(comparing(goods -> goods.getBrand().getBrandNo(), Comparator.reverseOrder()))
                .min(comparing(Goods::getSalePrice))
                .orElse(null);
        var findHighGoods = goodsList.stream()
                .filter(goods -> goods.getCategory() == sneakers)
                .sorted(comparing(goods -> goods.getBrand().getBrandNo(), Comparator.reverseOrder()))
                .max(comparing(Goods::getSalePrice))
                .orElse(null);

        assertNotNull(lowGoods);
        assertNotNull(highGoods);
        assertEquals(lowGoods, findLowGoods);
        assertEquals(highGoods, findHighGoods);
        assertEquals("G", lowGoods.getBrand().getBrandName());
        assertEquals("E", highGoods.getBrand().getBrandName());
        assertEquals(9000L, lowGoods.getSalePrice());
        assertEquals(9900L, highGoods.getSalePrice());

        // when - 최저가 상품, 최고가 브랜드 'E' 삭제
        lowGoods.delete();
        highGoods.getBrand().delete();
        goodsRepository.save(lowGoods);
        brandRepository.save(highGoods.getBrand());

        // 최저/최고 가격 상품 다시 조회
        goodsList = goodsRepository.findAllGoods();
        lowGoods = goodsRepository.findOneByCategory(sneakers, PriceSortType.LOW);
        highGoods = goodsRepository.findOneByCategory(sneakers, PriceSortType.HIGH);

        // then
        findLowGoods = goodsList.stream()
                .filter(goods -> goods.getCategory() == sneakers)
                .sorted(comparing(goods -> goods.getBrand().getBrandNo(), Comparator.reverseOrder()))
                .min(comparing(Goods::getSalePrice))
                .orElse(null);
        findHighGoods = goodsList.stream()
                .filter(goods -> goods.getCategory() == sneakers)
                .sorted(comparing(goods -> goods.getBrand().getBrandNo(), Comparator.reverseOrder()))
                .max(comparing(Goods::getSalePrice))
                .orElse(null);

        assertNotNull(lowGoods);
        assertNotNull(highGoods);
        assertEquals(lowGoods, findLowGoods);
        assertEquals(highGoods, findHighGoods);
        assertEquals("A", lowGoods.getBrand().getBrandName());
        assertEquals("H", highGoods.getBrand().getBrandName());
        assertEquals(9000L, lowGoods.getSalePrice());
        assertEquals(9700L, highGoods.getSalePrice());
    }

    @Test
    @DisplayName("브랜드와 카테고리로 최저/최고 가격 상품 조회")
    void test_findOneByBrandAndCategory() {

        // given
        /* init data by data.sql */

        // when
        var outer = categoryRepository.findByCategoryName("아우터").orElse(null);
        var brandC = brandRepository.findByBrandNo(3L).orElse(null);

        assert(brandC != null && outer != null);
        var outerC = goodsRepository.findOneByBrandAndCategory(brandC, outer, PriceSortType.LOW);

        // then
        assertNotNull(outerC);
        assertEquals(outerC.getBrand(), brandC);
        assertEquals(outerC.getCategory(), outer);
        assertEquals(6200L, outerC.getSalePrice());

        // when - 같은 브랜드, 카테고리 상품 추가
        goodsRepository.save(Goods.builder()
                .brand(brandC)
                .category(outer)
                .goodsName("C_아우터_추가")
                .salePrice(6000L)
                .build());

        var lowOuterC = goodsRepository.findOneByBrandAndCategory(brandC, outer, PriceSortType.LOW);
        var highOuterC = goodsRepository.findOneByBrandAndCategory(brandC, outer, PriceSortType.HIGH);

        // then
        var goodsList = goodsRepository.findAllGoods();
        var findLowGoods = goodsList.stream()
                .filter(goods -> goods.getCategory() == outer)
                .filter(goods -> goods.getBrand() == brandC)
                .min(comparing(Goods::getSalePrice))
                .orElse(null);
        var findHighGoods = goodsList.stream()
                .filter(goods -> goods.getCategory() == outer)
                .filter(goods -> goods.getBrand() == brandC)
                .max(comparing(Goods::getSalePrice))
                .orElse(null);

        assertNotNull(lowOuterC);
        assertNotNull(highOuterC);
        assertEquals(lowOuterC, findLowGoods);
        assertEquals(highOuterC, findHighGoods);
        assertEquals(outerC, highOuterC);
        assertEquals(6000L, lowOuterC.getSalePrice());
        assertEquals(6200L, highOuterC.getSalePrice());
    }

    @Test
    @DisplayName("")
    void test_findAllByCategory() {

        // given
        /* init data by data.sql */

        // when
        var top = categoryRepository.findByCategoryName("상의").orElse(null);
        var lowGoodsList = goodsRepository.findAllByCategory(top, PriceSortType.LOW);
        var highGoodsList = goodsRepository.findAllByCategory(top, PriceSortType.HIGH);

        // then
        assertNotNull(lowGoodsList);
        assertNotNull(highGoodsList);
        assertEquals(1, lowGoodsList.size());
        assertEquals(1, highGoodsList.size());
        assertTrue(lowGoodsList.stream()
                .allMatch(lowGoods -> lowGoods.getSalePrice().equals(10000L)));
        assertTrue(highGoodsList.stream()
                .allMatch(highGoods -> highGoods.getSalePrice().equals(11400L)));

        // when - 최저/최고가 상품 삭제
        lowGoodsList.forEach(Goods::delete);
        highGoodsList.forEach(Goods::delete);

        lowGoodsList = goodsRepository.findAllByCategory(top, PriceSortType.LOW);
        highGoodsList = goodsRepository.findAllByCategory(top, PriceSortType.HIGH);

        // then
        assertNotNull(lowGoodsList);
        assertNotNull(highGoodsList);
        assertEquals(1, lowGoodsList.size());
        assertEquals(2, highGoodsList.size());
        assertTrue(lowGoodsList.stream()
                .allMatch(lowGoods -> lowGoods.getSalePrice().equals(10100L)));
        assertTrue(highGoodsList.stream()
                .allMatch(highGoods -> highGoods.getSalePrice().equals(11200L)));
    }
}
