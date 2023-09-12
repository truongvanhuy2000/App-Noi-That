package com.huy.appnoithat.Controller.LuaChonNoiThat.CustomConverter;

import com.huy.appnoithat.Shared.Utils;
import javafx.util.StringConverter;

public class CustomLongStringConverter extends StringConverter<Long> {
    @Override
    public Long fromString(String var1) {
        var1 = var1.trim();
        Long returnLong = Utils.convertDecimalToLong(var1);
        return returnLong;
    }
    @Override
    public String toString(Long var1) {
        return Utils.convertLongToDecimal(var1);
    }
}
