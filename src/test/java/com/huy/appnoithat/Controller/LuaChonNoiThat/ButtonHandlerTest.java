package com.huy.appnoithat.Controller.LuaChonNoiThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ButtonHandlerTest {
    @BeforeEach
    void setUp() {
    }

    @Test
    void addNewLine() {
    }

    @Test
    void continuousLineAdd() {
    }

    @Test
    void findTheNextStt() {
        assertEquals("B", ButtonHandler.findTheNextStt("A"));
        assertEquals("C", ButtonHandler.findTheNextStt("B"));
        assertEquals("D", ButtonHandler.findTheNextStt("C"));
        assertEquals("II", ButtonHandler.findTheNextStt("I"));
        assertEquals("III", ButtonHandler.findTheNextStt("II"));
        assertEquals("IV", ButtonHandler.findTheNextStt("III"));
        assertEquals("V", ButtonHandler.findTheNextStt("IV"));
        assertEquals("2", ButtonHandler.findTheNextStt("1"));
        assertEquals("3", ButtonHandler.findTheNextStt("2"));
        assertEquals("4", ButtonHandler.findTheNextStt("3"));
        assertEquals("1", ButtonHandler.findTheNextStt("0"));
    }

    @Test
    void onDeleteLine() {
    }
}