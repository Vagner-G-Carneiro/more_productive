package br.com.moreproductive.enums;

public enum StatusTarefaEnum {
    ATRASADA(1),
    PENDENTE(2),
    CONCLUIDA(3);

    private final int statusNivel;

    StatusTarefaEnum(int statusNivel)
    {
        this.statusNivel = statusNivel;
    }

    public int getStatusNivel()
    {
        return statusNivel;
    }
}
