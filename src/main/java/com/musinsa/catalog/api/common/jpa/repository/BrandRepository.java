package com.musinsa.catalog.api.common.jpa.repository;

import com.musinsa.catalog.api.common.jpa.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT b FROM Brand b WHERE b.useYn = 'Y'")
    List<Brand> findAllBrands();

    @Query("SELECT b FROM Brand b WHERE b.brandNo = :brandNo AND b.useYn = 'Y'")
    Optional<Brand> findByBrandNo(@Param("brandNo") Long brandNo);

}
