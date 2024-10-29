package com.huy.appnoithat.Controller.LuaChonNoiThat.Converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormulaConverterTest {
    FormulaConverter formulaConverter = new FormulaConverter();

    @Test
    void testToString() {
    }

    @Test
    void fromString() {
        assertEquals(formulaConverter.fromString("1,000,000 + 2,000,000"), 3000000);
    }
}