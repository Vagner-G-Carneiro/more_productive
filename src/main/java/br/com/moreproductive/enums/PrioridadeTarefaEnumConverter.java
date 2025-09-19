package br.com.moreproductive.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PrioridadeTarefaEnumConverter implements AttributeConverter<PrioridadeTarefaEnum, Integer>
{

    @Override
    public Integer convertToDatabaseColumn(PrioridadeTarefaEnum prioridadeTarefaEnum) {
        if(prioridadeTarefaEnum == null)
        {
            return PrioridadeTarefaEnum.BAIXA.getPrioridadeNivel();
        }
        return prioridadeTarefaEnum.getPrioridadeNivel();
    }

    @Override
    public PrioridadeTarefaEnum convertToEntityAttribute(Integer prioridadeNivel) {
        if(prioridadeNivel == null)
        {
            return PrioridadeTarefaEnum.BAIXA;
        }

        for(PrioridadeTarefaEnum prioridadeTarefaEnum : PrioridadeTarefaEnum.values())
        {
            if(prioridadeTarefaEnum.getPrioridadeNivel() == prioridadeNivel)
            {
                return prioridadeTarefaEnum;
            }
        }

        throw new IllegalArgumentException("Nivel de prioridade inválido: " + prioridadeNivel);
    }
}
