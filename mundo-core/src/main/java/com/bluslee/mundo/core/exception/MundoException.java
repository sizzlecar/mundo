package com.bluslee.mundo.core.exception;

/**
 * mundo异常基类.
 *
 * @author carl.che
 */
public class MundoException extends RuntimeException {

    public MundoException() {
    }

    public MundoException(final String message) {
        super(message);
    }

    public MundoException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MundoException(final Throwable cause) {
        super(cause);
    }

    public MundoException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
