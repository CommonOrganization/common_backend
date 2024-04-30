package com.junghun.common.domain.like.exception;

public class AlreadyLikeException extends RuntimeException {
    public AlreadyLikeException(String message) {
        super(message);
    }
}
