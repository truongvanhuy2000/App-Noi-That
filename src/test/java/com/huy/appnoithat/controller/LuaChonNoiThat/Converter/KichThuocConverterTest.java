package com.huy.appnoithat.Controller.LuaChonNoiThat.Converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KichThuocConverterTest {
    private KichThuocConverter kichThuocConverter = new KichThuocConverter();
    @Test
    void testToString() {
    }

    @Test
    void fromString() {
        assertEquals(kichThuocConverter.fromString("1.2"), 1.2);
        assertEquals(kichThuocConverter.fromString("1+1.5"), 2.5);
    }
}