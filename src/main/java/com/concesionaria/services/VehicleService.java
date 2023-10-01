package com.concesionaria.services;

import com.concesionaria.dto.response.BasicVehicleDTO;
import com.concesionaria.dto.response.VehicleDTO;
import com.concesionaria.exceptions.NoSuchVehicleException;
import com.concesionaria.exceptions.VehicleNotFoundException;
import com.concesionaria.models.Vehicle;
import com.concesionaria.repositories.IRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service("vehicleService")
public class VehicleService implements IService<BasicVehicleDTO> {
    IRepository<Vehicle> vehicleIRepository;

    public VehicleService(IRepository<Vehicle> vehicleIRepository) {
        this.vehicleIRepository = vehicleIRepository;
    }

    @Override
    public List<BasicVehicleDTO> findAll() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        return vehicleIRepository
                       .findAll()
                       .stream()
                       .map(vehicle -> mapper.convertValue(vehicle, BasicVehicleDTO.class))
                       .toList();
    }

    @Override
    public BasicVehicleDTO findOne(Long id) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Optional<Vehicle> vehicle = vehicleIRepository.findById(id);
        return vehicle.map(value -> mapper.convertValue(value, VehicleDTO.class))
                      .orElseThrow(() -> new VehicleNotFoundException("Vehiculo no encontrado"));
    }

    @Override
    public BasicVehicleDTO createOrUpdate(BasicVehicleDTO vehicleDTO) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Vehicle vehicle = mapper.convertValue(vehicleDTO, Vehicle.class);

        return mapper.convertValue(vehicleIRepository.createOrUpdate(vehicle), VehicleDTO.class);
    }

    @Override
    public List<BasicVehicleDTO> findByDate(LocalDate since, LocalDate to) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Vehicle> vehicles = vehicleIRepository.findByDate(since, to);

        if (vehicles.isEmpty()) {
            throw new NoSuchVehicleException("No hay vehículos fabricados en ese período");
        }

        return vehicles.stream()
                       .map(vehicle -> mapper.convertValue(vehicle, BasicVehicleDTO.class))
                       .toList();
    }

    @Override
    public List<BasicVehicleDTO> filterByPrice(Integer since, Integer to, String currency) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<Vehicle> vehicles = vehicleIRepository.findByPrice(since, to, currency);
        if (vehicles.isEmpty()) {
            throw new NoSuchVehicleException("No encontramos vehículos en ese rango de precios");
        }

        return vehicles.stream()
                       .map(vehicle -> mapper.convertValue(vehicle, BasicVehicleDTO.class))
                       .toList();
    }
}