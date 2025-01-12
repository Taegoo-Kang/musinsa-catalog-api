package com.musinsa.platform.biz.core.api.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberUtilsTest {

    @Test
    @DisplayName("null")
    void test_null() {

        Long number = null;
        String formattedNumber = NumberUtils.formatDecimal(number);

        assertEquals("0", formattedNumber);
    }

    @Test
    @DisplayName("천단위 미만")
    void test_underThousand() {

        Long number = 999L;
        String formattedNumber = NumberUtils.formatDecimal(number);

        assertEquals(String.valueOf(number), formattedNumber);
    }

    @Test
    @DisplayName("천단위 콤마")
    void test_comma() {

        Long number = 1000L;
        String formattedNumber = NumberUtils.formatDecimal(number);

        assertEquals("1,000", formattedNumber);
    }

    @Test
    @DisplayName("백만단위 콤마")
    void test_millionComma() {

        Long number = 1000000L;
        String formattedNumber = NumberUtils.formatDecimal(number);

        assertEquals("1,000,000", formattedNumber);
    }
}
