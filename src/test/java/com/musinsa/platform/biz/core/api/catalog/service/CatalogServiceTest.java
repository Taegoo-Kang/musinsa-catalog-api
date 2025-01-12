package com.musinsa.platform.biz.core.api.catalog.service;

import com.musinsa.platform.biz.core.api.catalog.dto.GoodsVo;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Brand;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Category;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Goods;
import com.musinsa.platform.biz.core.api.common.jpa.repository.BrandRepository;
import com.musinsa.platform.biz.core.api.common.jpa.repository.CategoryRepository;
import com.musinsa.platform.biz.core.api.common.jpa.repository.GoodsRepository;
import com.musinsa.platform.biz.core.api.common.model.PriceSortType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private GoodsRepository goodsRepository;

    @InjectMocks
    private CatalogService catalogService;

    @Test
    @DisplayName("카테고리 별 상품 조회")
    void test_getCategoriesCatalog() {

        var categoryEntity1 = Category.builder()
                .categoryName("카테고리1")
                .build();

        var categoryEntity2 = Category.builder()
                .categoryName("카테고리2")
                .build();

        given(categoryRepository.findAllCategories()).willReturn(List.of(categoryEntity1, categoryEntity2));

        var brandEntity = Brand.builder()
                .brandName("테스트브랜드")
                .build();

        var goodsEntity1 = Goods.builder()
                .category(categoryEntity1)
                .brand(brandEntity)
                .goodsName("테스트상품1")
                .salePrice(1000L)
                .build();

        var goodsEntity2 = Goods.builder()
                .category(categoryEntity2)
                .brand(brandEntity)
                .goodsName("테스트상품2")
                .salePrice(2000L)
                .build();

        given(goodsRepository.findOneByCategory(eq(categoryEntity1), any(PriceSortType.class))).willReturn(goodsEntity1);
        given(goodsRepository.findOneByCategory(eq(categoryEntity2), any(PriceSortType.class))).willReturn(goodsEntity2);

        var response = catalogService.getCategoriesCatalog(PriceSortType.LOW);

        verify(goodsRepository, times(2)).findOneByCategory(any(Category.class), any(PriceSortType.class));

        Long totalPrice = Stream.of(goodsEntity1, goodsEntity2)
                .mapToLong(Goods::getSalePrice).sum();

        assertEquals(totalPrice, response.totalPrice());

        assertEquals(goodsEntity1.getBrand().getBrandName(), response.goodsList().getFirst().brandName());
        assertEquals(goodsEntity1.getCategory().getCategoryName(), response.goodsList().getFirst().categoryName());
        assertEquals(goodsEntity1.getSalePrice(), response.goodsList().getFirst().salePrice());

        assertEquals(goodsEntity2.getBrand().getBrandName(), response.goodsList().getLast().brandName());
        assertEquals(goodsEntity2.getCategory().getCategoryName(), response.goodsList().getLast().categoryName());
        assertEquals(goodsEntity2.getSalePrice(), response.goodsList().getLast().salePrice());
    }

    @Test
    @DisplayName("단일 브랜드 상품 조회")
    void test_getBrandCatalog() {

        var categoryEntity1 = Category.builder()
                .categoryName("카테고리1")
                .build();

        var categoryEntity2 = Category.builder()
                .categoryName("카테고리2")
                .build();

        given(categoryRepository.findAllCategories()).willReturn(List.of(categoryEntity1, categoryEntity2));

        var brandEntity1 = Brand.builder()
                .brandName("브랜드1")
                .build();

        var brandEntity2 = Brand.builder()
                .brandName("브랜드2")
                .build();

        given(brandRepository.findAllBrands()).willReturn(List.of(brandEntity1, brandEntity2));

        var goodsEntity1 = Goods.builder()
                .brand(brandEntity1)
                .category(categoryEntity1)
                .goodsName("상품1")
                .salePrice(1000L)
                .build();

        var goodsEntity2 = Goods.builder()
                .brand(brandEntity1)
                .category(categoryEntity2)
                .goodsName("상품2")
                .salePrice(10000L)
                .build();

        var goodsEntity3 = Goods.builder()
                .brand(brandEntity2)
                .category(categoryEntity1)
                .goodsName("상품3")
                .salePrice(3000L)
                .build();

        var goodsEntity4 = Goods.builder()
                .brand(brandEntity2)
                .category(categoryEntity2)
                .goodsName("상품4")
                .salePrice(5000L)
                .build();

        var goodsEntity5 = Goods.builder()
                .brand(brandEntity1)
                .category(categoryEntity1)
                .goodsName("상품5")
                .salePrice(2000L)
                .build();

        var goodsEntity6 = Goods.builder()
                .brand(brandEntity1)
                .category(categoryEntity2)
                .goodsName("상품6")
                .salePrice(20000L)
                .build();

        var goodsEntity7 = Goods.builder()
                .brand(brandEntity2)
                .category(categoryEntity1)
                .goodsName("상품7")
                .salePrice(6000L)
                .build();

        var goodsEntity8 = Goods.builder()
                .brand(brandEntity2)
                .category(categoryEntity2)
                .goodsName("상품8")
                .salePrice(10000L)
                .build();

        given(goodsRepository.findOneByBrandAndCategory(brandEntity1, categoryEntity1, PriceSortType.LOW)).willReturn(goodsEntity1);
        given(goodsRepository.findOneByBrandAndCategory(brandEntity1, categoryEntity2, PriceSortType.LOW)).willReturn(goodsEntity2);
        given(goodsRepository.findOneByBrandAndCategory(brandEntity2, categoryEntity1, PriceSortType.LOW)).willReturn(goodsEntity3);
        given(goodsRepository.findOneByBrandAndCategory(brandEntity2, categoryEntity2, PriceSortType.LOW)).willReturn(goodsEntity4);
        given(goodsRepository.findOneByBrandAndCategory(brandEntity1, categoryEntity1, PriceSortType.HIGH)).willReturn(goodsEntity5);
        given(goodsRepository.findOneByBrandAndCategory(brandEntity1, categoryEntity2, PriceSortType.HIGH)).willReturn(goodsEntity6);
        given(goodsRepository.findOneByBrandAndCategory(brandEntity2, categoryEntity1, PriceSortType.HIGH)).willReturn(goodsEntity7);
        given(goodsRepository.findOneByBrandAndCategory(brandEntity2, categoryEntity2, PriceSortType.HIGH)).willReturn(goodsEntity8);

        var lowResponse = catalogService.getBrandCatalog(PriceSortType.LOW);
        var highResponse = catalogService.getBrandCatalog(PriceSortType.HIGH);

        var lowTotalPrice = Stream.of(goodsEntity3, goodsEntity4)
                .mapToLong(Goods::getSalePrice).sum();
        var highTotalPrice = Stream.of(goodsEntity5, goodsEntity6)
                .mapToLong(Goods::getSalePrice).sum();

        assertNotNull(lowResponse.brandCatalog());
        assertEquals(brandEntity2.getBrandName(), lowResponse.brandCatalog().brandName());
        assertEquals(brandEntity1.getBrandName(), highResponse.brandCatalog().brandName());
        assertEquals(lowTotalPrice, lowResponse.brandCatalog().totalPrice());
        assertEquals(highTotalPrice, highResponse.brandCatalog().totalPrice());
    }

    @Test
    @DisplayName("카테고리 최저/최고 상품 조회")
    void test_getLowHighPriceGoodsByCategory() {

        var categoryEntity = Category.builder()
                .categoryName("카테고리")
                .build();

        given(categoryRepository.findByCategoryName(anyString())).willReturn(Optional.of(categoryEntity));

        var brandEntity1 = Brand.builder()
                .brandName("브랜드1")
                .build();

        var brandEntity2 = Brand.builder()
                .brandName("브랜드2")
                .build();

        var goodsEntity1 = Goods.builder()
                .brand(brandEntity1)
                .category(categoryEntity)
                .goodsName("상품1")
                .salePrice(1000L)
                .build();

        var goodsEntity2 = Goods.builder()
                .brand(brandEntity1)
                .category(categoryEntity)
                .goodsName("상품2")
                .salePrice(10000L)
                .build();

        var goodsEntity3 = Goods.builder()
                .brand(brandEntity2)
                .category(categoryEntity)
                .goodsName("상품3")
                .salePrice(3000L)
                .build();

        var goodsEntity4 = Goods.builder()
                .brand(brandEntity2)
                .category(categoryEntity)
                .goodsName("상품4")
                .salePrice(5000L)
                .build();

        var lowGoodsList = List.of(goodsEntity3, goodsEntity4);
        var highGoodsList = List.of(goodsEntity1, goodsEntity2);

        given(goodsRepository.findAllByCategory(any(Category.class), eq(PriceSortType.LOW))).willReturn(lowGoodsList);
        given(goodsRepository.findAllByCategory(any(Category.class), eq(PriceSortType.HIGH))).willReturn(highGoodsList);

        var response = catalogService.getLowHighPriceGoodsByCategory(categoryEntity.getCategoryName());

        assertEquals(categoryEntity.getCategoryName(), response.categoryName());
        assertEquals(lowGoodsList.stream().mapToLong(Goods::getSalePrice).sum()
                , response.lowPriceGoodsList().stream().mapToLong(GoodsVo::salePrice).sum());
        assertEquals(highGoodsList.stream().mapToLong(Goods::getSalePrice).sum()
                , response.highPriceGoodsList().stream().mapToLong(GoodsVo::salePrice).sum());
    }

    @Test
    @DisplayName("카테고리 최저/최고 상품 조회 - 카테고리 없음")
    void test_getLowHighPriceGoodsByCategory_nonCategory() {

        given(categoryRepository.findByCategoryName(anyString())).willReturn(Optional.empty());

        var response = catalogService.getLowHighPriceGoodsByCategory(anyString());

        assertTrue(response.lowPriceGoodsList().isEmpty());
        assertTrue(response.highPriceGoodsList().isEmpty());
    }
}
