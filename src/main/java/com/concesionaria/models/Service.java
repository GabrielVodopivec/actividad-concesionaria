package com.concesionaria.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Service {
    private LocalDate date;
    private Integer kilometers;

    private String descriptions;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}