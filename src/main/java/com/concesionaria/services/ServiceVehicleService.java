package com.concesionaria.services;

import com.concesionaria.dto.request.service.BasicServiceDTO;
import com.concesionaria.dto.request.service.ServiceDTO;
import com.concesionaria.exceptions.NotFoundException;
import com.concesionaria.models.Service;
import com.concesionaria.repositories.IRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@org.springframework.stereotype.Service
public class ServiceVehicleService implements IService<BasicServiceDTO> {
    private final IRepository<Service> servicesRepository;
    private final ObjectMapper mapper;

    public ServiceVehicleService(IRepository<Service> servicesRepository) {
        this.servicesRepository = servicesRepository;
        mapper                  = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<BasicServiceDTO> findAll() {
        Function<Service, BasicServiceDTO> convertToDto =
                service -> mapper.convertValue(service, BasicServiceDTO.class);

        return servicesRepository
                       .findAll()
                       .stream()
                       .map(convertToDto)
                       .toList();
    }

    @Override
    public BasicServiceDTO findOne(Long id) {
        return servicesRepository
                       .findById(id)
                       .map(value -> mapper.convertValue(value, ServiceDTO.class))
                       .orElseThrow(() -> new NotFoundException("Servicio no encontrado", "No existe un servicio con el ID suministrado"));
    }

    @Override
    public BasicServiceDTO createOrUpdate(BasicServiceDTO basicServiceDTO) {
        return null;
    }

    @Override
    public List<BasicServiceDTO> findByDate(LocalDate since, LocalDate to) {
        return null;
    }

    @Override
    public List<BasicServiceDTO> filterByPrice(Integer since, Integer to, String currency) {
        return null;
    }

    @Override
    public void deleteOne(Long id) {

    }
}