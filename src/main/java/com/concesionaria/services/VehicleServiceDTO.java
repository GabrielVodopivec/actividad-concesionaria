package com.concesionaria.services;

import com.concesionaria.dto.response.VehicleDTO;
import com.concesionaria.models.Vehicle;
import com.concesionaria.repositories.IRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        mapper.registerModule(new JavaTimeModule());
        return vehicleRepository
                       .findAll()
                       .stream()
                       .map(vehicle -> (
                               mapper.convertValue(vehicle, VehicleDTO.class)
                       ))
                       .toList();
    }

    @Override
    public VehicleDTO findOne(Long id) {
        return null;
    }

    @Override
    public VehicleDTO createOrUpdate(VehicleDTO vehicleDTO) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Vehicle vehicle = mapper.convertValue(vehicleDTO, Vehicle.class);
        return mapper.convertValue(vehicleRepository.createOrUpdate(vehicle), VehicleDTO.class);
    }

    @Override
    public List<VehicleDTO> findByDate(LocalDate initialDate, LocalDate finalDate) {
        ObjectMapper mapper;
        List<Vehicle> resultList = vehicleRepository.findByDate(initialDate, finalDate);
        if (resultList.isEmpty()) {
            return null;
        }
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        return resultList
                       .stream()
                       .map(vehicle -> mapper.convertValue(vehicle, VehicleDTO.class))
                       .toList();
    }
}