package br.com.moreproductive.enums;

public enum PrioridadeTarefaEnum {
    BAIXA(1),
    MEDIA(2),
    ALTA(3),
    MUITO_ALTA(4);

    private final int prioridadeNivel;

    PrioridadeTarefaEnum(int prioridadeNivel)
    {
        this.prioridadeNivel = prioridadeNivel;
    }

    public int getPrioridadeNivel()
    {
        return prioridadeNivel;
    }
}
