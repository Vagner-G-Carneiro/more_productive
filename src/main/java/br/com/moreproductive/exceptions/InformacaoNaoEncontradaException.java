package br.com.moreproductive.exceptions;

public class InformacaoNaoEncontradaException extends RuntimeException {
    public InformacaoNaoEncontradaException(String message) {
        super(message);
    }
}
