package com.concesionaria.repositories;

import com.concesionaria.models.Service;
import com.concesionaria.models.Vehicle;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@Primary
public class VehicleRepository implements IRepository<Vehicle> {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private static long lastId;
    public VehicleRepository() {
        Service service = new Service();
        service.setDate(LocalDate.parse("2020-10-25"));
        service.setKilometers(150000);
        service.setDescriptions("Oil change");

        Vehicle v = new Vehicle();
        Vehicle v2 = new Vehicle();

        v.setId(++lastId);
        v.setBrand("Chevrolet");
        v.setModel("Corsa");
        v.setDoors(4);
        v.setNumberOfKilometers(140000);
        v.setPrice(40000);
        v.addService(service);
        v.setManufacturingDate(LocalDate.now());
        v.setCurrency("AR");

        v2.setId(++lastId);
        v2.setBrand("Renault");
        v2.setModel("Clio");
        v2.setDoors(4);
        v2.setNumberOfKilometers(150000);
        v2.setPrice(35000);
        v2.addService(service);
        v2.setManufacturingDate(LocalDate.now());
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

    @Override
    public Vehicle createOrUpdate(Vehicle vehicle) {
        int index = vehicles.indexOf(vehicle);
        if (index == -1) {
            vehicle.setId(++lastId);
            vehicles.add(vehicle);
        } else {
            vehicles.set(index, vehicle);
        }
        return vehicle;
    }

    @Override
    public List<Vehicle> findByDate(LocalDate initialDate, LocalDate finalDate) {
        List<Vehicle> resultList = new ArrayList<>();
        for (Vehicle v: vehicles) {
            LocalDate date = v.getManufacturingDate();
            if (date.isAfter(initialDate) && date.isBefore(finalDate)) {
                resultList.add(v);
            }
        }
        return resultList;
    }
}