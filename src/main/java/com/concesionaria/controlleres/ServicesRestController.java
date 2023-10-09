package com.concesionaria.controlleres;

import com.concesionaria.dto.request.service.BasicServiceDTO;
import com.concesionaria.dto.response.ResultListResponseDTO;
import com.concesionaria.dto.response.SingleResultResponseDTO;
import com.concesionaria.services.IService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/api/services")
public class ServicesRestController {

    private final IService<BasicServiceDTO> service;

    public ServicesRestController(IService<BasicServiceDTO> service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<ResultListResponseDTO<BasicServiceDTO>> getAllServices() {
        ResultListResponseDTO<BasicServiceDTO> response = new ResultListResponseDTO<>();
        List<BasicServiceDTO> result = service.findAll();
        HttpStatus status = HttpStatus.OK;

        response.setStatus(status.value());
        response.setMessage("Solicitud procesada con éxito");
        response.setResult(result);
        response.setSize(result.size());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResultResponseDTO<BasicServiceDTO>> findById(@PathVariable Long id) {
        SingleResultResponseDTO<BasicServiceDTO> response = new SingleResultResponseDTO<>();
        BasicServiceDTO result = service.findOne(id);
        HttpStatus status = HttpStatus.OK;

        response.setStatus(status.value());
        response.setMessage("Solicitud procesada con éxito");
        response.setResult(result);

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}