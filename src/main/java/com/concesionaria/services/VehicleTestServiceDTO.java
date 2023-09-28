package com.concesionaria.services;

import com.concesionaria.dto.response.VehicleTestDTO;
import com.concesionaria.models.Vehicle;
import com.concesionaria.repositories.IRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vehicleTestServiceDTO")
public class VehicleTestServiceDTO implements IService<VehicleTestDTO> {
    IRepository<Vehicle> vehicleRepository;

    public VehicleTestServiceDTO(IRepository<Vehicle> vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<VehicleTestDTO> findAll() {
        return vehicleRepository.findAll()
                                .stream()
                                .map(vehicle -> {
                                    VehicleTestDTO vehicleTestDTO = new VehicleTestDTO();
                                    vehicleTestDTO.setBrand(vehicle.getBrand());
                                    vehicleTestDTO.setModel(vehicle.getModel());
                                    return vehicleTestDTO;
                                })
                                .toList();
    }

    @Override
    public VehicleTestDTO findOne(Long id) {
        return null;
    }
}