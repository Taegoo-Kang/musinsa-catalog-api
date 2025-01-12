package com.musinsa.platform.biz.core.api.common.utils;

import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;

@UtilityClass
public class NumberUtils {

    private final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public static String formatDecimal(Long number) {
        if (number == null) {
            return "0";
        }

        return decimalFormat.format(number);
    }
}
