package com.huy.appnoithat.Exception;

public class CalculationException extends RuntimeException {
    public CalculationException(String message) {
        super(message);
    }

    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculationException(Throwable cause) {
        super(cause);
    }

    public CalculationException() {
        super();
    }
}
