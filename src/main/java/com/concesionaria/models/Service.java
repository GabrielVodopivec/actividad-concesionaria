package com.concesionaria.models;

import java.time.LocalDate;
import java.util.Objects;

@SuppressWarnings("unused")
public class Service {
    private Long id;
    private LocalDate date;
    private Integer kilometers;
    private String descriptions;
    private Long vehicleID;

    public Service() {
    }

    public Service(Long id, LocalDate date, Integer kilometers, String descriptions) {
        this.id           = id;
        this.date         = date;
        this.kilometers   = kilometers;
        this.descriptions = descriptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Long vehicleID) {
        this.vehicleID = vehicleID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service service)) return false;
        return Objects.equals(id, service.id);
    }

}