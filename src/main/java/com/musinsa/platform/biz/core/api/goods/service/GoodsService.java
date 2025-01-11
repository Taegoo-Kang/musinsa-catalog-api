package com.musinsa.platform.biz.core.api.goods.service;

import com.musinsa.platform.biz.core.api.goods.dto.GoodsDto;
import com.musinsa.platform.biz.core.api.common.exception.NotFoundException;
import com.musinsa.platform.biz.core.api.common.jpa.repository.BrandRepository;
import com.musinsa.platform.biz.core.api.common.jpa.repository.CategoryRepository;
import com.musinsa.platform.biz.core.api.common.jpa.repository.GoodsRepository;
import com.musinsa.platform.biz.core.api.goods.mapper.GoodsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GoodsService {

    private final GoodsMapper goodsMapper;
    private final GoodsRepository goodsRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 전체 상품 조회
     *
     * @return 상품 목록
     */
    public List<GoodsDto> findAllGoods() {
        return goodsRepository.findAllGoods().stream()
                .map(goodsMapper::toDto)
                .toList();
    }

    /**
     * 상품 추가
     *
     * @param goodsDto 상품 정보
     * @return 추가 된 상품 정보
     */
    @Transactional
    public GoodsDto createGoods(GoodsDto goodsDto) {
        var goods = goodsRepository.save(goodsMapper.toEntity(goodsDto));

        return goodsMapper.toDto(goods);
    }

    /**
     * 상품 업데이트
     *
     * @param goodsDto 상품 정보
     * @return 업데이트 된 상품 정보
     */
    @Transactional
    public GoodsDto updateGoods(Long goodsNo, GoodsDto goodsDto) {
        var goods = goodsRepository.findByGoodsNo(goodsNo)
                .orElseThrow(() -> new NotFoundException("상품", goodsNo));

        var brand = brandRepository.findByBrandNo(goodsDto.getBrandNo())
                .orElseThrow(() -> new NotFoundException("브랜드", goodsDto.getBrandNo()));

        var category = categoryRepository.findByCategoryNo(goodsDto.getCategoryNo())
                .orElseThrow(() -> new NotFoundException("카테고리", goodsDto.getCategoryNo()));

        goods.setBrand(brand);
        goods.setCategory(category);
        goods.setGoodsName(goodsDto.getGoodsName());
        goods.setSalePrice(goodsDto.getSalePrice());

        return goodsMapper.toDto(goods);
    }

    /**
     * 상품 삭제
     *
     * @param goodsNo 삭제 할 상품 번호
     */
    @Transactional
    public void deleteGoods(Long goodsNo) {
        var goods = goodsRepository.findByGoodsNo(goodsNo)
                .orElseThrow(() -> new NotFoundException("상품", goodsNo));

        goods.delete();
    }
}
