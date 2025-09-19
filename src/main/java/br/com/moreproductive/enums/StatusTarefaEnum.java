package br.com.moreproductive.enums;

public enum StatusTarefaEnum {
    ATRASADA(1),
    PENDENTE(2),
    CONCLUIDA(3);

    private final int status;

    StatusTarefaEnum(int status)
    {
        this.status = status;
    }

    public int getOrdenacaoSimples()
    {
        return status;
    }
}
