package com.concesionaria.dto.request.vehicle;

import com.concesionaria.dto.request.service.BasicServiceDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class VehicleDTO extends BasicVehicleDTO {
    private List<BasicServiceDTO> services;

    public VehicleDTO() {
        this.services = new ArrayList<>();
    }

    public List<BasicServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<BasicServiceDTO> services) {
        this.services = services;
    }

    public void addService(BasicServiceDTO service) {
        this.services.add(service);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDTO that = (VehicleDTO) o;
        return Objects.equals(super.getId(), that.getId());
    }

}