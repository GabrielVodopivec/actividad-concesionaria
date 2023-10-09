package com.concesionaria.controlleres;

import com.concesionaria.dto.request.DatesDTO;
import com.concesionaria.dto.request.PricesDTO;
import com.concesionaria.dto.request.vehicle.BasicVehicleDTO;
import com.concesionaria.dto.response.DeleteResponseDTO;
import com.concesionaria.dto.request.vehicle.VehicleDTO;
import com.concesionaria.dto.response.ResultListResponseDTO;
import com.concesionaria.dto.response.SingleResultResponseDTO;
import com.concesionaria.services.IService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/vehicles")
@CrossOrigin(
        originPatterns = {"*"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class VehiclesRestController {

    private final IService<BasicVehicleDTO> vehicleService;

    public VehiclesRestController(@Qualifier("vehicleService") IService<BasicVehicleDTO> vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/")
    public ResponseEntity<ResultListResponseDTO<BasicVehicleDTO>> getAllVehicles() {
        List<BasicVehicleDTO> result = vehicleService.findAll();
        return getResponseEntity(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResultResponseDTO<BasicVehicleDTO>> findOne(@PathVariable Long id) {
        HttpStatus status = HttpStatus.OK;
        BasicVehicleDTO result = vehicleService.findOne(id);
        SingleResultResponseDTO<BasicVehicleDTO> response = new SingleResultResponseDTO<>();

        response.setStatus(status.value());
        response.setMessage("Solicitud procesada con éxito");
        response.setResult(result);

        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/dates")
    public ResponseEntity<ResultListResponseDTO<BasicVehicleDTO>> findByDate(@ModelAttribute DatesDTO dates) {
        List<BasicVehicleDTO> result = vehicleService.findByDate(dates.getSince(), dates.getTo());
        return getResponseEntity(result);
    }

    @GetMapping("/prices")
    public ResponseEntity<ResultListResponseDTO<BasicVehicleDTO>> findByPrice(@ModelAttribute PricesDTO prices) {
        return getResponseEntity(vehicleService.filterByPrice(prices.getSince(), prices.getTo(), prices.getCurrency()));
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<BasicVehicleDTO> createOrUpdate(@RequestBody VehicleDTO vehicle) {
        return new ResponseEntity<>(vehicleService.createOrUpdate(vehicle), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponseDTO> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteOne(id);
        HttpStatus status = HttpStatus.OK;
        DeleteResponseDTO response = new DeleteResponseDTO(
                status.value(), "Vehículo eliminado correctamente"
        );
        return new ResponseEntity<>(response, status);
    }

    private static ResponseEntity<ResultListResponseDTO<BasicVehicleDTO>> getResponseEntity(List<BasicVehicleDTO> result) {
        HttpStatus status = HttpStatus.OK;
        ResultListResponseDTO<BasicVehicleDTO> response = new ResultListResponseDTO<>();

        response.setStatus(status.value());
        response.setMessage("Solicitud procesada con éxito");
        response.setSize(result.size());
        response.setResult(result);

        return new ResponseEntity<>(response, status);
    }
}