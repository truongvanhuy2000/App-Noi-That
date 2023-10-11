package com.huy.appnoithat.Common;

public class CalculationUtils {
    public static long round(double input) {
        long i = (long) Math.ceil(input);
        return ((i + 1000000 - 1) / 1000000) * 1000000;
    };
}
