package com.musinsa.platform.biz.core.api.common.jpa.repository;

import com.musinsa.platform.biz.core.api.common.config.JpaConfiguration;
import com.musinsa.platform.biz.core.api.common.config.QuerydslConfiguration;
import com.musinsa.platform.biz.core.api.common.jpa.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@ImportAutoConfiguration({JpaConfiguration.class, QuerydslConfiguration.class})
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 저장")
    void test_saveCategory() {

        // given
        var category = Category.builder()
                .categoryName("테스트카테고리")
                .build();

        // when
        var saved = categoryRepository.save(category);

        // then
        assertNotNull(saved.getCategoryNo());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());

        assertEquals(category.getCategoryName(), saved.getCategoryName());
        assertEquals("Y", saved.getUseYn());
    }

    @Test
    @DisplayName("카테고리 전체 조회")
    void test_findAllCategory() {

        // given
        var category = Category.builder()
                .categoryName("테스트카테고리")
                .build();

        categoryRepository.save(category);

        // when
        var categoryList = categoryRepository.findAllCategories();
        var saved = categoryList.getLast();

        // then
        assertEquals(category.getCategoryName(), saved.getCategoryName());
        assertTrue(categoryList.stream()
                .allMatch(find -> "Y".equals(find.getUseYn())));
    }

    @Test
    @DisplayName("카테고리 번호로 조회")
    void test_findByCategoryNo() {

        // given
        var category = Category.builder()
                .categoryName("테스트카테고리")
                .build();

        categoryRepository.save(category);

        // when
        var saved = categoryRepository.findByCategoryNo(category.getCategoryNo())
                .orElse(null);

        // then
        assert(saved != null);
        assertNotNull(saved.getCategoryNo());
        assertEquals(category.getCategoryNo(), saved.getCategoryNo());
        assertEquals(category.getCategoryName(), saved.getCategoryName());
        assertEquals("Y", saved.getUseYn());
    }

    @Test
    @DisplayName("카테고리 이름으로 조회")
    void test_findByCategoryName() {

        // given
        var category = Category.builder()
                .categoryName("테스트카테고리")
                .build();

        categoryRepository.save(category);

        // when
        var saved = categoryRepository.findByCategoryName(category.getCategoryName())
                .orElse(null);

        // then
        assert(saved != null);
        assertNotNull(saved.getCategoryNo());
        assertEquals(category.getCategoryNo(), saved.getCategoryNo());
        assertEquals(category.getCategoryName(), saved.getCategoryName());
        assertEquals("Y", saved.getUseYn());
    }
}
