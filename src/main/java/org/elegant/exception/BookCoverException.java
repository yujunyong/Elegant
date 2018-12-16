package org.elegant.exception;

public class BookCoverException extends ElegantException {
    public BookCoverException() {
    }

    public BookCoverException(String message) {
        super(message);
    }

    public BookCoverException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookCoverException(Throwable cause) {
        super(cause);
    }

    public BookCoverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
