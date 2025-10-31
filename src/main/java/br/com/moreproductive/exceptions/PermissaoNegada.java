package br.com.moreproductive.exceptions;

public class PermissaoNegada extends RuntimeException {
    private static final String MENSAGEM_PADRAO = "Ops, parece que você não tem permissão para isso!";
    public PermissaoNegada() {
        super(MENSAGEM_PADRAO);
    }
    public PermissaoNegada(String message) {
        super(message);
    }
}
