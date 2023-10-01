package com.concesionaria.controlleres;

import com.concesionaria.dto.response.BasicVehicleDTO;
import com.concesionaria.dto.response.VehicleDTO;
import com.concesionaria.services.IService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/api/vehicles")
@CrossOrigin(
        originPatterns = {"*"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class VehiclesRestController {

    IService<BasicVehicleDTO, VehicleDTO> vehicleService;

    public VehiclesRestController(@Qualifier("vehicleService") IService<BasicVehicleDTO, VehicleDTO> vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/")
    public ResponseEntity<List<BasicVehicleDTO>> getAllVehicles() {
        return new ResponseEntity<>(vehicleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(vehicleService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<BasicVehicleDTO>> findByDate(@RequestParam String since, @RequestParam String to) {

        LocalDate initialDate = LocalDate.parse(since);
        LocalDate finalDate = LocalDate.parse(to);

        return new ResponseEntity<>(vehicleService.findByDate(initialDate, finalDate), HttpStatus.FOUND);
    }

    @GetMapping("/prices")
    public ResponseEntity<List<BasicVehicleDTO>> findByPrice(
            @RequestParam Integer since, @RequestParam Integer to, @RequestParam String currency
    ) {
        return new ResponseEntity<>(vehicleService.filterByPrice(since, to, currency), HttpStatus.FOUND);
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<VehicleDTO> createOrUpdate(@RequestBody VehicleDTO vehicle) {
        return new ResponseEntity<>(vehicleService.createOrUpdate(vehicle), HttpStatus.CREATED);
    }


}