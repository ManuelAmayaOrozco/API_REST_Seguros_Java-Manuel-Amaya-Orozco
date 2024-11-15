package com.es.segurosinseguros.Controller;

import com.es.segurosinseguros.DTO.AsistenciaMedicaDTO;
import com.es.segurosinseguros.Exception.BadRequestException;
import com.es.segurosinseguros.Exception.NotFoundException;
import com.es.segurosinseguros.Service.AsistenciaMedicaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asistencias")
public class AsistenciaMedicaController {

    private final AsistenciaMedicaService asistenciaMedicaService;

    public AsistenciaMedicaController(AsistenciaMedicaService asistenciaMedicaService) {
        this.asistenciaMedicaService = asistenciaMedicaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsistenciaMedicaDTO> getById(
            @PathVariable String id
    ) {

        //1º Compruebo que el id no es null
        if (id == null) {
            //LANZO UNA EXCEPCION PROPIA
            /*
            a) Qué código de estado devolveríais -> BAD_REQUEST (400)
            b) Qué información daríais al cliente
            -> Un mensaje: "El ID no tiene un formato válido."
            -> La URI: localhost:8080/asistencias/x
            c) Nombre a nuestra excepción -> BadRequestException
             */
            throw new BadRequestException("El ID no tiene un formato válido.");
        }

        //2º Si no viene vacio, llamo al Service
        AsistenciaMedicaDTO a = asistenciaMedicaService.getById(id);

        //3º Compruebo la validez de a para devolver una respuesta
        if (a == null) {
            //LANZO UNA EXCEPCION PROPIA
            /*
            a) Qué código de estado devolveríais -> NOT_FOUND (404)
            b) Qué información daríais al cliente
            -> Un mensaje: "No se encuentra ninguna asistencia médica con el NIF especificado."
            -> La URI: localhost:8080/asistencias/x
            c) Nombre a nuestra excepción -> BadRequestException
             */
            throw new NotFoundException("No se encuentra ninguna asistencia médica con el NIF especificado.");
        } else {

            ResponseEntity<AsistenciaMedicaDTO> respuesta = new ResponseEntity<>(
                    a, HttpStatus.OK
            );
            return respuesta;
        }

    }
}
