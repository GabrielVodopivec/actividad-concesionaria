package com.concesionaria.services;

import com.concesionaria.dto.response.VehicleBrandModelDTO;
import com.concesionaria.models.Vehicle;
import com.concesionaria.repositories.IRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("vehicleBrandModelServiceDTO")
public class VehicleBrandModelServiceDTO implements IService<VehicleBrandModelDTO> {
    IRepository<Vehicle> vehicleRepository;

    public VehicleBrandModelServiceDTO(IRepository<Vehicle> vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<VehicleBrandModelDTO> findAll() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
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

    @Override
    public VehicleBrandModelDTO createOrUpdate(VehicleBrandModelDTO vehicleBrandModelDTO) {
        return null;
    }

    @Override
    public List<VehicleBrandModelDTO> findByDate(LocalDate initialDate, LocalDate finalDate) {
        return null;
    }
}