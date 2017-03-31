package com.notiflowcate.exception;

/**
 * @author Dayel Ostraco
 * 11/27/15
 */
public class ApplicationException extends Exception {

    private static final long serialVersionUID = 1223674776826317171L;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
