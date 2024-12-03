package com.es.segurosinseguros.DTO;

import com.es.segurosinseguros.Model.Seguro;

import java.time.LocalDate;
import java.time.LocalTime;

public class AsistenciaMedicaDTO {

    private Long idAsistenciaMedica;
    private Long idSeguro;
    private String breveDescripcion;
    private String lugar;
    private String explicacion;
    private String tipoAsistencia;
    private LocalDate fecha;
    private LocalTime hora;
    private double importe;

    //Constructores

    public AsistenciaMedicaDTO(Long idAsistenciaMedica, Long idSeguro, String breveDescripcion, String lugar, String explicacion, String tipoAsistencia, LocalDate fecha, LocalTime hora, double importe) {
        this.idAsistenciaMedica = idAsistenciaMedica;
        this.idSeguro = idSeguro;
        this.breveDescripcion = breveDescripcion;
        this.lugar = lugar;
        this.explicacion = explicacion;
        this.tipoAsistencia = tipoAsistencia;
        this.fecha = fecha;
        this.hora = hora;
        this.importe = importe;
    }

    public AsistenciaMedicaDTO(Long idSeguro, String breveDescripcion, String lugar, String explicacion, String tipoAsistencia, LocalDate fecha, LocalTime hora, double importe) {
        this.idSeguro = idSeguro;
        this.breveDescripcion = breveDescripcion;
        this.lugar = lugar;
        this.explicacion = explicacion;
        this.tipoAsistencia = tipoAsistencia;
        this.fecha = fecha;
        this.hora = hora;
        this.importe = importe;
    }

    public AsistenciaMedicaDTO(String breveDescripcion, String lugar, String explicacion, String tipoAsistencia, LocalDate fecha, LocalTime hora, double importe) {
        this.breveDescripcion = breveDescripcion;
        this.lugar = lugar;
        this.explicacion = explicacion;
        this.tipoAsistencia = tipoAsistencia;
        this.fecha = fecha;
        this.hora = hora;
        this.importe = importe;
    }

    public AsistenciaMedicaDTO() {}

    //Getters y Setters

    public Long getIdAsistenciaMedica() {
        return idAsistenciaMedica;
    }

    public void setIdAsistenciaMedica(Long idAsistenciaMedica) {
        this.idAsistenciaMedica = idAsistenciaMedica;
    }

    public Long getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(Long idSeguro) {
        this.idSeguro = idSeguro;
    }

    public String getBreveDescripcion() {
        return breveDescripcion;
    }

    public void setBreveDescripcion(String breveDescripcion) {
        this.breveDescripcion = breveDescripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }

    public String getTipoAsistencia() {
        return tipoAsistencia;
    }

    public void setTipoAsistencia(String tipoAsistencia) {
        this.tipoAsistencia = tipoAsistencia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
