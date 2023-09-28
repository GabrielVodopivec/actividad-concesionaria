package com.concesionaria.services;

import com.concesionaria.dto.response.VehicleBrandModelDTO;
import com.concesionaria.models.Vehicle;
import com.concesionaria.repositories.IRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vehicleTestServiceDTO")
public class VehicleBrandModelServiceDTO implements IService<VehicleBrandModelDTO> {
    IRepository<Vehicle> vehicleRepository;

    public VehicleBrandModelServiceDTO(IRepository<Vehicle> vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<VehicleBrandModelDTO> findAll() {
        return vehicleRepository.findAll()
                                .stream()
                                .map(vehicle -> {
                                    VehicleBrandModelDTO vehicleBrandModelDTO = new VehicleBrandModelDTO();
                                    vehicleBrandModelDTO.setBrand(vehicle.getBrand());
                                    vehicleBrandModelDTO.setModel(vehicle.getModel());
                                    return vehicleBrandModelDTO;
                                })
                                .toList();
    }

    @Override
    public VehicleBrandModelDTO findOne(Long id) {
        return null;
    }
}