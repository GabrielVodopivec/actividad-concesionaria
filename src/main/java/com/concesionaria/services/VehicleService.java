package com.concesionaria.services;

import com.concesionaria.models.Vehicle;
import com.concesionaria.repositories.IRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vehicleService")
public class VehicleService implements IService<Vehicle> {
    IRepository<Vehicle> vehicleIRepository;

    public VehicleService(IRepository<Vehicle> vehicleIRepository) {
        this.vehicleIRepository = vehicleIRepository;
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleIRepository.findAll();
    }

    @Override
    public Vehicle findOne(Long id) {
        return vehicleIRepository.findById(id);
    }
}