package com.bluslee.mundo.core.exception;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description MundoException
 */
public class MundoException extends RuntimeException {

    public MundoException() {
    }

    public MundoException(String message) {
        super(message);
    }

    public MundoException(String message, Throwable cause) {
        super(message, cause);
    }

    public MundoException(Throwable cause) {
        super(cause);
    }

    public MundoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
