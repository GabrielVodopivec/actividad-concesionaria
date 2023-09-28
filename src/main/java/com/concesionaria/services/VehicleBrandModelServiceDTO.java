package com.concesionaria.services;

import com.concesionaria.dto.response.VehicleBrandModelDTO;
import com.concesionaria.models.Vehicle;
import com.concesionaria.repositories.IRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return vehicleRepository
                       .findAll()
                       .stream()
                       .map(vehicle -> (
                               mapper.convertValue(vehicle, VehicleBrandModelDTO.class)
                       ))
                       .toList();
    }

    @Override
    public VehicleBrandModelDTO findOne(Long id) {
        return null;
    }
}