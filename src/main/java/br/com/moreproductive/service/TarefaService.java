package br.com.moreproductive.service;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.entities.Tarefa;
import br.com.moreproductive.exceptions.PersistenciaException;
import br.com.moreproductive.repository.TarefaRepository;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository)
    {
        this.tarefaRepository = tarefaRepository;
    }

    public TarefaDTO salvar(TarefaDTO novaTarefa)
    {
        try
        {
            Tarefa tarefa = new Tarefa(novaTarefa);
            tarefa = this.tarefaRepository.save(tarefa);
            return new TarefaDTO(tarefa);
        } catch (Exception e) {
            throw new PersistenciaException("Erro ao persistir tarefa no banco: " + e.getMessage());
        }
    }

}
