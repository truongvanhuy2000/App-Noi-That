package com.huy.appnoithat.Controller.LuaChonNoiThat.Converter;

import com.huy.appnoithat.Common.Utils;
import javafx.util.StringConverter;

public class DecimalLongStringConverter extends StringConverter<Long> {
    @Override
    public Long fromString(String var1) {
        if (var1 == null) {
            return 0L;
        }
        var1 = var1.trim();
        return Utils.convertDecimalToLong(var1);
    }

    @Override
    public String toString(Long var1) {
        if (var1 == null) {
            return "0";
        }
        return Utils.convertLongToDecimal(var1);
    }
}
