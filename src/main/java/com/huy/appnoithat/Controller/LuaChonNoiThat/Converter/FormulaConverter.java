package com.huy.appnoithat.Controller.LuaChonNoiThat.Converter;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.parser.ParseException;
import com.huy.appnoithat.Exception.CalculationException;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FormulaConverter extends StringConverter<Number> {
    private final Logger LOGGER = LogManager.getLogger(this);

    @Override
    public String toString(Number aLong) {
        if (aLong.equals(0.0)) {
            return StringUtils.EMPTY;
        }
        return String.valueOf(aLong.longValue());
    }

    @Override
    public Number fromString(String s) {
        Expression expression = new Expression(s);
        try {
            return expression.evaluate().getNumberValue();
        } catch (EvaluationException e) {
            LOGGER.error("Can't evaluate this expression", e);
            throw new CalculationException(e);
        } catch (ParseException e) {
            LOGGER.error("Unable to parse this expression", e);
            throw new CalculationException(e);
        }
    }
}
