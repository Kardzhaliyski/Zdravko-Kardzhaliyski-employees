package com.github.kardzhaliyski.collaboration.exceptions;

public class InvalidDateFormatException extends RuntimeException {
    public InvalidDateFormatException() {
    }

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
