package com.huy.appnoithat.Controller.LuaChonNoiThat.Converter;

import javafx.util.converter.DoubleStringConverter;
import org.apache.commons.lang3.StringUtils;

public class KichThuocConverter extends DoubleStringConverter {
    @Override
    public String toString(Double value) {
        if (value.equals(0.0)) {
            return StringUtils.EMPTY;
        }
        return String.valueOf(value.longValue());
    }
}
