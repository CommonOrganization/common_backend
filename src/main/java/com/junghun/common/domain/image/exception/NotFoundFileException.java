package com.junghun.common.domain.image.exception;

public class NotFoundFileException extends RuntimeException {

    public NotFoundFileException(String message) {
        super(message);
    }
}