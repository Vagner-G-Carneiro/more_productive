package br.com.moreproductive.controller;

import br.com.moreproductive.dto.UsuarioDTO;
import br.com.moreproductive.dto.UsuarioEmailUpdateDTO;
import br.com.moreproductive.dto.UsuarioSenhaUpdateDTO;
import br.com.moreproductive.dto.UsuarioUpdateParcialDTO;
import br.com.moreproductive.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        UsuarioDTO usuarioSalvo = this.usuarioService.cadastrarUsuario(usuarioDTO);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> encontrarPorId(@PathVariable int id)
    {
        UsuarioDTO usuarioDTO = this.usuarioService.encontrarPorId(id);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UsuarioDTO> encontrarPorEmail(@RequestParam("email") String email) {
        UsuarioDTO usuarioDTO = this.usuarioService.encontrarPorEmail(email);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UsuarioUpdateParcialDTO> atualizar(@Valid @RequestBody UsuarioUpdateParcialDTO usuarioAtualizado) {
        UsuarioUpdateParcialDTO usuario = this.usuarioService.atualizar(usuarioAtualizado);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping("/email")
    public ResponseEntity<UsuarioDTO> atualizarEmail(@Valid @RequestBody UsuarioEmailUpdateDTO usuarioEmailUpdateDTO)
    {
        UsuarioDTO usuario = this.usuarioService.atualizarEmail(usuarioEmailUpdateDTO);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping("/senha")
    public ResponseEntity<UsuarioDTO> atualizarSenha(@Valid @RequestBody UsuarioSenhaUpdateDTO usuarioSenhaUpdateDTO)
    {
        UsuarioDTO usuario = this.usuarioService.atualizarSenha(usuarioSenhaUpdateDTO);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable int id) throws Exception {
        this.usuarioService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
