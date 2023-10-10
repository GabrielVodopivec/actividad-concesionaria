package com.concesionaria.services;

import com.concesionaria.dto.request.vehicle.BasicVehicleDTO;
import com.concesionaria.dto.request.vehicle.VehicleDTO;
import com.concesionaria.exceptions.NoSuchVehicleException;
import com.concesionaria.exceptions.NotFoundException;
import com.concesionaria.exceptions.TiempoDeEsperaExcedidoException;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
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
//        -------------------Simulación de falla por tiempo excedido-----------------
        AtomicReference<List<BasicVehicleDTO>> resultReference = new AtomicReference<>();

//        Creo un hilo para realizar pedirle la lista al repositorio.
        Thread thread = getThread(resultReference);

        try {
//            Espero un máximo de 3 segundos para unir el hilo creado con el
//            actual
            thread.join(TimeUnit.SECONDS.toMillis(3));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<BasicVehicleDTO> result = resultReference.get();

//        Como el repositorio tiene el hilo dormido por 5 segundos el resultdo es null
//        y se lanza la excepción.
        if (result == null) {
            throw new TiempoDeEsperaExcedidoException("Tiempo de espera excedido");
        }

        return result;
//        -----------------------------------------------------------------------------

//        Implementación normal
//        Comentar la implementación de arriba para obtener la Lista inicial;

//        return vehicleIRepository
//                       .findAll()
//                       .stream()
//                       .map(vehicle -> mapper.convertValue(vehicle, BasicVehicleDTO.class))
//                       .toList();
    }

    private Thread getThread(AtomicReference<List<BasicVehicleDTO>> resultReference) {
        Thread thread = new Thread(() -> {
            List<BasicVehicleDTO> result = vehicleIRepository
                                                   .findAll()
                                                   .stream()
                                                   .map(vehicle -> mapper.convertValue(vehicle, BasicVehicleDTO.class))
                                                   .toList();

            resultReference.set(result);
        });

        thread.start();
        return thread;
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