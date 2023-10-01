package com.concesionaria.exceptions;

public class NoSuchVehicleException extends RuntimeException {
    public NoSuchVehicleException(String message) {
        super(message);
    }
}