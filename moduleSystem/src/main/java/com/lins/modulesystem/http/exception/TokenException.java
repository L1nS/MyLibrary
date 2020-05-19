package com.lins.modulesystem.http.exception;

public class TokenException extends Exception {

    public TokenException() {
    }

    public TokenException(String detailMessage) {
        super(detailMessage);
    }

    public TokenException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public TokenException(Throwable throwable) {
        super(throwable);
    }

}
