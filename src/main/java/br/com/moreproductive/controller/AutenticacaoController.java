package br.com.moreproductive.controller;

import br.com.moreproductive.config.JwtService;
import br.com.moreproductive.dto.LoginRequest;
import br.com.moreproductive.dto.LoginResponse;
import br.com.moreproductive.dto.UsuarioDTO;
import br.com.moreproductive.entities.Usuario;
import br.com.moreproductive.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AutenticacaoController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtService jwtService)
    {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken emailESenha = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha());
        Authentication autenticacao = authenticationManager.authenticate(emailESenha);
        Usuario usuario = (Usuario) autenticacao.getPrincipal();
        String token = jwtService.gerarToken(usuario);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<LoginResponse> cadastrar(@Valid @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuarioSalvo = this.usuarioService.cadastrarUsuario(usuarioDTO);
        String token = jwtService.gerarToken(usuarioSalvo);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.CREATED);
    }

}