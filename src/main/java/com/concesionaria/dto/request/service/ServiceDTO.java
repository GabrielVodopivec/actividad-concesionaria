package com.concesionaria.dto.request.service;

import java.util.Objects;

@SuppressWarnings("unused")
public class ServiceDTO extends BasicServiceDTO {
    private Long vehicleID;

    public Long getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Long vehicleID) {
        this.vehicleID = vehicleID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDTO that = (ServiceDTO) o;
        return Objects.equals(super.getId(), that.getId());
    }

}