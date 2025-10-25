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
    public ResponseEntity<TarefaDTO> salvar(@Valid @RequestBody TarefaDTO novaTarefa, Authentication autenticacao) throws Exception {
        String usuarioLogado = autenticacao.getName();
        TarefaDTO tarefaSalva = this.tarefaService.salvar(novaTarefa, usuarioLogado);
        return new ResponseEntity<>(tarefaSalva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> buscarTodas(@RequestParam int usuarioId)
    {
        List<TarefaDTO> tarefasUsuario = this.tarefaService.buscarOrdenadasDataLimite(usuarioId);
        return new ResponseEntity<>(tarefasUsuario, HttpStatus.OK);
    }

    @GetMapping("/ordenar/dataCriacao/")
    public ResponseEntity<List<TarefaDTO>> buscarOrdenasDataCriacao(@RequestParam int usuarioId)
    {
        List<TarefaDTO> tarefas = this.tarefaService.buscarOrdenadasDataCriacao(usuarioId);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/ordenar/prioridade/")
    public ResponseEntity<List<TarefaDTO>> buscarOrdenasPrioridade(@RequestParam int usuarioId)
    {
        List<TarefaDTO> tarefas = this.tarefaService.buscarOrdenadasPrioridade(usuarioId);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/ordenar/status/")
    public ResponseEntity<List<TarefaDTO>> buscarOrdenasStatus(@RequestParam int usuarioId)
    {
        List<TarefaDTO> tarefas = this.tarefaService.buscarOrdenadasStatus(usuarioId);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/filtrar/status/")
    public ResponseEntity<List<TarefaDTO>> filtrarPorStatus(@RequestParam int usuarioId,
                                                            @RequestParam StatusTarefaEnum status)
    {
        List<TarefaDTO> tarefas = this.tarefaService.filtrarPorStatus(usuarioId, status);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/filtrar/prioridade/")
    public ResponseEntity<List<TarefaDTO>> filtrarPorPrioridade(@RequestParam int usuarioId,
                                                            @RequestParam PrioridadeTarefaEnum prioridade)
    {
        List<TarefaDTO> tarefas = this.tarefaService.filtrarPorPrioridade(usuarioId, prioridade);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<TarefaDTO> atualizarTarefa(@RequestParam int id,
                                                     @RequestBody TarefaDTO tarefaAtualizadaDTO)
    {
        TarefaDTO tarefaDTO = this.tarefaService.atualizarTarefa(id, tarefaAtualizadaDTO);
        return new ResponseEntity<>(tarefaDTO, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> excluirTarefa(@RequestParam int id)
    {
        this.tarefaService.excluirTarefa(id);
        return new ResponseEntity<>("Excluido com sucesso!",HttpStatus.OK);
    }
}
