package com.concesionaria.repositories;

import com.concesionaria.models.Vehicle;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

@Repository
public class VehicleRepository implements IRepository<Vehicle> {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private static long lastId;

    public VehicleRepository() {
        Vehicle v = new Vehicle();
        Vehicle v2 = new Vehicle();

        v.setId(++lastId);
        v.setBrand("Chevrolet");
        v.setModel("Corsa");
        v.setDoors(4);
        v.setNumberOfKilometers(140000);
        v.setPrice(40000);
        v.setManufacturingDate(LocalDate.parse("2012-06-15"));
        v.setCurrency("AR");
        v.setCountOfOwners(3);

        v2.setId(++lastId);
        v2.setBrand("Renault");
        v2.setModel("Clio");
        v2.setDoors(4);
        v2.setNumberOfKilometers(150000);
        v2.setPrice(35000);
        v2.setManufacturingDate(LocalDate.parse("2010-10-20"));
        v2.setCurrency("AR");
        v2.setCountOfOwners(3);

        vehicles.add(v);
        vehicles.add(v2);
    }

    @Override
    public List<Vehicle> findAll() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicles.stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst();
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
    public List<Vehicle> findByDate(LocalDate since, LocalDate to) {
        Predicate<Vehicle> byDate = (vehicle) -> {
            LocalDate currentDate = vehicle.getManufacturingDate();
            return currentDate.isAfter(since) && currentDate.isBefore(to);
        };
        return vehicles.stream().filter(byDate).toList();
    }

    @Override
    public List<Vehicle> findByPrice(Integer since, Integer to, String currency) {
        Predicate<Vehicle> byPrice = (vehicle) -> {
            Integer price = vehicle.getPrice();
            String currentCurrency = vehicle.getCurrency();
            return currentCurrency.equals(currency) && price > since && price < to;
        };
        return vehicles.stream().filter(byPrice).toList();
    }

    @Override
    public Vehicle deleteOne(Long id) {
        Vehicle foundVehicle = findById(id).orElse(null);
        vehicles.remove(foundVehicle);
        return foundVehicle;
    }
}