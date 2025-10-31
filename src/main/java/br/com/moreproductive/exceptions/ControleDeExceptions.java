package br.com.moreproductive.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(PermissaoNegada.class)
    public ResponseEntity<String> handGenericException(PermissaoNegada exception)
    {
        return new ResponseEntity<>("Erro de permissão." + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException exception)
    {
        Map<String, String> argumentosInvalidos = new HashMap<>();
        Map<String, Object> respostaDetalhada = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((erro) -> {
            String nomeCampoIvalido = ((FieldError) erro).getField();
            String mensagemErro = erro.getDefaultMessage();
            argumentosInvalidos.put(nomeCampoIvalido, mensagemErro);
        });

        respostaDetalhada.put("timestamp", LocalDateTime.now());
        respostaDetalhada.put("status", HttpStatus.BAD_REQUEST);
        respostaDetalhada.put("campos inválidos", argumentosInvalidos);

        return new ResponseEntity<>(respostaDetalhada, HttpStatus.BAD_REQUEST);
    }
}
