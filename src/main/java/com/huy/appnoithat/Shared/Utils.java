package com.huy.appnoithat.Shared;

import java.util.TreeMap;

public class Utils {
    public static boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    public static class RomanNumber {
        private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();
        static {

            map.put(1000, "M");
            map.put(900, "CM");
            map.put(500, "D");
            map.put(400, "CD");
            map.put(100, "C");
            map.put(90, "XC");
            map.put(50, "L");
            map.put(40, "XL");
            map.put(10, "X");
            map.put(9, "IX");
            map.put(5, "V");
            map.put(4, "IV");
            map.put(1, "I");

        }
        public static String toRoman(int number) {
            int l =  map.floorKey(number);
            if ( number == l ) {
                return map.get(number);
            }
            return map.get(l) + toRoman(number-l);
        }

        public static boolean isRoman(String roman) {
            return roman.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
        }
    }
}
