package com.huy.appnoithat.Shared;

import com.huy.appnoithat.Common.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    @Test
    void isRoman() {
        assertTrue(Utils.RomanNumber.isRoman("I"));
        assertTrue(Utils.RomanNumber.isRoman("II"));
        assertTrue(Utils.RomanNumber.isRoman("III"));
        assertTrue(Utils.RomanNumber.isRoman("IV"));
        assertTrue(Utils.RomanNumber.isRoman("VI"));
        assertTrue(Utils.RomanNumber.isRoman("V"));
        assertTrue(Utils.RomanNumber.isRoman("X"));
        assertTrue(Utils.RomanNumber.isRoman("XV"));
        assertTrue(Utils.RomanNumber.isRoman("LV"));

    }

    @Test
    void isNumeric() {
        assertTrue(Utils.isNumeric("1"));
        assertTrue(Utils.isNumeric("2"));
        assertTrue(Utils.isNumeric("3"));
        assertFalse(Utils.isNumeric("a"));
    }

    @Test
    void getIntegerFromRoman() {
        assertEquals(1, Utils.RomanNumber.romanToInt("I"));
        assertEquals(2, Utils.RomanNumber.romanToInt("II"));
        assertEquals(3, Utils.RomanNumber.romanToInt("III"));
        assertEquals(4, Utils.RomanNumber.romanToInt("IV"));
        assertEquals(5, Utils.RomanNumber.romanToInt("V"));
        assertEquals(6, Utils.RomanNumber.romanToInt("VI"));
        assertEquals(7, Utils.RomanNumber.romanToInt("VII"));
        assertEquals(8, Utils.RomanNumber.romanToInt("VIII"));
        assertEquals(9, Utils.RomanNumber.romanToInt("IX"));
    }
}