package com.voting.util.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    APP_ERROR("APP_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    //  http://stackoverflow.com/a/22358422/548473
    DATA_NOT_FOUND("DATA_NOT_FOUND", HttpStatus.UNPROCESSABLE_ENTITY),
    DATA_ERROR("DATA_ERROR", HttpStatus.CONFLICT),
    VALIDATION_ERROR("VALIDATION_ERROR", HttpStatus.UNPROCESSABLE_ENTITY),
    VOTE_ERROR("VOTE_ERROR", HttpStatus.CONFLICT),
    WRONG_REQUEST("WRONG_REQUEST", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final HttpStatus status;

    ErrorType(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
