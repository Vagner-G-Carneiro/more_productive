package br.com.moreproductive.controller;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/usuarios/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<TarefaDTO> salvar(@Valid @RequestBody TarefaDTO novaTarefa)
    {
        novaTarefa.setDataCriacao(LocalDateTime.now());
        TarefaDTO tarefaSalva = this.tarefaService.salvar(novaTarefa);
        return new ResponseEntity<>(tarefaSalva, HttpStatus.CREATED);
    }

}
