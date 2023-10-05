package com.huy.appnoithat.Controller.LuaChonNoiThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TableUtilsTest {
    @BeforeEach
    void setUp() {

    }

    @Test
    void calculateKhoiLuong() {
        assertEquals(TableUtils.calculateKhoiLuong(0f, 0f, 0f, "mét dài"), 0f);
        assertEquals(TableUtils.calculateKhoiLuong(0f, 0f, 0f, "mét vuông"), 0f);
        assertEquals(TableUtils.calculateKhoiLuong(1f, 1f, 1f, "mét dài"), 0.001f);
        assertEquals(TableUtils.calculateKhoiLuong(1f, 1f, 1f, "mét vuông"), 0.000001f);
        assertEquals(TableUtils.calculateKhoiLuong(2000f, 1000f, 1f, "mét vuông"), 2f);
        assertEquals(TableUtils.calculateKhoiLuong(2000f, 1000f, 1f, "mét dài"), 2f);
    }
}