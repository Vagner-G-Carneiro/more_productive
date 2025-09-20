package br.com.moreproductive.enums;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class PrioridadeTarefaEnumDeserializer extends JsonDeserializer<PrioridadeTarefaEnum> {
    @Override
    public PrioridadeTarefaEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String texto = jsonParser.getText();

        if(texto.trim().isEmpty())
        {
            return null;
        }

        try
        {
            return PrioridadeTarefaEnum.valueOf(texto.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Parametro invalido para prioridade: " + texto + " " + e.getMessage());
        }
    }
}
