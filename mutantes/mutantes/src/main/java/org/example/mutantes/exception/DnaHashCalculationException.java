package org.example.mutantes.exception;

public class DnaHashCalculationException extends RuntimeException {

    public DnaHashCalculationException(String mensaje) {
        super(mensaje);
    }

    public DnaHashCalculationException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}