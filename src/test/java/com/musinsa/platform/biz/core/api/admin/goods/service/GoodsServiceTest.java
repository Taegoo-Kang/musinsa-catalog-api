package com.musinsa.platform.biz.core.api.admin.goods.service;

import com.musinsa.platform.biz.core.api.admin.goods.service.GoodsService;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Brand;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Category;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Goods;
import com.musinsa.platform.biz.core.api.common.jpa.repository.BrandRepository;
import com.musinsa.platform.biz.core.api.common.jpa.repository.CategoryRepository;
import com.musinsa.platform.biz.core.api.common.jpa.repository.GoodsRepository;
import com.musinsa.platform.biz.core.api.admin.goods.dto.GoodsDto;
import com.musinsa.platform.biz.core.api.admin.goods.mapper.GoodsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class GoodsServiceTest {

    @Mock
    private GoodsRepository goodsRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private CategoryRepository categoryRepository;

    private GoodsService goodsService;

    @BeforeEach
    void setUp() {
        if (this.goodsService == null) {
            var goodsMapper = new GoodsMapper(brandRepository, categoryRepository);
            goodsService = new GoodsService(goodsMapper, goodsRepository, brandRepository, categoryRepository);
        }
    }

    @Test
    @DisplayName("전체 상품 조회")
    void test_findAllGoods() {

        var brandEntity = Brand.builder()
                .brandName("테스트브랜드")
                .build();

        var categoryEntity = Category.builder()
                .categoryName("테스트카테고리")
                .build();

        var goodsEntity1 = Goods.builder()
                .brand(brandEntity)
                .category(categoryEntity)
                .goodsName("테스트상품1")
                .salePrice(1000L)
                .build();

        var goodsEntity2 = Goods.builder()
                .brand(brandEntity)
                .category(categoryEntity)
                .goodsName("테스트상품2")
                .salePrice(2000L)
                .build();

        given(goodsRepository.findAllGoods()).willReturn(List.of(goodsEntity1, goodsEntity2));

        var goodsDtoList = goodsService.findAllGoods();

        assertEquals(2, goodsDtoList.size());
        assertEquals(goodsEntity1.getGoodsName(), goodsDtoList.getFirst().getGoodsName());
        assertEquals(goodsEntity1.getSalePrice(), goodsDtoList.getFirst().getSalePrice());
        assertEquals(goodsEntity2.getGoodsName(), goodsDtoList.getLast().getGoodsName());
        assertEquals(goodsEntity2.getSalePrice(), goodsDtoList.getLast().getSalePrice());
    }

    @Test
    @DisplayName("상품 추가")
    void test_saveGoods() {

        var brandEntity = Brand.builder()
                .brandName("테스트브랜드")
                .build();

        var categoryEntity = Category.builder()
                .categoryName("테스트카테고리")
                .build();

        var goodsEntity = Goods.builder()
                .brand(brandEntity)
                .category(categoryEntity)
                .goodsName("테스트상품")
                .salePrice(1000L)
                .build();

        given(brandRepository.findByBrandNo(anyLong())).willReturn(Optional.of(brandEntity));
        given(categoryRepository.findByCategoryNo(anyLong())).willReturn(Optional.of(categoryEntity));
        given(goodsRepository.save(any(Goods.class))).willReturn(goodsEntity);

        var goodsDto = GoodsDto.builder()
                .brandNo(1L)
                .brandName(brandEntity.getBrandName())
                .categoryNo(1L)
                .categoryName(categoryEntity.getCategoryName())
                .goodsName(goodsEntity.getGoodsName())
                .salePrice(goodsEntity.getSalePrice())
                .build();

        var saved = goodsService.createGoods(goodsDto);

        assertEquals("Y", goodsEntity.getUseYn());
        assertEquals(goodsDto.getGoodsName(), saved.getGoodsName());
        assertEquals(goodsDto.getSalePrice(), saved.getSalePrice());
        assertEquals(goodsDto.getBrandName(), saved.getBrandName());
        assertEquals(goodsDto.getCategoryName(), saved.getCategoryName());
        assertEquals(goodsDto.getBrandName(), brandEntity.getBrandName());
        assertEquals(goodsDto.getCategoryName(), categoryEntity.getCategoryName());
    }

    @Test
    @DisplayName("상품 업데이트")
    void test_updateGoods() {

        var brandEntity = Brand.builder()
                .brandName("테스트브랜드")
                .build();

        var categoryEntity = Category.builder()
                .categoryName("테스트카테고리")
                .build();

        var goodsEntity = Goods.builder()
                .brand(brandEntity)
                .category(categoryEntity)
                .goodsName("테스트상품")
                .salePrice(1000L)
                .build();

        var updateBrand = Brand.builder()
                .brandName("업데이트브랜드")
                .build();

        var updateCategory = Category.builder()
                .categoryName("업데이트카테고리")
                .build();

        given(goodsRepository.findByGoodsNo(anyLong())).willReturn(Optional.of(goodsEntity));
        given(brandRepository.findByBrandNo(anyLong())).willReturn(Optional.of(updateBrand));
        given(categoryRepository.findByCategoryNo(anyLong())).willReturn(Optional.of(updateCategory));

        var goodsDto = GoodsDto.builder()
                .brandNo(1L)
                .brandName(updateBrand.getBrandName())
                .categoryNo(1L)
                .categoryName(updateCategory.getCategoryName())
                .goodsName("업데이트상품")
                .salePrice(2000L)
                .build();

        var updated = goodsService.updateGoods(anyLong(), goodsDto);

        assertEquals(goodsDto.getBrandName(), updateBrand.getBrandName());
        assertEquals(goodsDto.getCategoryName(), updateCategory.getCategoryName());
        assertEquals(goodsDto.getGoodsName(), updated.getGoodsName());
        assertEquals(goodsDto.getSalePrice(), updated.getSalePrice());
    }

    @Test
    @DisplayName("상품 삭제")
    void test_deleteGoods() {

        var goodsEntity = Goods.builder().build();

        given(goodsRepository.findByGoodsNo(anyLong())).willReturn(Optional.of(goodsEntity));

        goodsService.deleteGoods(anyLong());

        assertEquals("N", goodsEntity.getUseYn());
    }
}
