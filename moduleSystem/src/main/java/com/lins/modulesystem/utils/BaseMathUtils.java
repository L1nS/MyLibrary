package com.lins.modulesystem.utils;

import java.math.BigDecimal;

public class BaseMathUtils {

    /**
     * 除法运算
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 保留的小数位数
     * @return
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if (v2 == 0)
            return v1;
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
