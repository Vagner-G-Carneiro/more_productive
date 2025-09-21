package br.com.moreproductive.controller;

import br.com.moreproductive.dto.UsuarioDTO;
import br.com.moreproductive.service.UsuarioService;
import br.com.moreproductive.utils.AtualizarEmailRequest;
import br.com.moreproductive.utils.AtualizarSenhaRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public ResponseEntity<UsuarioDTO> atualizarEmail(AtualizarEmailRequest informacoes)
    {
        UsuarioDTO usuario = this.usuarioService.atualizarEmail(informacoes.emailAntigo(), informacoes.novoEmail(), informacoes.senha());
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping("/atualizar/senha")
    public ResponseEntity<UsuarioDTO> atualizarSenha(Principal principal, @RequestBody AtualizarSenhaRequest atualizarSenhaRequest)
    {
        String email = principal.getName();
        UsuarioDTO usuario = this.usuarioService.atualizarSenha(email, atualizarSenhaRequest.novaSenha(), atualizarSenhaRequest.senhaAtual());
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluir(@PathVariable int id) throws Exception {
        this.usuarioService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
