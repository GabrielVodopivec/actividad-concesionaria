package com.concesionaria.errorHandlers;

import com.concesionaria.exceptions.NoSuchVehicleException;
import com.concesionaria.exceptions.NotFoundException;
import com.concesionaria.exceptions.TiempoDeEsperaExcedidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;

@ControllerAdvice
@SuppressWarnings("unused")
public class HandlerException {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> typeMismatchException(Exception e) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Escribí bin bobo");
        response.setCause(e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> vehicleNotFound(NotFoundException nfe) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(nfe.getMessage());
        response.setCause(nfe.getDetail());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> dateParseException(Exception e) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getMessage());
        response.setCause("Esperamos recibir la fecha en formato yyyy-MM-dd");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> missingParametersException(Exception e) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Faltan parámetros en la url");
        response.setCause(
                "No encontramos los parámetros since y to en la URL, " +
                "/v1/api/vehicles/dates?since=initialDate&to=finalDate");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchVehicleException.class)
    public ResponseEntity<ErrorResponse> noSuchVehiclesException(Exception e) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(e.getMessage());
        response.setCause("Los vehículos de la concesionaria están fuera del rango solicitado");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> notReadableException(Exception e) {
        ErrorResponse response = new ErrorResponse();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("No pudimos procesar la solicitud");
        response.setCause(
                "Hay al menos un campo cuyo tipo de dato no corresponde con el requerido. Revisá la documentación que" +
                " para algo está");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(Exception e) {
        ErrorResponse response = new ErrorResponse();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Recibimos alguna de las fechas en formato incorrecto. Revisar la información enviada");
        response.setCause("No fue posible convertir el string en una fecha.");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TiempoDeEsperaExcedidoException.class)
    public ResponseEntity<ErrorResponse> tiempoDeEsperaExcedidoException(Exception e) {
        ErrorResponse response = new ErrorResponse();

        response.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
        response.setMessage(e.getMessage());
        response.setCause("El repositorio tardó una banda en responder.");

        return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT);
    }


}