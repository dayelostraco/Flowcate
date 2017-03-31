package com.notiflowcate.exception;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
public class GeofenceException extends Exception {

    private static final long serialVersionUID = -2300071626973603951L;

    public GeofenceException() {
        super();
    }

    public GeofenceException(String message) {
        super(message);
    }

    public GeofenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
