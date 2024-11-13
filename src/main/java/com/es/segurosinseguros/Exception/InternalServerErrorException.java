package com.es.segurosinseguros.Exception;

public class InternalServerErrorException extends RuntimeException{

    private static final String DESCRIPCION = "Internal Server Error (500)";

    public InternalServerErrorException(String mensaje) {

        super(DESCRIPCION + ". " + mensaje);

    }

}
