package br.com.moreproductive.controller;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.enums.PrioridadeTarefaEnum;
import br.com.moreproductive.enums.StatusTarefaEnum;
import br.com.moreproductive.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuario/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<TarefaDTO> salvar(@Valid @RequestBody TarefaDTO novaTarefa, Authentication autenticacao) {
        String usuarioLogadoEmail = autenticacao.getName();
        TarefaDTO tarefa = this.tarefaService.salvar(novaTarefa, usuarioLogadoEmail);
        return new ResponseEntity<>(tarefa, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> buscarTodas(Authentication autenticacao)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        List<TarefaDTO> tarefas = this.tarefaService.buscarTodas(usuarioLogadoEmail);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/ordenar/dataLimite")
    public ResponseEntity<List<TarefaDTO>> buscarOrdenadasDataLimite(Authentication autenticacao)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        List<TarefaDTO> tarefas = this.tarefaService.buscarOrdenadasDataLimite(usuarioLogadoEmail);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/ordenar/dataCriacao")
    public ResponseEntity<List<TarefaDTO>> buscarOrdenasDataCriacao(Authentication autenticacao)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        List<TarefaDTO> tarefas = this.tarefaService.buscarOrdenadasDataCriacao(usuarioLogadoEmail);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/ordenar/prioridade")
    public ResponseEntity<List<TarefaDTO>> buscarOrdenasPrioridade(Authentication autenticacao)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        List<TarefaDTO> tarefas = this.tarefaService.buscarOrdenadasPrioridade(usuarioLogadoEmail);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/ordenar/status")
    public ResponseEntity<List<TarefaDTO>> buscarOrdenasStatus(Authentication autenticacao)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        List<TarefaDTO> tarefas = this.tarefaService.buscarOrdenadasStatus(usuarioLogadoEmail);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/filtrar/status")
    public ResponseEntity<List<TarefaDTO>> filtrarPorStatus(Authentication autenticacao,
                                                            @RequestParam StatusTarefaEnum status)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        List<TarefaDTO> tarefas = this.tarefaService.filtrarPorStatus(usuarioLogadoEmail, status);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/filtrar/prioridade")
    public ResponseEntity<List<TarefaDTO>> filtrarPorPrioridade(Authentication autenticacao,
                                                                @RequestParam PrioridadeTarefaEnum prioridade)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        List<TarefaDTO> tarefas = this.tarefaService.filtrarPorPrioridade(usuarioLogadoEmail, prioridade);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TarefaDTO> atualizarTarefa(Authentication autenticacao,
                                                     @RequestBody TarefaDTO tarefaAtualizadaDTO)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        TarefaDTO tarefaDTO = this.tarefaService.atualizarTarefa(usuarioLogadoEmail, tarefaAtualizadaDTO);
        return new ResponseEntity<>(tarefaDTO, HttpStatus.OK);
    }

    @PutMapping("/{tarefaId}")
    public ResponseEntity<TarefaDTO> concluirTareda(Authentication autenticacao,
                                                     @PathVariable int tarefaId)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        TarefaDTO tarefaDTO = this.tarefaService.concluirTarefa(usuarioLogadoEmail, tarefaId);
        return new ResponseEntity<>(tarefaDTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> excluirTarefa(Authentication autenticacao,
                                                @RequestParam int tarefaId)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        this.tarefaService.excluirTarefa(usuarioLogadoEmail, tarefaId);
        return new ResponseEntity<>("Excluido com sucesso!",HttpStatus.OK);
    }
}
