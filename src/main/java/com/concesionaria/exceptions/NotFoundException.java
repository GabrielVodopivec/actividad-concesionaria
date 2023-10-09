package com.concesionaria.exceptions;

@SuppressWarnings("unused")
public class NotFoundException extends RuntimeException {

    private String detail;
    public NotFoundException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}