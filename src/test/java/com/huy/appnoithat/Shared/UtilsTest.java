package com.huy.appnoithat.Shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.DataModel.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    String tokenObject = "{\n" +
            "    \"token\": \"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImFwcG5vaXRoYXQiLCJpYXQiOjE3MDAzOTkzMzYsImV4cCI6MTcwMDM5OTM5Nn0.6g8XsCycgkG3UoUR8c34j6D6AW2x2AtxoupyVhvCTQbxS1OCiJSwoSRzWhCrmOA8bTW2z-8jxkk_OgUCKphjZw\",\n" +
            "    \"refreshToken\": \"eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhcHBub2l0aGF0Iiwic3ViIjoiYWRtaW4iLCJhdWQiOiJhZG1pbiIsImlhdCI6MTcwMDM5OTMzNiwiZXhwIjoxNzAyOTkxMzM2fQ.SO_rTOTOJMuZEulcydA6JffM6nBMCBZvcaZFv-l4smCGV07jtWotO6naRihMba5NBA-7fZnN_C2qPi9fKAGYkw\"\n" +
            "}";
    @Test
    void isRoman() throws JsonProcessingException {
        Token token = new ObjectMapper().readValue(tokenObject, Token.class);
        System.out.println(token);
    }

    @Test
    void isNumeric() {
        System.out.println(System.getProperty("user.home"));

    }

    @Test
    void getIntegerFromRoman() {
    }
    @Test
    void testCalculation() {
    }
}