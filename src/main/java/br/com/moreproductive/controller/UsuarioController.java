package br.com.moreproductive.controller;

import br.com.moreproductive.dto.UsuarioDTO;
import br.com.moreproductive.service.UsuarioService;
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
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioSalvo = this.usuarioService.cadastrarUsuario(usuarioDTO);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }

    @GetMapping("/encontrar/id/{id}")
    public ResponseEntity<UsuarioDTO> encontrarPorId(@PathVariable int id)
    {
        UsuarioDTO usuarioDTO = this.usuarioService.encontrarPorId(id);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @GetMapping("/encontrar/email")
    public ResponseEntity<UsuarioDTO> encontrarPorEmail(@RequestParam("email") String email) {
        UsuarioDTO usuarioDTO = this.usuarioService.encontrarPorEmail(email);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioDTO> atualizar(@RequestBody UsuarioDTO usuarioAtualizado) {
        UsuarioDTO usuario = this.usuarioService.atualizar(usuarioAtualizado);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping("/atualizar/email")
    public ResponseEntity<UsuarioDTO> atualizarEmail(
            @RequestParam("email") String email,
            @RequestParam("novoEmail") String novoEmail)
    {
        UsuarioDTO usuario = this.usuarioService.atualizarEmail(email, novoEmail);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping("/atualizar/senha")
    public ResponseEntity<UsuarioDTO> atualizarSenha(
            @RequestParam("email") String email,
            @RequestParam("novaSenha") String novaSenha)
    {
        UsuarioDTO usuario = this.usuarioService.atualizarSenha(email, novaSenha);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluir(@PathVariable int id) {
        this.usuarioService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
