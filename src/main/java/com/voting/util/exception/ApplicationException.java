package com.voting.util.exception;

public class ApplicationException extends RuntimeException {

    private final ErrorType type;

    public ApplicationException(ErrorType type, String msgCode) {
        super(msgCode);
        this.type = type;

    }

    public ErrorType getType() {
        return type;
    }
}
