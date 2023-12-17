package com.huy.appnoithat.Common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    /**
     * @param name This function will check if the input is belonged to the alhabet or not
     * @return
     */
    public static boolean isAlpha(String name) {
        if (name == null)
            return false;
        return name.matches("[a-zA-Z]+");
    }
    public static String toAlpha(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 'A' - 1)) : null;
    }
    /**
     * @param str This function will check if the input is numeric or not
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * @param value This function will encode the input value to UTF-8
     * @return
     */
    public static String encodeValue(String value) {
        if (value == null)
            return "";
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    /**
     * @param value This function will decode the input value to UTF-8
     * @return
     */
    public static String decodeValue(String value) {
        if (value == null)
            return "";
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

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

    /**
     * @param list This function will convert the input object list to the list of string
     * @return
     */
    public static List<String> getObjectNameList(List<?> list) {
        if (list == null || list.isEmpty())
            new ArrayList<>();
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }

    /**
     * @param number This function will convert the input long number to the string with commas
     * @return
     */
    public static String convertLongToDecimal(long number) {
        // Create a DecimalFormat object with comma delimiter
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        // Format the long number to a string with commas
        return decimalFormat.format(number);
    }

    /**
     * @param formattedNumber This function will convert the input string with commas to the long number
     * @return
     */
    public static long convertDecimalToLong(String formattedNumber) {
        if (formattedNumber == null)
            return 0L;
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        try {
            return decimalFormat.parse(formattedNumber).longValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static String convertMilisToDateTimeString(long milis) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(milis));
    }
    public static long convertDateTimeStringToMilis(String dateTimeString) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateTimeString).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static String urlEncode(String data) {
        if (data == null)
            return "";
        return URLEncoder.encode(data, StandardCharsets.UTF_8);
    }
    public static String base64EncodeData(String data) {
        if (data == null)
            return "";
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
    public static String base64DecodeData(byte[] data) {
        if (data == null)
            return "";
        return new String(Base64.getDecoder().decode(data));
    }
    public static String urlDecode(byte[] data) {
        if (data == null)
            return "";
        String encodedData = new String(data, StandardCharsets.UTF_8);
        return URLDecoder.decode(encodedData, StandardCharsets.UTF_8);
    }
    public static String readFromFile(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            return Utils.urlDecode(inputStream.readAllBytes());
        } catch (IOException e) {
            return null;
        }
    }
    public static void writeToFile(File file, String data) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(Utils.urlEncode(data).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> T readObjectFromFile(File file, Class<T> clazz) {
        try (InputStream inputStream = new FileInputStream(file)) {
            return new ObjectMapper().readValue(Utils.urlDecode(
                    base64DecodeData(inputStream.readAllBytes()).getBytes()), clazz);
        } catch (IOException e) {
            return null;
        }
    }
    public static <T> void writeObjectToFile(File file, T object) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(Utils.base64EncodeData(
                    urlEncode(new ObjectMapper().writeValueAsString(object))).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
