package com.huy.appnoithat.Shared;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Utils {
    public static boolean isAlpha(String name) {
        if (name == null)
            return false;
        return name.matches("[a-zA-Z]+");
    }

    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static String encodeValue(String value) {
        if (value == null)
            return "";
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static String decodeValue(String value) {
        if (value == null)
            return "";
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
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

            int l = map.floorKey(number);
            if (number == l) {
                return map.get(number);
            }
            return map.get(l) + toRoman(number - l);
        }

        public static int romanToInt(String s) {
            if (s == null || s.isEmpty()) {
                return -1;
            }
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
            if (roman == null)
                return false;
            return roman.matches("^(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
        }
    }

    public static List<String> getObjectNameList(List<?> list) {
        if (list == null)
            new ArrayList<>();
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }

    public static String convertLongToDecimal(long number) {
        // Create a DecimalFormat object with comma delimiter
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        // Format the long number to a string with commas
        return decimalFormat.format(number);
    }

    public static long convertDecimalToLong(String formattedNumber) {
        if (formattedNumber == null)
            return 0L;
        // Remove commas from the string
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        try {
            return decimalFormat.parse(formattedNumber).longValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
