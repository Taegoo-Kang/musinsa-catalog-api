package com.musinsa.catalog.api.common.jpa.repository;

import com.musinsa.catalog.api.common.jpa.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("SELECT g FROM Goods g WHERE g.useYn = 'Y'")
    List<Goods> findAllGoods();

    @Query("SELECT g FROM Goods g WHERE g.goodsNo = :goodsNo AND g.useYn = 'Y'")
    Optional<Goods> findByGoodsNo(@Param("goodsNo") Long goodsNo);
}
