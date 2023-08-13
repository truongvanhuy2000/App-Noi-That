package com.huy.appnoithat.Shared;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Utils {
    public static boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    public static class RomanNumber {
        public static String toRoman(int number) {
            TreeMap<Integer, String> map = new TreeMap<Integer, String>();
            map.put(50, "L");
            map.put(40, "XL");
            map.put(10, "X");
            map.put(9, "IX");
            map.put(5, "V");
            map.put(4, "IV");
            map.put(1, "I");

            int l =  map.floorKey(number);
            if ( number == l ) {
                return map.get(number);
            }
            return map.get(l) + toRoman(number-l);
        }

        public static int romanToInt(String s) {
            HashMap<Character, Integer> romanValues = new HashMap<>();
            romanValues.put('I', 1);
            romanValues.put('V', 5);
            romanValues.put('X', 10);
            romanValues.put('L', 50);

            int result = 0;
            int prevValue = 0;

            for (int i = s.length() - 1; i >= 0; i--) {
                int currentValue = romanValues.get(s.charAt(i));

                if (currentValue < prevValue) {
                    result -= currentValue;
                } else {
                    result += currentValue;
                }

                prevValue = currentValue;
            }

            return result;
        }


        public static boolean isRoman(String roman) {
            return roman.matches("^(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
        }
    }
    public static List<String> getObjectNameList(List<?> list){
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }
}
