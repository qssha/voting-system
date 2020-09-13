package com.voting.util.exception;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(ErrorType.DATA_NOT_FOUND, message);
    }
}
