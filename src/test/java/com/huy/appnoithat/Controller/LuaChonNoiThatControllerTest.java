package com.huy.appnoithat.Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LuaChonNoiThatControllerTest {
    LuaChonNoiThatController luaChonNoiThatController;
    @Test
    void initialize() {
    }

    @Test
    void testInitialize() {
        luaChonNoiThatController.initialize(null, null);
    }

    @BeforeEach
    void setUp() {
        luaChonNoiThatController = new LuaChonNoiThatController();
    }

    @AfterEach
    void tearDown() {
    }
}