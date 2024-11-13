package com.es.segurosinseguros.Controller;

import com.es.segurosinseguros.DTO.SeguroDTO;
import com.es.segurosinseguros.Exception.BadRequestException;
import com.es.segurosinseguros.Exception.NotFoundException;
import com.es.segurosinseguros.Service.SeguroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seguros")
public class SeguroController {

    private final SeguroService seguroService;

    public SeguroController(SeguroService seguroService) {
        this.seguroService = seguroService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguroDTO> getById(
            @PathVariable String id
    ) {

        //1º Compruebo que el id no es null
        if (id == null || id.equals("a")) {
            //LANZO UNA EXCEPCION PROPIA
            /*
            a) Qué código de estado devolveríais -> BAD_REQUEST (400)
            b) Qué información daríais al cliente
            -> Un mensaje: "El ID no tiene un formato válido."
            -> La URI: localhost:8080/seguros/x
            c) Nombre a nuestra excepción -> BadRequestException
             */
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        //2º Si no viene vacio, llamo al Service
        SeguroDTO s = seguroService.getById(id);

        //3º Compruebo la validez de s para devolver una respuesta
        if (s == null) {
            //LANZO UNA EXCEPCION PROPIA
            /*
            a) Qué código de estado devolveríais -> NOT_FOUND (404)
            b) Qué información daríais al cliente
            -> Un mensaje: "No se encuentra ningún seguro con el NIF eespecificado."
            -> La URI: localhost:8080/seguros/x
            c) Nombre a nuestra excepción -> BadRequestException
             */
            throw new NotFoundException("No se encuentra ningún seguro con el ID especificado.");
        } else {

            ResponseEntity<SeguroDTO> respuesta = new ResponseEntity<SeguroDTO>(
                    s, HttpStatus.OK
            );
            return respuesta;
        }

    }

}
