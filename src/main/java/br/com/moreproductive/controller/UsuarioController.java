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
    private final JwtService jwtService;

    public UsuarioController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> encontrarPorId(@PathVariable int id, Authentication autenticacao) throws AccessDeniedException {
        String usuarioLogado = autenticacao.getName();
        UsuarioDTO usuarioDTO = this.usuarioService.encontrarPorId(id, usuarioLogado);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UsuarioDTO> encontrarPorEmail(@RequestParam("email") String email, Authentication autenticacao) throws AccessDeniedException {
        String usuarioLogado = autenticacao.getName();
        UsuarioDTO usuarioDTO = this.usuarioService.encontrarPorEmail(email, usuarioLogado);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UsuarioUpdateParcialDTO> atualizar(@Valid @RequestBody UsuarioUpdateParcialDTO usuarioAtualizado, Authentication autenticacao) throws AccessDeniedException {
        String usuarioLogado = autenticacao.getName();
        UsuarioUpdateParcialDTO usuario = this.usuarioService.atualizar(usuarioAtualizado, usuarioLogado);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping("/email")
    public ResponseEntity<LoginResponse> atualizarEmail(@Valid @RequestBody UsuarioEmailUpdateDTO usuarioEmailUpdateDTO, Authentication autenticacao) throws AccessDeniedException {
        String usuarioLogado = autenticacao.getName();
        String token = this.usuarioService.atualizarEmail(usuarioEmailUpdateDTO, usuarioLogado);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @PutMapping("/senha")
    public ResponseEntity<LoginResponse> atualizarSenha(@Valid @RequestBody UsuarioSenhaUpdateDTO usuarioSenhaUpdateDTO, Authentication autenticacao) throws AccessDeniedException {
        String usuarioLogado = autenticacao.getName();
        String token = this.usuarioService.atualizarSenha(usuarioSenhaUpdateDTO, usuarioLogado);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable int id,Authentication autenticacao) throws Exception {
        String usuarioLogado = autenticacao.getName();
        this.usuarioService.excluir(id, usuarioLogado);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
