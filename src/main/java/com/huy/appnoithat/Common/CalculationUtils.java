package com.huy.appnoithat.Common;

public class CalculationUtils {
    public static long round(double input) {
        long i = (long) Math.ceil(input);
        return ((i + 100000 - 1) / 100000) * 100000;
    };
}
