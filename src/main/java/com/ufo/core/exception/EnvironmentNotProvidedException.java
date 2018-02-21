package com.ufo.core.exception;

/**
 * Created by FOG on 21.02.2018.
 */

@SuppressWarnings("unused")
public class EnvironmentNotProvidedException extends RuntimeException{

    public EnvironmentNotProvidedException() {
        super();
    }

    public EnvironmentNotProvidedException(String message) {
        super(message);
    }

    public EnvironmentNotProvidedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnvironmentNotProvidedException(Throwable cause) {
        super(cause);
    }

    public EnvironmentNotProvidedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
