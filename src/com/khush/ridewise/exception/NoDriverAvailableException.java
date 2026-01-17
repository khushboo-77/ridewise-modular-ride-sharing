package com.khush.ridewise.exception;

public class NoDriverAvailableException extends RuntimeException {
    public NoDriverAvailableException(String message) {
        super(message);
    }
}
