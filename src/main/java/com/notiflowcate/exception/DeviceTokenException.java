package com.notiflowcate.exception;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
public class DeviceTokenException extends Exception {

    private static final long serialVersionUID = -8633136667647400214L;

    public DeviceTokenException() {
        super();
    }

    public DeviceTokenException(String message) {
        super(message);
    }

    public DeviceTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
