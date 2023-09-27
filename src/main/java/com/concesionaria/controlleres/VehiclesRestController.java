package com.concesionaria.controlleres;

import com.concesionaria.models.Vehicle;
import com.concesionaria.services.IService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/vehicles")
public class VehiclesRestController {


    IService<Vehicle> vehicleIService;

    public VehiclesRestController(@Qualifier("vehicleService") IService<Vehicle> vehicleIService) {
        this.vehicleIService = vehicleIService;
    }

    @GetMapping("/")
    public List<Vehicle> getAllVehicles() {
        return vehicleIService.findAll();
    }

    @GetMapping("/{id}")
    public Vehicle findOne(@PathVariable Long id) {
        return vehicleIService.findOne(id);
    }


}