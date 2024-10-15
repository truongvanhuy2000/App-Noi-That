package com.huy.appnoithat.Controller.LuaChonNoiThat.Converter;

import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

public class DecimalPlaceFormulaLongConverter  extends StringConverter<Long> {
    private final StringConverter<Long> decimalLongStringConverter = new DecimalLongStringConverter();
    private final FormulaConverter formulaConverter = new FormulaConverter();

    @Override
    public Long fromString(String value) {
        Number formulaResult = formulaConverter.fromString(value);
        return decimalLongStringConverter.fromString(String.valueOf(formulaResult.longValue()));
    }

    @Override
    public String toString(Long value) {
        if (value.equals(0L)) {
            return StringUtils.EMPTY;
        }
        return decimalLongStringConverter.toString(value);
    }
}