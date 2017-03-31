package com.notiflowcate.exception;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
public class NotificationException extends Exception {

    private static final long serialVersionUID = -8748244946195696386L;

    public NotificationException() {
        super();
    }

    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
