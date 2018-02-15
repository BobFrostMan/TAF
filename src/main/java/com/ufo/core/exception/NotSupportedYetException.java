package com.ufo.core.exception;

/**
 * Created by FOG on 15.02.2018.
 * <p>
 * Runtime exception to inform about not supported functionality
 */
public class NotSupportedYetException extends RuntimeException {

    public NotSupportedYetException(String message) {
        super(message);
    }

}
