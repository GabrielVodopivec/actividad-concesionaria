package com.concesionaria.repositories;

import com.concesionaria.models.Service;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public class ServiceRepository implements IRepository<Service> {

    private final List<Service> services;
    private static long lastId;

    public ServiceRepository() {
        this.services = new ArrayList<>();
        Service s1 = new Service(++lastId, LocalDate.parse("2015-10-22"), 95000, "Cambio de correas");
        Service s2 = new Service(++lastId, LocalDate.parse("2016-09-17"), 125000, "Alineación y balanceo");
        Service s3 = new Service(++lastId, LocalDate.parse("2017-08-03"), 138000, "Grabado de cristales");

        s1.setVehicleID(1L);
        s2.setVehicleID(1L);
        s3.setVehicleID(2L);

        this.services.add(s1);
        this.services.add(s2);
        this.services.add(s3);
    }

    @Override
    public List<Service> findAll() {
        return this.services;
    }

    @Override
    public Optional<Service> findById(Long id) {
        Predicate<Service> byId = (service) -> service.getId().equals(id);
        return services.stream().filter(byId).findFirst();
    }

    @Override
    public Service createOrUpdate(Service service) {
        int index = services.indexOf(service);
        if (index == -1) {
            service.setId(++lastId);
            services.add(service);
        } else {
            services.set(index, service);
        }
        return service;
    }

    @Override
    public List<Service> findByDate(LocalDate since, LocalDate to) {
        return null;
    }

    @Override
    public List<Service> findByPrice(Integer since, Integer to, String currency) {
        return null;
    }

    @Override
    public Service deleteOne(Long id) {
        return null;
    }

    public List<Service> deleteMany(Long vehicleId) {
        Predicate<Service> byVehicleId = service -> Objects.equals(service.getVehicleID(), vehicleId);
        List<Service> servicesToDelete = this.services.stream().filter(byVehicleId).toList();
        this.services.removeAll(servicesToDelete);
        return servicesToDelete;
    }
}