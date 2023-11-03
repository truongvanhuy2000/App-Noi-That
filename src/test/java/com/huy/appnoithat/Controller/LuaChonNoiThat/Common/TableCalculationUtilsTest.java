package com.huy.appnoithat.Controller.LuaChonNoiThat.Common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TableCalculationUtilsTest {

    @Test
    void round() {

        assertEquals(TableCalculationUtils.round(9377015), 9000000);
        assertEquals(TableCalculationUtils.round(9677015), 10000000);
        assertEquals(TableCalculationUtils.round(28131045), 28000000);
    }
}