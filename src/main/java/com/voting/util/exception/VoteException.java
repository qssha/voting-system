package com.voting.util.exception;

public class VoteException extends ApplicationException {
    public VoteException(String message) {
        super(ErrorType.VOTE_ERROR,message);
    }
}
