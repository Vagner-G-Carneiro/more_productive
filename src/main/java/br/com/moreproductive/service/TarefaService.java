package br.com.moreproductive.service;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.entities.Tarefa;
import br.com.moreproductive.enums.PrioridadeTarefaEnum;
import br.com.moreproductive.enums.StatusTarefaEnum;
import br.com.moreproductive.exceptions.InformacaoNaoEncontradaException;
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

    public TarefaDTO salvar(TarefaDTO novaTarefa) throws Exception {
        try
        {
            Tarefa tarefa = new Tarefa(novaTarefa);
            tarefa = this.tarefaRepository.save(tarefa);
            return new TarefaDTO(tarefa);
        } catch (Exception e) {
            throw new Exception(" Erro ao persistir tarefa no banco: " + e.getMessage());
        }
    }

    public List<TarefaDTO> buscarOrdenadasDataLimite(int usuarioId)
    {
            List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdOrderByDataLimite(usuarioId);
            if(tarefas.isEmpty())
            {
                throw new InformacaoNaoEncontradaException(" Nenhuma tarefa encontrada com esse critério.");
            }
            return tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).toList();
    }

    public List<TarefaDTO> buscarOrdenadasDataCriacao(int usuarioId)
    {
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId);
        if(tarefas.isEmpty())
        {
            throw new InformacaoNaoEncontradaException(" Nenhuma tarefa encontrada com esse critério.");
        }
        return tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).toList();
    }

    public List<TarefaDTO> buscarOrdenadasPrioridade(int usuarioId)
    {
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdOrderByPrioridadeDesc(usuarioId);
        if(tarefas.isEmpty())
        {
            throw new InformacaoNaoEncontradaException(" Nenhuma tarefa encontrada com esse critério.");
        }
        return tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).toList();
    }

    public List<TarefaDTO> buscarOrdenadasStatus(int usuarioId)
    {
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdOrderByStatus(usuarioId);
        if(tarefas.isEmpty())
        {
            throw new InformacaoNaoEncontradaException(" Nenhuma tarefa encontrada com esse critério.");
        }
        return tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).toList();
    }

    public List<TarefaDTO> filtrarPorStatus(int usuarioId, StatusTarefaEnum status)
    {
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdAndStatus(usuarioId, status);
        if(tarefas.isEmpty())
        {
            throw new InformacaoNaoEncontradaException(" Nenhuma tarefa encontrada com esse critério.");
        }
        return tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).toList();
    }

    public List<TarefaDTO> filtrarPorPrioridade(int usuarioId, PrioridadeTarefaEnum prioridade)
    {
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdAndPrioridade(usuarioId, prioridade);
        if(tarefas.isEmpty())
        {
            throw new InformacaoNaoEncontradaException(" Nenhuma tarefa encontrada com esse critério.");
        }
        return tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).toList();
    }
}
