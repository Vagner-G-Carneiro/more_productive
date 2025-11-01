package br.com.moreproductive.controller;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<TarefaDTO>> buscarTarefas(Authentication autenticacao, @PageableDefault(page = 0, size = 30)
            @SortDefault(sort = "status")
            @SortDefault(sort = "prioridade", direction = Sort.Direction.DESC)
            @SortDefault(sort = "dataLimite")
        Pageable pageable)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        Page<TarefaDTO> pageTarefas = this.tarefaService.buscarTarefas(usuarioLogadoEmail, pageable);
        return new ResponseEntity<>(pageTarefas, HttpStatus.OK);
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

    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<String> excluirTarefa(Authentication autenticacao,
                                                @PathVariable int tarefaId)
    {
        String usuarioLogadoEmail = autenticacao.getName();
        this.tarefaService.excluirTarefa(usuarioLogadoEmail, tarefaId);
        return new ResponseEntity<>("Excluido com sucesso!",HttpStatus.OK);
    }
}
