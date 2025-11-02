package br.com.moreproductive.repository;

import br.com.moreproductive.entities.Tarefa;
import br.com.moreproductive.enums.StatusTarefaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository <Tarefa, Integer> {

    Page<Tarefa> findByUsuarioId(int usuarioId, Pageable pageable);
    List<Tarefa> findByStatusAndDataLimiteBefore(StatusTarefaEnum statusTarefaEnum, LocalDateTime now);
}
