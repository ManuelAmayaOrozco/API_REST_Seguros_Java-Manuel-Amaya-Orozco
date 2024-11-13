package com.es.segurosinseguros.Service;

import com.es.segurosinseguros.DTO.SeguroDTO;
import com.es.segurosinseguros.Exception.BadRequestException;
import com.es.segurosinseguros.Exception.InternalServerErrorException;
import com.es.segurosinseguros.Exception.NotFoundException;
import com.es.segurosinseguros.Model.Seguro;
import com.es.segurosinseguros.Repository.AsistenciaMedicaRepository;
import com.es.segurosinseguros.Repository.SeguroRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class SeguroService {

    private final SeguroRepository seguroRepository;
    private final AsistenciaMedicaRepository asistenciaMedicaRepository;

    public SeguroService(SeguroRepository seguroRepository, AsistenciaMedicaRepository asistenciaMedicaRepository) {
        this.seguroRepository = seguroRepository;
        this.asistenciaMedicaRepository = asistenciaMedicaRepository;
    }

    public SeguroDTO getById(String id) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        Seguro s = null;
        try {
            s = seguroRepository
                    .findById(idL)
                    .orElse(null);
        } catch (Exception e) {
            throw new InternalServerErrorException("Un error inesperado ha ocurrido al buscar el seguro por su ID.");
        }

        if(s == null) {
            throw new NotFoundException("No se encuentra ningún seguro con el ID especificado.");
        } else {
            // 3 Convertir s (Seguro) a SeguroDTO
            return mapToDTO(s);
        }

    }

    private SeguroDTO mapToDTO(Seguro seguro) {
        SeguroDTO seguroDTO = new SeguroDTO();
        seguroDTO.setIdSeguro(seguro.getIdSeguro());
        seguroDTO.setNif(seguro.getNif());
        seguroDTO.setNombre(seguro.getNombre());
        seguroDTO.setApe1(seguro.getApe1());
        seguroDTO.setApe2(seguro.getApe2());
        seguroDTO.setEdad(seguro.getEdad());
        seguroDTO.setNumHijos(seguro.getNumHijos());
        seguroDTO.setFechaCreacion(seguro.getFechaCreacion());
        seguroDTO.setSexo(seguro.getSexo());
        seguroDTO.setCasado(seguro.isCasado());
        seguroDTO.setEmbarazada(seguro.isEmbarazada());
        return seguroDTO;
    }

    private Seguro mapToSeguro(SeguroDTO seguroDTO) {
        Seguro seguro = new Seguro();
        seguro.setIdSeguro(seguroDTO.getIdSeguro());
        seguro.setNif(seguroDTO.getNif());
        seguro.setNombre(seguroDTO.getNombre());
        seguro.setApe1(seguroDTO.getApe1());
        seguro.setApe2(seguroDTO.getApe2());
        seguro.setEdad(seguroDTO.getEdad());
        seguro.setNumHijos(seguroDTO.getNumHijos());
        seguro.setFechaCreacion(seguroDTO.getFechaCreacion());
        seguro.setSexo(seguroDTO.getSexo());
        seguro.setCasado(seguroDTO.isCasado());
        seguro.setEmbarazada(seguroDTO.isEmbarazada());
        return seguro;
    }
}
