package com.concesionaria.repositories;

import com.concesionaria.models.Service;
import com.concesionaria.models.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VehicleRepository implements IRepository<Vehicle> {
    private final List<Vehicle> vehicles = new ArrayList<>();

    public VehicleRepository() {
        Service service = new Service();
        service.setDate(new Date());
        service.setKilometers(150000);
        service.setDescriptions("Oil change");

        Vehicle v = new Vehicle();
        Vehicle v2 = new Vehicle();

        v.setId(1L);
        v.setBrand("Chevrolet");
        v.setModel("Corsa");
        v.setDoors(4);
        v.setNumberOfKilometers(140000);
        v.setPrice(40000);
        v.addService(service);
        v.setManufacturingDate(new Date());
        v.setCurrency("AR");

        v2.setId(2L);
        v2.setBrand("Renault");
        v2.setModel("Clio");
        v2.setDoors(4);
        v2.setNumberOfKilometers(150000);
        v2.setPrice(35000);
        v2.addService(service);
        v2.setManufacturingDate(new Date());
        v2.setCurrency("AR");

        vehicles.add(v);
        vehicles.add(v2);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicles;
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicles.stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst().orElse(null);
    }
}