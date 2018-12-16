package org.elegant.exception;

public class ElegantException extends RuntimeException {
    public ElegantException() {
    }

    public ElegantException(String message) {
        super(message);
    }

    public ElegantException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElegantException(Throwable cause) {
        super(cause);
    }

    public ElegantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
