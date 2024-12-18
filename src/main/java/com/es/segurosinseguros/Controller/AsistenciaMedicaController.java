package com.es.segurosinseguros.Controller;

import com.es.segurosinseguros.DTO.AsistenciaMedicaDTO;
import com.es.segurosinseguros.DTO.SeguroDTO;
import com.es.segurosinseguros.Exception.BadRequestException;
import com.es.segurosinseguros.Exception.InternalServerErrorException;
import com.es.segurosinseguros.Exception.NotFoundException;
import com.es.segurosinseguros.Model.AsistenciaMedica;
import com.es.segurosinseguros.Service.AsistenciaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asistencias")
public class AsistenciaMedicaController {

    @Autowired
    private AsistenciaMedicaService asistenciaMedicaService;

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

    @GetMapping
    public ResponseEntity<List<AsistenciaMedicaDTO>> getAll() {

        List<AsistenciaMedicaDTO> a = asistenciaMedicaService.getAll();

        if(a == null) {

            throw new NotFoundException("No se encuentra ninguna asistencia médica para mostrar.");

        } else {
            ResponseEntity<List<AsistenciaMedicaDTO>> respuesta = new ResponseEntity<List<AsistenciaMedicaDTO>>(
                    a, HttpStatus.OK
            );
            return respuesta;
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<AsistenciaMedicaDTO> update(
            @RequestBody AsistenciaMedicaDTO asistenciaMedicaDTO,
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
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        //2º Si no viene vacio, llamo al Service
        AsistenciaMedicaDTO a = asistenciaMedicaService.update(id, asistenciaMedicaDTO);

        if(a == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar actualizar la asistencia médica.");

        } else {
            ResponseEntity<AsistenciaMedicaDTO> respuesta = new ResponseEntity<AsistenciaMedicaDTO>(
                    a, HttpStatus.OK
            );
            return respuesta;
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AsistenciaMedicaDTO> delete(
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
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        //2º Si no viene vacio, llamo al Service
        AsistenciaMedicaDTO a = asistenciaMedicaService.delete(id);

        if(a == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar eliminar la asistencia médica.");

        } else {
            ResponseEntity<AsistenciaMedicaDTO> respuesta = new ResponseEntity<AsistenciaMedicaDTO>(
                    a, HttpStatus.NO_CONTENT
            );
            return respuesta;
        }

    }
}
