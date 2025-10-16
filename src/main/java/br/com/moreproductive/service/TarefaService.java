package br.com.moreproductive.service;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.entities.Tarefa;
import br.com.moreproductive.enums.PrioridadeTarefaEnum;
import br.com.moreproductive.enums.StatusTarefaEnum;
import br.com.moreproductive.exceptions.InformacaoNaoEncontradaException;
import br.com.moreproductive.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            novaTarefa.setDataCriacao(LocalDateTime.now());
            Tarefa tarefa = new Tarefa(novaTarefa);
            tarefa = this.tarefaRepository.save(tarefa);
            if(tarefa.getStatus() == null || tarefa.getPrioridade() == null)
            {
                tarefa = this.tarefaRepository.findById(tarefa.getId());
            }
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

    public TarefaDTO atualizarTarefa(int id, TarefaDTO tarefaAtualizadaDTO)
    {
        try
        {
            Tarefa tarefaAntiga = this.tarefaRepository.findById(id);
            if(tarefaAntiga != null)
            {
                if(tarefaAtualizadaDTO.getDataCriacao() == null)
                {
                    tarefaAtualizadaDTO.setDataCriacao(tarefaAntiga.getDataCriacao());
                }

                Tarefa tarefaAtualizada = new Tarefa(tarefaAtualizadaDTO);
                tarefaAtualizada.setId(id);
                return new TarefaDTO(this.tarefaRepository.save(tarefaAtualizada));
            } else {
                throw new InformacaoNaoEncontradaException("Erro ao encontrar tarefa para atualizar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar tarefa." + e.getMessage());
        }
    }

    public String excluirTarefa(int id)
    {
        try
        {
            Tarefa tarefa = this.tarefaRepository.findById(id);
            if(tarefa.getClass() == null)
            {
                return "Tarefa não encontrada, impossivel excluir!";
            }
            this.tarefaRepository.deleteById(id);
            return "Tarefa Excluida com sucesso!";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir tarefa: " + e.getMessage());
        }
    }
}
