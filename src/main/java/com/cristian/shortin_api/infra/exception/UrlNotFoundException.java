package com.cristian.shortin_api.infra.exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String s) {
        super(s);
    }
}
