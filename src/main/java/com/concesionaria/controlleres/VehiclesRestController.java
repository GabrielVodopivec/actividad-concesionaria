package com.concesionaria.controlleres;

import com.concesionaria.dto.response.VehicleDTO;
import com.concesionaria.dto.response.VehicleBrandModelDTO;
import com.concesionaria.models.Vehicle;
import com.concesionaria.services.IService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/vehicles")
public class VehiclesRestController {


    IService<Vehicle> vehicleService;
    IService<VehicleDTO> vehicleServiceDTO;
    IService<VehicleBrandModelDTO> vehicleTestServiceDTO;

    public VehiclesRestController(
            @Qualifier("vehicleService") IService<Vehicle> vehicleService,
            @Qualifier("vehicleServiceDTO") IService<VehicleDTO> vehicleServiceDTO,
            @Qualifier("vehicleTestServiceDTO") IService<VehicleBrandModelDTO> vehicleTestServiceDTO
    ) {
        this.vehicleService        = vehicleService;
        this.vehicleServiceDTO     = vehicleServiceDTO;
        this.vehicleTestServiceDTO = vehicleTestServiceDTO;
    }

    @GetMapping("/")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return new ResponseEntity<>(vehicleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/dto")
    public ResponseEntity<List<VehicleDTO>> getAllVehiclesDTO() {
        return new ResponseEntity<>(vehicleServiceDTO.findAll(), HttpStatus.OK);
    }

    @GetMapping("/brand-model/dto")
    public ResponseEntity<List<VehicleBrandModelDTO>> getAllTestVehicles() {
        return new ResponseEntity<>(vehicleTestServiceDTO.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Vehicle findOne(@PathVariable Long id) {
        return vehicleService.findOne(id);
    }


    @GetMapping("/brand-model/dto/{id}")
    public ResponseEntity<Vehicle> findOneDTO(@PathVariable Long id) {
        return new ResponseEntity<>(vehicleService.findOne(id), HttpStatus.OK);
    }
}