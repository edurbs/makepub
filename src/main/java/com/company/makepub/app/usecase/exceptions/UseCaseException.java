package com.company.makepub.app.usecase.exceptions;

public class UseCaseException extends RuntimeException {

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public UseCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UseCaseException(String message) {
        super(message);
    }
}
