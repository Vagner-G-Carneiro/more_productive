package br.com.moreproductive.service;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.entities.Tarefa;
import br.com.moreproductive.exceptions.InformacaoNaoEncontradaException;
import br.com.moreproductive.exceptions.PersistenciaException;
import br.com.moreproductive.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<TarefaDTO> buscarTodasAsTarefas(int usuarioId)
    {
        try
        {
            List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdOrderByDataLimite(usuarioId);
            return tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).toList();
        } catch (Exception ex) {
            throw new InformacaoNaoEncontradaException("Erro ao encontrar as Tarefas do Usuario: " + usuarioId);
        }
    }

}
