package br.com.moreproductive.enums;

import jakarta.persistence.AttributeConverter;

public class StatusTarefaEnumConverter implements AttributeConverter<StatusTarefaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusTarefaEnum statusTarefaEnum) {
        if(statusTarefaEnum == null)
        {
            return StatusTarefaEnum.PENDENTE.getStatusNivel();
        }
        return statusTarefaEnum.getStatusNivel();
    }

    @Override
    public StatusTarefaEnum convertToEntityAttribute(Integer statusNivel) {
        if(statusNivel == null)
        {
            return StatusTarefaEnum.PENDENTE;
        }

        for(StatusTarefaEnum statusTarefaEnum : StatusTarefaEnum.values())
        {
            if(statusTarefaEnum.getStatusNivel() == statusNivel)
            {
                return statusTarefaEnum;
            }
        }
        throw new IllegalArgumentException("Status de tarefa invalida para o nivel: " + statusNivel);
    }
}
