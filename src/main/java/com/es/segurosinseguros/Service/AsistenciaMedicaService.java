package com.es.segurosinseguros.Service;

import com.es.segurosinseguros.DTO.AsistenciaMedicaDTO;
import com.es.segurosinseguros.DTO.SeguroDTO;
import com.es.segurosinseguros.Exception.BadRequestException;
import com.es.segurosinseguros.Exception.InternalServerErrorException;
import com.es.segurosinseguros.Exception.NotFoundException;
import com.es.segurosinseguros.Model.AsistenciaMedica;
import com.es.segurosinseguros.Model.Seguro;
import com.es.segurosinseguros.Repository.AsistenciaMedicaRepository;
import com.es.segurosinseguros.Repository.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsistenciaMedicaService {

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private AsistenciaMedicaRepository asistenciaMedicaRepository;

    public AsistenciaMedicaDTO getById(String id) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El ID no tiene un formato válido.");
        }

        AsistenciaMedica a = null;
        try {
            a = asistenciaMedicaRepository
                    .findById(idL)
                    .orElse(null);
        } catch (Exception e) {
            throw new InternalServerErrorException("Un error inesperado ha ocurrido al buscar la asistencia médica por su ID.");
        }

        if(a == null) {
            throw new NotFoundException("No se encuentra ninguna asistencia médica con el ID especificado.");
        } else {
            // 3 Convertir a (AsistenciaMedica) a AsistenciaMedicaDTO
            return mapToDTO(a);
        }

    }

    public List<AsistenciaMedicaDTO> getAll() {

        List<AsistenciaMedicaDTO> listaDeDTOs = new ArrayList<>();

        List<AsistenciaMedica> listaAsi = asistenciaMedicaRepository.findAll();

        for (AsistenciaMedica a: listaAsi) {

            listaDeDTOs.add(mapToDTO(a));

        }

        return listaDeDTOs;

    }

    private AsistenciaMedicaDTO mapToDTO(AsistenciaMedica asistenciaMedica) {
        AsistenciaMedicaDTO asistenciaMedicaDTO = new AsistenciaMedicaDTO();
        asistenciaMedicaDTO.setIdAsistenciaMedica(asistenciaMedica.getIdAsistenciaMedica());
        asistenciaMedicaDTO.setIdSeguro(asistenciaMedica.getSeguro().getIdSeguro());
        asistenciaMedicaDTO.setBreveDescripcion(asistenciaMedica.getBreveDescripcion());
        asistenciaMedicaDTO.setLugar(asistenciaMedica.getLugar());
        asistenciaMedicaDTO.setExplicacion(asistenciaMedica.getExplicacion());
        asistenciaMedicaDTO.setTipoAsistencia(asistenciaMedica.getTipoAsistencia());
        asistenciaMedicaDTO.setFecha(asistenciaMedica.getFecha());
        asistenciaMedicaDTO.setHora(asistenciaMedica.getHora());
        asistenciaMedicaDTO.setImporte(asistenciaMedica.getImporte());
        return asistenciaMedicaDTO;
    }

    private AsistenciaMedica mapToAsistenciaMedica(AsistenciaMedicaDTO asistenciaMedicaDTO) {
        AsistenciaMedica asistenciaMedica = new AsistenciaMedica();
        asistenciaMedica.setIdAsistenciaMedica(asistenciaMedicaDTO.getIdAsistenciaMedica());
        asistenciaMedica.setSeguro(seguroRepository.getReferenceById(asistenciaMedicaDTO.getIdSeguro()));
        asistenciaMedica.setBreveDescripcion(asistenciaMedicaDTO.getBreveDescripcion());
        asistenciaMedica.setLugar(asistenciaMedicaDTO.getLugar());
        asistenciaMedica.setExplicacion(asistenciaMedicaDTO.getExplicacion());
        asistenciaMedica.setTipoAsistencia(asistenciaMedicaDTO.getTipoAsistencia());
        asistenciaMedica.setFecha(asistenciaMedicaDTO.getFecha());
        asistenciaMedica.setHora(asistenciaMedicaDTO.getHora());
        asistenciaMedica.setImporte(asistenciaMedicaDTO.getImporte());
        return asistenciaMedica;
    }

}
