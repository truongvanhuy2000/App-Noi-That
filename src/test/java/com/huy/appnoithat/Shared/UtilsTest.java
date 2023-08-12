package com.huy.appnoithat.Shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    @Test
    void isRoman()
    {
        assertTrue(Utils.RomanNumber.isRoman("I"));
        assertTrue(Utils.RomanNumber.isRoman("II"));
        assertTrue(Utils.RomanNumber.isRoman("III"));
        assertTrue(Utils.RomanNumber.isRoman("IV"));
        assertTrue(Utils.RomanNumber.isRoman("VI"));
        assertTrue(Utils.RomanNumber.isRoman("V"));
        assertTrue(Utils.RomanNumber.isRoman("X"));
        assertTrue(Utils.RomanNumber.isRoman("XV"));
    }

    @Test
    void isNumeric() {
        assertTrue(Utils.isNumeric("1"));
        assertTrue(Utils.isNumeric("2"));
        assertTrue(Utils.isNumeric("3"));
        assertFalse(Utils.isNumeric("a"));
    }
}