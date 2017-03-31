package com.notiflowcate.exception;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
public class BeaconException extends Exception {

    private static final long serialVersionUID = 4698534951490122091L;

    public BeaconException() {
        super();
    }

    public BeaconException(String message) {
        super(message);
    }

    public BeaconException(String message, Throwable cause) {
        super(message, cause);
    }
}
