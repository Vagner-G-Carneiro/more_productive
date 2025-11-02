package br.com.moreproductive.config;

import br.com.moreproductive.entities.Tarefa;
import br.com.moreproductive.enums.StatusTarefaEnum;
import br.com.moreproductive.repository.TarefaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StatusTarefaSchedulingConfig
{
    TarefaRepository tarefaRepository;

    public StatusTarefaSchedulingConfig (TarefaRepository tarefaRepository)
    {
        this.tarefaRepository = tarefaRepository;
    }

    @Scheduled(cron = "1 0 0 * * *")
    public void atualizarTarefasPendentesParaAtrasadas()
    {
        List<Tarefa> tarefasAtrasadas = this.tarefaRepository.findByStatusAndDataLimiteBefore(StatusTarefaEnum.PENDENTE, LocalDateTime.now());

        if(tarefasAtrasadas.isEmpty())
        {
            System.out.println("Nenhuma tarefa para ser atualizada, sistema em dia! :D");
        } else {
            for(Tarefa tarefa : tarefasAtrasadas)
            {
                tarefa.setStatus(StatusTarefaEnum.ATRASADA);
            }
            this.tarefaRepository.saveAll(tarefasAtrasadas);
        }
    }
}
