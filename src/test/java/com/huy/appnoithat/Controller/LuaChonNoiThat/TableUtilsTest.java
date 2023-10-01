package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TableUtilsTest {
    @BeforeEach
    void setUp() {

    }

    @Test
    void calculateKhoiLuong() {
        assertEquals(TableCalculationUtils.calculateKhoiLuong(0f, 0f, 0f, "mét dài"), 0f);
        assertEquals(TableCalculationUtils.calculateKhoiLuong(0f, 0f, 0f, "mét vuông"), 0f);
        assertEquals(TableCalculationUtils.calculateKhoiLuong(1f, 1f, 1f, "mét dài"), 0.001f);
        assertEquals(TableCalculationUtils.calculateKhoiLuong(1f, 1f, 1f, "mét vuông"), 0.000001f);
        assertEquals(TableCalculationUtils.calculateKhoiLuong(2000f, 1000f, 1f, "mét vuông"), 2f);
        assertEquals(TableCalculationUtils.calculateKhoiLuong(2000f, 1000f, 1f, "mét dài"), 2f);
    }
}