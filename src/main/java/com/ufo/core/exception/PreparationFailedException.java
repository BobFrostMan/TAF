package com.ufo.core.exception;

/**
 * Created by FOG on 12.02.2018.
 * <p>
 * Common exception for all types of runners
 */
@SuppressWarnings("unused")
public class PreparationFailedException extends Exception {

    public PreparationFailedException(String message) {
        super(message);
    }

    public PreparationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreparationFailedException(Throwable cause) {
        super(cause);
    }

    public PreparationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
