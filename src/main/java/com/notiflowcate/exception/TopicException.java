package com.notiflowcate.exception;

/**
 * @author Dayel Ostraco
 * 11/27/15
 */
public class TopicException extends Exception {

    private static final long serialVersionUID = -800869248254655476L;

    public TopicException() {
        super();
    }

    public TopicException(String message) {
        super(message);
    }

    public TopicException(String message, Throwable cause) {
        super(message, cause);
    }
}
