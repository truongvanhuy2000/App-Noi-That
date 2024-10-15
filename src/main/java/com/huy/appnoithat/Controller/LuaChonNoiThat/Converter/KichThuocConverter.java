package com.huy.appnoithat.Controller.LuaChonNoiThat.Converter;

import javafx.util.converter.DoubleStringConverter;
import org.apache.commons.lang3.StringUtils;

public class KichThuocConverter extends DoubleStringConverter {
    private final FormulaConverter formulaConverter = new FormulaConverter();

    @Override
    public String toString(Double value) {
        if (value.equals(0.0)) {
            return StringUtils.EMPTY;
        }
        return String.valueOf(value.longValue());
    }

    @Override
    public Double fromString(String s) {
        return formulaConverter.fromString(s).doubleValue();
    }
}
