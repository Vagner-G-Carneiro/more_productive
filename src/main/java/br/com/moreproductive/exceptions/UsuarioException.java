package br.com.moreproductive.exceptions;

public class UsuarioException extends RuntimeException {
    private static final String MENSAGEM_PADRAO = "Erro ao encontrar usuário";
    public UsuarioException() {
        super(MENSAGEM_PADRAO);
    }
    public UsuarioException(String message) {
        super(message);
    }
}
