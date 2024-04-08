package com.junghun.common.domain.user.exception;

public class NotExistUserException extends RuntimeException{

    public NotExistUserException(String message) {
        super(message);
    }
}
