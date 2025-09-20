package br.com.moreproductive.repository;

import br.com.moreproductive.entities.Tarefa;
import br.com.moreproductive.enums.PrioridadeTarefaEnum;
import br.com.moreproductive.enums.StatusTarefaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository <Tarefa, Integer> {

    Tarefa findById(int id);
    List<Tarefa> findByUsuarioIdOrderByDataLimite(int usuarioId);
    List<Tarefa> findByUsuarioIdOrderByDataCriacaoDesc(int usuarioId);
    List<Tarefa> findByUsuarioIdOrderByPrioridadeDesc(int usuarioId);
    List<Tarefa> findByUsuarioIdOrderByStatus(int usuarioId);
    List<Tarefa> findByUsuarioIdAndStatus(int usuarioId, StatusTarefaEnum statusTarefaEnum);
    List<Tarefa> findByUsuarioIdAndPrioridade(int usuarioId, PrioridadeTarefaEnum prioridadeTarefaEnum);
    List<Tarefa> findByStatusAndDataLimiteBefore(StatusTarefaEnum statusTarefaPendente, LocalDateTime agora);
}
