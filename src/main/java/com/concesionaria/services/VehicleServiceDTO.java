package com.concesionaria.services;

import com.concesionaria.dto.response.VehicleDTO;
import com.concesionaria.models.Vehicle;
import com.concesionaria.repositories.IRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vehicleServiceDTO")
public class VehicleServiceDTO implements IService<VehicleDTO> {

    IRepository<Vehicle> vehicleRepository;

    public VehicleServiceDTO(IRepository<Vehicle> vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<VehicleDTO> findAll() {
        ObjectMapper mapper = new ObjectMapper();
        return vehicleRepository
                       .findAll()
                       .stream()
                       .map(vehicle -> mapper.convertValue(vehicle, VehicleDTO.class))
                       .toList();
    }

    @Override
    public VehicleDTO findOne(Long id) {
        return null;
    }
}