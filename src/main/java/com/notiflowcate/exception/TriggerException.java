package com.notiflowcate.exception;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
public class TriggerException extends Exception {

    private static final long serialVersionUID = 6229774073903723203L;

    public TriggerException() {
        super();
    }

    public TriggerException(String message) {
        super(message);
    }

    public TriggerException(String message, Throwable cause) {
        super(message, cause);
    }
}
