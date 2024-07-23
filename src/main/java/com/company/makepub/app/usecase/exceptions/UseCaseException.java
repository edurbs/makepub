package com.company.makepub.app.usecase.exceptions;

public class UseCaseException extends RuntimeException {

    public UseCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UseCaseException(String message) {
        super(message);
    }
}
