package br.com.moreproductive.exceptions;


public class PersistenciaException extends RuntimeException {
    public PersistenciaException(String message) {
        super(message);
    }
}
