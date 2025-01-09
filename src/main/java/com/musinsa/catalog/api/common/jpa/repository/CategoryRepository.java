package com.musinsa.catalog.api.common.jpa.repository;

import com.musinsa.catalog.api.common.jpa.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.categoryNo = :categoryNo AND c.useYn = 'Y'")
    Optional<Category> findByCategoryNo(@Param("categoryNo") Long categoryNo);
}
