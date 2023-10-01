package com.concesionaria.dto.response;

import com.concesionaria.models.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VehicleDTO extends BasicVehicleDTO {
    private Long id;
    private String brand;
    private String model;
    private LocalDate manufacturingDate;
    private Integer numberOfKilometers;
    private Integer doors;
    private Integer price;
    private String currency;
    private Integer countOfOwners;
    private List<Service> services;

    public VehicleDTO() {
        this.services = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Integer getNumberOfKilometers() {
        return numberOfKilometers;
    }

    public void setNumberOfKilometers(Integer numberOfKilometers) {
        this.numberOfKilometers = numberOfKilometers;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getCountOfOwners() {
        return countOfOwners;
    }

    public void setCountOfOwners(Integer countOfOwners) {
        this.countOfOwners = countOfOwners;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
        this.services.add(service);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDTO that = (VehicleDTO) o;
        return Objects.equals(id, that.id);
    }

}