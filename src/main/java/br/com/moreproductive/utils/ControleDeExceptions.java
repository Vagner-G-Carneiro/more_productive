package br.com.moreproductive.utils;

import br.com.moreproductive.exceptions.InformacaoNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControleDeExceptions
{
    @ExceptionHandler(InformacaoNaoEncontradaException.class)
    public ResponseEntity<String> handleInformacaoNaoEncontrada(InformacaoNaoEncontradaException exception)
    {
        return new ResponseEntity<>("Erro ao buscar os dados solicitados" + exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handGenericException(Exception exception)
    {
        return new ResponseEntity<>("Erro inesperado no servidor." + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
