package com.huy.appnoithat.Controller.LuaChonNoiThat.CustomConverter;

import com.huy.appnoithat.Common.Utils;
import javafx.util.StringConverter;

public class CustomLongStringConverter extends StringConverter<Long> {
    @Override
    public Long fromString(String var1) {
        if (var1 == null) {
            return 0L;
        }
        var1 = var1.trim();
        Long returnLong = Utils.convertDecimalToLong(var1);
        return returnLong;
    }
    @Override
    public String toString(Long var1) {
        if (var1 == null) {
            return "0";
        }
        return Utils.convertLongToDecimal(var1);
    }
}
