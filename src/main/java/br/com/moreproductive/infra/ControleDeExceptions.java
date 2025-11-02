package br.com.moreproductive.infra;

import br.com.moreproductive.exceptions.InformacaoNaoEncontradaException;
import br.com.moreproductive.exceptions.PermissaoNegada;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControleDeExceptions
{
    @ExceptionHandler(InformacaoNaoEncontradaException.class)
    public ResponseEntity<RestPadraoRespostaApi> responderInformacaoNaoEncontradaException(InformacaoNaoEncontradaException exception, HttpServletRequest request)
    {
        RestPadraoRespostaApi respostaApi = new RestPadraoRespostaApi("marca:more-productive:informacao-nao-encontrada",
                false, "Informacao nao encontrada", 404, exception.getMessage(), request.getMethod(), request.getRequestURI(),
                null, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return new ResponseEntity<>(respostaApi, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> responderException(Exception exception)
    {
        return new ResponseEntity<>("Erro inesperado no servidor." + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PermissaoNegada.class)
    public ResponseEntity<RestPadraoRespostaApi> responderPermissaoNegadaExcepion(PermissaoNegada exception,  HttpServletRequest request)
    {
        RestPadraoRespostaApi respostaApi = new RestPadraoRespostaApi("marca:more-productive:permissao-negada",
                false, "Permissao Negada", 403, exception.getMessage(), request.getMethod(), request.getRequestURI(),
                null, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return new ResponseEntity<>(respostaApi, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestPadraoRespostaApi> responderMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request)
    {

        Map<String, String> errosDeValidacao = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((erro) -> {
            String campoInvalido = ((FieldError)erro).getField();
            String menssagemErro = erro.getDefaultMessage();
            errosDeValidacao.put(campoInvalido, menssagemErro);
        });

        RestPadraoRespostaApi respostaApi = new RestPadraoRespostaApi(
                "marca:more-productive:erro-validacao",false, "Campos inválidos", 400, "Um ou mais campos foram negados pela API por falta de coerência com as regras de negócio",
                request.getMethod(), request.getRequestURI(), errosDeValidacao,LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        );
        return new ResponseEntity<>(respostaApi, HttpStatus.BAD_REQUEST);
    }
}
