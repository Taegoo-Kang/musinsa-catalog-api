package com.musinsa.platform.biz.core.api.common.jpa.repository.querydsl;

public interface BrandQuerydslRepository {

    boolean existsByBrandName(String brandName);
}
