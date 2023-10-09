package com.concesionaria.exceptions;

@SuppressWarnings("unused")
public class NoSuchVehicleException extends RuntimeException {
    public NoSuchVehicleException(String message) {
        super(message);
    }
}