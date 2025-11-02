package br.com.moreproductive.exceptions;

public class PermissaoNegadaException extends RuntimeException {
    private static final String MENSAGEM_PADRAO = "Ops, parece que você não tem permissão para isso!";
    public PermissaoNegadaException() {
        super(MENSAGEM_PADRAO);
    }
    public PermissaoNegadaException(String message) {
        super(message);
    }
}
