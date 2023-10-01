package com.concesionaria.exceptions;

public class DateParseException extends RuntimeException{
    public DateParseException(String message) {
        super(message);
    }
}