package com.concesionaria.exceptions;

@SuppressWarnings("unused")
public class DateParseException extends RuntimeException {
    public DateParseException(String message) {
        super(message);
    }
}