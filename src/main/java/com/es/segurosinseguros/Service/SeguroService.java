package com.es.segurosinseguros.Service;

import com.es.segurosinseguros.DTO.SeguroDTO;
import com.es.segurosinseguros.Exception.BadRequestException;
import com.es.segurosinseguros.Exception.InternalServerErrorException;
import com.es.segurosinseguros.Exception.NotFoundException;
import com.es.segurosinseguros.Model.AsistenciaMedica;
import com.es.segurosinseguros.Model.Seguro;
import com.es.segurosinseguros.Repository.AsistenciaMedicaRepository;
import com.es.segurosinseguros.Repository.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeguroService {

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private AsistenciaMedicaRepository asistenciaMedicaRepository;

    public SeguroDTO create(SeguroDTO seguroDTO) {

        //Comprobación NIF
        if (!validarNIF(seguroDTO.getNif())) {

            throw new BadRequestException("El campo NIF no tiene un formato válido.");

        }

        //Comprobación Nombre
        if (seguroDTO.getNombre().isEmpty()) {

            throw new BadRequestException("El campo Nombre no puede estar vacío.");

        }

        //Comprobación Ape1
        if (seguroDTO.getApe1().isEmpty()) {

            throw new BadRequestException("El campo Ape1 no puede estar vacío.");

        }

        //Comprobación Edad
        if (seguroDTO.getEdad() < 18) {

            throw new BadRequestException("No es posible ser menor de edad para hacer un seguro.");

        }

        //Comprobación NumHijos
        if (seguroDTO.getNumHijos() < 0) {

            throw new BadRequestException("Un seguro no puede registrar hijos si no está casado.");

        } else if (!seguroDTO.isCasado() && seguroDTO.getNumHijos() != 0) {

            throw new BadRequestException("Un seguro no puede registrar hijos si no está casado.");

        }

        //Comprobación Embarazada
        if (seguroDTO.isEmbarazada() && seguroDTO.getSexo().equalsIgnoreCase("hombre")) {

            throw new BadRequestException("El campo embarazada no puede ser true si el asegurado es hombre.");

        }

        Seguro seguro = mapToSeguro(seguroDTO);

        seguro = seguroRepository.save(seguro);

        return mapToDTO(seguro);

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

    public List<SeguroDTO> getAll() {

        List<SeguroDTO> listaDeDTOs = new ArrayList<>();

        List<Seguro> listaSeg = seguroRepository.findAll();

        for (Seguro s: listaSeg) {

            listaDeDTOs.add(mapToDTO(s));

        }

        return listaDeDTOs;

    }

    public SeguroDTO update(String id, SeguroDTO seguroDTO) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        //Comprobación NIF
        if (!validarNIF(seguroDTO.getNif())) {

            throw new BadRequestException("El campo NIF no tiene un formato válido.");

        }

        //Comprobación Nombre
        if (seguroDTO.getNombre().isEmpty()) {

            throw new BadRequestException("El campo Nombre no puede estar vacío.");

        }

        //Comprobación Ape1
        if (seguroDTO.getApe1().isEmpty()) {

            throw new BadRequestException("El campo Ape1 no puede estar vacío.");

        }

        //Comprobación Edad
        if (seguroDTO.getEdad() < 18) {

            throw new BadRequestException("No es posible ser menor de edad para hacer un seguro.");

        }

        //Comprobación NumHijos
        if (seguroDTO.getNumHijos() < 0) {

            throw new BadRequestException("Un seguro no puede registrar hijos si no está casado.");

        } else if (!seguroDTO.isCasado() && seguroDTO.getNumHijos() != 0) {

            throw new BadRequestException("Un seguro no puede registrar hijos si no está casado.");

        }

        //Comprobación Embarazada
        if (seguroDTO.isEmbarazada() && seguroDTO.getSexo().equalsIgnoreCase("hombre")) {

            throw new BadRequestException("El campo embarazada no puede ser true si el asegurado es hombre.");

        }

        // Compruebo que el seguro existe en la BDD
        Seguro s = seguroRepository.findById(idL).orElse(null);

        if (s == null) {

            return null;

        } else {

            Seguro newS = mapToSeguro(seguroDTO);

            newS.setIdSeguro(s.getIdSeguro());

            seguroRepository.save(newS);

            return mapToDTO(newS);

        }

    }

    public SeguroDTO delete(String id) {

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
            List<AsistenciaMedica> asistencias = asistenciaMedicaRepository.findAll();

            for (AsistenciaMedica a: asistencias) {

                if (s.getIdSeguro() == idL) {

                    asistenciaMedicaRepository.delete(a);

                }

            }

            SeguroDTO seguroDTO = mapToDTO(s);

            seguroRepository.delete(s);

            return seguroDTO;
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

    private Boolean validarNIF(String nif) {

        String letraMayuscula = "";

        if (nif.length() != 9 || !Character.isLetter(nif.charAt(8))) {

            return false;

        }

        letraMayuscula = (nif.substring(8)).toUpperCase();

        if (soloNumeros(nif) == true && letraNIF(nif).equals(letraMayuscula)) {

            return true;

        } else {

            return false;

        }

    }

    private Boolean soloNumeros(String nif) {

        int i, j = 0;
        String numero = "";
        String miNIF = "";
        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

        for (i = 0; i < nif.length() - 1; i++) {

            numero = nif.substring(i, i + 1);

            for (j = 0; j < unoNueve.length; j++) {

                if (numero.equals(unoNueve[j])) {

                    miNIF += unoNueve[j];

                }

            }

        }

        if (miNIF.length() != 8) {

            return false;

        } else {

            return true;

        }

    }

    private String letraNIF(String nif) {

        int miNIF = Integer.parseInt(nif.substring(0, 8));
        int resto = 0;
        String miLetra = "";
        String[] asignacionLetra = {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};

        resto = miNIF % 23;

        miLetra = asignacionLetra[resto];

        return miLetra;

    }
}
