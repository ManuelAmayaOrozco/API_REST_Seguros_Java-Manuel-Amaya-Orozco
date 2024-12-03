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

    public AsistenciaMedicaDTO create(String idSeguro, AsistenciaMedicaDTO asistenciaMedicaDTO) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(idSeguro);

            //Comprobar que el Seguro existe
            List<Seguro> listaSeg = seguroRepository.findAll();

            boolean existe = false;

            for (Seguro s: listaSeg) {

                if (idL == s.getIdSeguro()) {

                    existe = true;
                    break;

                }

            }

            if (!existe) {

                throw new BadRequestException("El ID del seguro proveido no coincide con ningún seguro.");

            } else {

                asistenciaMedicaDTO.setIdSeguro(idL);

            }

        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        //Comprobación Breve Descripción
        if (asistenciaMedicaDTO.getBreveDescripcion().isEmpty()) {

            throw new BadRequestException("El campo breveDescripción no puede estar vacío.");

        }

        //Comprobación Lugar
        if (asistenciaMedicaDTO.getLugar().isEmpty()) {

            throw new BadRequestException("El campo lugar no puede estar vacío.");

        }

        //Comprobación Explicación
        if (asistenciaMedicaDTO.getExplicacion().isEmpty()) {

            throw new BadRequestException("El campo explicación no puede estar vacío.");

        }

        //Comprobación tipoAsistencia
        if (asistenciaMedicaDTO.getTipoAsistencia() == null) {

            throw new BadRequestException("El campo tipoAsistencia no puede ser nulo.");

        }

        //Comprobación tipoAsistencia
        if (asistenciaMedicaDTO.getFecha() == null) {

            throw new BadRequestException("El campo fecha no puede ser nulo.");

        }

        //Comprobación tipoAsistencia
        if (asistenciaMedicaDTO.getHora() == null) {

            throw new BadRequestException("El campo hora no puede ser nulo.");

        }

        //Comprobación Importe
        if (asistenciaMedicaDTO.getImporte() < 0) {

            throw new BadRequestException("El campo importe debe ser mayor que 0.");

        }

        AsistenciaMedica a = mapToAsistenciaMedica(asistenciaMedicaDTO);

        a = asistenciaMedicaRepository.save(a);

        return mapToDTO(a);

    }

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

    public AsistenciaMedicaDTO update(String id, AsistenciaMedicaDTO asistenciaMedicaDTO) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(id);

            //Comprobar que el Seguro existe
            List<Seguro> listaSeg = seguroRepository.findAll();

            boolean existe = false;

            for (Seguro s: listaSeg) {

                if (asistenciaMedicaDTO.getIdSeguro() == s.getIdSeguro()) {

                    existe = true;
                    break;

                }

            }

            if (!existe) {

                throw new BadRequestException("El ID del seguro proveido no coincide con ningún seguro.");

            }

        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        //Comprobación Breve Descripción
        if (asistenciaMedicaDTO.getBreveDescripcion().isEmpty()) {

            throw new BadRequestException("El campo breveDescripción no puede estar vacío.");

        }

        //Comprobación Lugar
        if (asistenciaMedicaDTO.getLugar().isEmpty()) {

            throw new BadRequestException("El campo lugar no puede estar vacío.");

        }

        //Comprobación Explicación
        if (asistenciaMedicaDTO.getExplicacion().isEmpty()) {

            throw new BadRequestException("El campo explicación no puede estar vacío.");

        }

        //Comprobación tipoAsistencia
        if (asistenciaMedicaDTO.getTipoAsistencia() == null) {

            throw new BadRequestException("El campo tipoAsistencia no puede ser nulo.");

        }

        //Comprobación tipoAsistencia
        if (asistenciaMedicaDTO.getFecha() == null) {

            throw new BadRequestException("El campo fecha no puede ser nulo.");

        }

        //Comprobación tipoAsistencia
        if (asistenciaMedicaDTO.getHora() == null) {

            throw new BadRequestException("El campo hora no puede ser nulo.");

        }

        //Comprobación Importe
        if (asistenciaMedicaDTO.getImporte() < 0) {

            throw new BadRequestException("El campo importe debe ser mayor que 0.");

        }

        // Compruebo que el seguro existe en la BDD
        AsistenciaMedica a = asistenciaMedicaRepository.findById(idL).orElse(null);

        if (a == null) {

            return null;

        //Comprobación SeguroId coincide
        } else if (a.getSeguro() != seguroRepository.getReferenceById(asistenciaMedicaDTO.getIdSeguro()))     {

            throw new BadRequestException("El ID del seguro proveido no coincide con el ID del seguro original.");

        } else {

            AsistenciaMedica newA = mapToAsistenciaMedica(asistenciaMedicaDTO);

            newA.setIdAsistenciaMedica(a.getIdAsistenciaMedica());

            asistenciaMedicaRepository.save(newA);

            return mapToDTO(newA);

        }

    }

    public AsistenciaMedicaDTO delete(String id) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
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

            AsistenciaMedicaDTO asistenciaMedicaDTO = mapToDTO(a);

            asistenciaMedicaRepository.delete(a);

            return asistenciaMedicaDTO;

        }

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
