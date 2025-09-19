package br.com.moreproductive.enums;

public enum PrioridadeTarefaEnum {
    BAIXA(1),
    MEDIA(2),
    ALTA(3),
    MUITO_ALTA(4);

    private final int prioridade;

    PrioridadeTarefaEnum(int prioridade)
    {
        this.prioridade = prioridade;
    }

    public int getPrioridade()
    {
        return prioridade;
    }
}
