package br.com.moreproductive.controller;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/buscar/todas/")
    public ResponseEntity<List<TarefaDTO>> buscarTodasAsTarefas(@RequestParam int usuarioId)
    {
        List<TarefaDTO> tarefasUsuario = this.tarefaService.buscarTodasAsTarefas(usuarioId);
        return new ResponseEntity<>(tarefasUsuario, HttpStatus.OK);
    }
}
