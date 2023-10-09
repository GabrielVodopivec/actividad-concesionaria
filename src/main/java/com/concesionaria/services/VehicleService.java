package com.concesionaria.services;

import com.concesionaria.dto.request.vehicle.BasicVehicleDTO;
import com.concesionaria.dto.request.vehicle.VehicleDTO;
import com.concesionaria.exceptions.NoSuchVehicleException;
import com.concesionaria.exceptions.NotFoundException;
import com.concesionaria.models.Service;
import com.concesionaria.models.Vehicle;
import com.concesionaria.repositories.IRepository;
import com.concesionaria.repositories.ServiceRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@org.springframework.stereotype.Service
public class VehicleService implements IService<BasicVehicleDTO> {
    private final IRepository<Vehicle> vehicleIRepository;
    private final IRepository<Service> serviceRepository;
    private final ObjectMapper mapper;

    public VehicleService(IRepository<Vehicle> vehicleIRepository, IRepository<Service> serviceRepository) {
        this.vehicleIRepository = vehicleIRepository;
        this.serviceRepository  = serviceRepository;
        mapper                  = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    @Override
    public List<BasicVehicleDTO> findAll() {
        return vehicleIRepository
                       .findAll()
                       .stream()
                       .map(vehicle -> mapper.convertValue(vehicle, BasicVehicleDTO.class))
                       .toList();
    }

    @Override
    public BasicVehicleDTO findOne(Long id) {
        List<Service> vehicleServices;
        Optional<Vehicle> vehicle = vehicleIRepository.findById(id);
        Predicate<Service> byVehicleId = service -> service.getVehicleID().equals(id);
        if (vehicle.isPresent()) {
            vehicleServices = serviceRepository
                                      .findAll()
                                      .stream()
                                      .filter(byVehicleId)
                                      .toList();

            vehicle.get().setServices(vehicleServices);
        }

        return vehicle.map(v -> mapper.convertValue(v, VehicleDTO.class))
                      .orElseThrow(() -> new NotFoundException(
                              "Vehículo no encontrado",
                              "No existe un vehículo con el ID suministrado."
                      ));
    }

    @Override
    public BasicVehicleDTO createOrUpdate(BasicVehicleDTO vehicleDTO) {
        Vehicle vehicle = mapper.convertValue(vehicleDTO, Vehicle.class);
        Vehicle newVehicle = vehicleIRepository.createOrUpdate(vehicle);

        Function<Service, Service> addVehicleIdToService = service -> {
            service.setVehicleID(newVehicle.getId());
            return serviceRepository.createOrUpdate(service);
        };

        newVehicle.setServices(getMappedServices(vehicle, addVehicleIdToService));
        return mapper.convertValue(newVehicle, VehicleDTO.class);
    }

    @Override
    public List<BasicVehicleDTO> findByDate(LocalDate since, LocalDate to) {
        if (Objects.isNull(since)) {
            since = LocalDate.parse("1970-01-01");
        }

        if (Objects.isNull(to)) {
            to = LocalDate.now();
        }

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
        List<Vehicle> vehicles = vehicleIRepository.findByPrice(since, to, currency);
        if (vehicles.isEmpty()) {
            throw new NoSuchVehicleException("No encontramos vehículos en ese rango de precios");
        }

        return vehicles.stream()
                       .map(vehicle -> mapper.convertValue(vehicle, BasicVehicleDTO.class))
                       .toList();
    }

    @Override
    public void deleteOne(Long id) {
        Vehicle deletedVehicle = vehicleIRepository.deleteOne(id);

        if (Objects.isNull(deletedVehicle)) {
            throw new NotFoundException(
                    "No fue posible eliminar el vehiculo",
                    "No existe ningún vehiculo con el ID suministrado"
            );
        }

        List<Service> deletedServices = ((ServiceRepository) serviceRepository).deleteMany(id);

        if (Objects.isNull(deletedServices)) {
            throw new NotFoundException(
                    "Servicios no encontrados",
                    "No existen los servicios asociados al vehículo que se intenta eliminar"
            );
        }
    }

    private static List<Service> getMappedServices(Vehicle vehicle, Function<Service, Service> addVehicleIdToService) {
        return vehicle.getServices()
                      .stream()
                      .map(addVehicleIdToService)
                      .toList();
    }
}