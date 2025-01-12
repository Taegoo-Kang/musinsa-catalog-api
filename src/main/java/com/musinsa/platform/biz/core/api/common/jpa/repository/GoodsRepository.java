package com.musinsa.platform.biz.core.api.common.jpa.repository;

import com.musinsa.platform.biz.core.api.common.jpa.entity.Goods;
import com.musinsa.platform.biz.core.api.common.jpa.repository.querydsl.GoodsQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsQuerydslRepository {

    @Query("""
        SELECT g
        FROM Goods g
            JOIN FETCH g.category c
            JOIN FETCH g.brand b
        WHERE g.useYn = 'Y'
            AND c.useYn = 'Y'
            AND b.useYn = 'Y'
    """)
    List<Goods> findAllGoods();

    @Query("""
        SELECT g
        FROM Goods g
            JOIN FETCH g.category c
            JOIN FETCH g.brand b
        WHERE g.goodsNo = :goodsNo
            AND g.useYn = 'Y'
            AND c.useYn = 'Y'
            AND b.useYn = 'Y'
    """)
    Optional<Goods> findByGoodsNo(@Param("goodsNo") Long goodsNo);
}
