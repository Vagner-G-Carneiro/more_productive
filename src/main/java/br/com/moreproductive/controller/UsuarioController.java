package br.com.moreproductive.controller;

import br.com.moreproductive.config.JwtService;
import br.com.moreproductive.dto.*;
import br.com.moreproductive.entities.Usuario;
import br.com.moreproductive.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> encontrarPorId(@PathVariable int usuarioAlvo, Authentication autenticacao) {
        String usuarioLogadoEmail = autenticacao.getName();
        UsuarioDTO usuarioDTO = this.usuarioService.encontrarPorId(usuarioAlvo, usuarioLogadoEmail);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UsuarioDTO> encontrarPorEmail(@RequestParam("email") String usuarioAlvo, Authentication autenticacao) {
        String usuarioLogadoEmail = autenticacao.getName();
        UsuarioDTO usuarioDTO = this.usuarioService.encontrarPorEmail(usuarioAlvo, usuarioLogadoEmail);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizar(@Valid @RequestBody UsuarioUpdateParcialDTO usuarioAlvo, Authentication autenticacao) {
        String usuarioLogadoEmail = autenticacao.getName();
        UsuarioDTO usuario = this.usuarioService.atualizar(usuarioAlvo, usuarioLogadoEmail);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping("/email")
    public ResponseEntity<LoginResponse> atualizarEmail(@Valid @RequestBody UsuarioEmailUpdateDTO usuarioAlvo, Authentication autenticacao) throws AccessDeniedException {
        String usuarioLogadoEmail = autenticacao.getName();
        String token = this.usuarioService.atualizarEmail(usuarioAlvo, usuarioLogadoEmail);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @PutMapping("/senha")
    public ResponseEntity<LoginResponse> atualizarSenha(@Valid @RequestBody UsuarioSenhaUpdateDTO usuarioAlvo, Authentication autenticacao) throws AccessDeniedException {
        String usuarioLogadoEmail = autenticacao.getName();
        String token = this.usuarioService.atualizarSenha(usuarioAlvo, usuarioLogadoEmail);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@Valid @RequestBody LoginRequest deletarUsuario ,Authentication autenticacao) throws Exception {
        String usuarioLogadoEmail = autenticacao.getName();
        this.usuarioService.excluir(deletarUsuario, usuarioLogadoEmail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
