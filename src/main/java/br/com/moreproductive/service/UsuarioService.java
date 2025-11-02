package br.com.moreproductive.service;

import br.com.moreproductive.config.JwtService;
import br.com.moreproductive.dto.*;
import br.com.moreproductive.entities.Usuario;
import br.com.moreproductive.exceptions.PermissaoNegadaException;
import br.com.moreproductive.exceptions.UsuarioException;
import br.com.moreproductive.repository.UsuarioRepository;
import br.com.moreproductive.config.SegurancaConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SegurancaConfig segurancaConfig;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository, SegurancaConfig segurancaConfig, PasswordEncoder passwordEncoder, JwtService jwtService)
    {
        this.usuarioRepository = usuarioRepository;
        this.segurancaConfig = segurancaConfig;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private Usuario recuperarUsuarioLogado (String usuarioLogadoEmail)
    {
        return this.usuarioRepository.findUsuarioByEmail(usuarioLogadoEmail)
                .orElseThrow(() -> new UsuarioException("Erro ao recuperar usuário logado"));
    }

    private boolean altorizarRequisicaoViaEmail(String usuarioLogadoEmail, String usuarioAlvo)
    {
        return usuarioLogadoEmail.equals(usuarioAlvo);
    }

    private boolean altorizarRequisicaoViaId(int usuarioLogadoId, int usuarioAlvo)
    {
        return usuarioAlvo == usuarioLogadoId;
    }

    public Usuario cadastrarUsuario(UsuarioDTO usuarioDTO) throws Exception {
        try {
            Optional<Usuario> email = this.usuarioRepository.findUsuarioByEmail(usuarioDTO.getEmail());
            if (email.isPresent())
            {
                throw new UsuarioException("Parece que já existe uma conta para este e-mail");
            }
            String senhaHash = this.segurancaConfig.passwordEncoder().encode(usuarioDTO.getSenha());
            Usuario novoUsuario = new Usuario(usuarioDTO, senhaHash);
            this.usuarioRepository.save(novoUsuario);
            return novoUsuario;
        } catch (Exception exception) {
            throw new Exception("Erro ao salvar usuario! " + exception.getMessage());
        }
    }

    public UsuarioDTO encontrarPorId(int usuarioAlvo, String usuarioLogadoEmail) {
        Usuario usuarioLogado = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        if(this.altorizarRequisicaoViaId(usuarioLogado.getId(), usuarioAlvo))
        {
            return new UsuarioDTO(this.usuarioRepository.findById(usuarioAlvo).orElseThrow(() -> new UsuarioException()));
        }
        throw new PermissaoNegadaException();
    }

    public UsuarioDTO encontrarPorEmail(String usuarioAlvo, String usuarioLogadoEmail) {
        Usuario usuarioLogado = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        if(this.altorizarRequisicaoViaEmail(usuarioLogadoEmail, usuarioAlvo))
        {
            return new UsuarioDTO(this.usuarioRepository.findUsuarioByEmail(usuarioAlvo)
                    .orElseThrow(() -> new UsuarioException()));
        }
        throw new PermissaoNegadaException();
    }

    public UsuarioDTO atualizar(UsuarioUpdateParcialDTO usuarioDtoAtualizado, String usuarioLogadoEmail) {
        Usuario usuarioLogado = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        usuarioLogado.setNome(usuarioDtoAtualizado.getNome());
        usuarioLogado.setFotoUrl(usuarioDtoAtualizado.getFotoUrl());
        this.usuarioRepository.save(usuarioLogado);
        return new UsuarioDTO(usuarioLogado);
    }

    public String atualizarEmail(UsuarioEmailUpdateDTO usuarioEmailUpdateDTO, String usuarioLogadoEmail) {
        Usuario usuarioLogado = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        if(altorizarRequisicaoViaEmail(usuarioLogadoEmail, usuarioEmailUpdateDTO.emailAtual()))
        {
            Optional<Usuario> emailJaCadastrado = this.usuarioRepository.findUsuarioByEmail(usuarioEmailUpdateDTO.emailNovo());
            if(emailJaCadastrado.isPresent())
            {
                throw new UsuarioException("Já existe um usuário cadastrado com o que seria seu novo email.");
            }

            if(!this.passwordEncoder.matches(usuarioEmailUpdateDTO.senha(), usuarioLogado.getSenhaHash()))
            {
                throw new PermissaoNegadaException("Troca de e-mail negada, senha inválida!");
            }
            usuarioLogado.setEmail(usuarioEmailUpdateDTO.emailNovo());
            usuarioLogado.setTokenValido(Instant.now().truncatedTo(ChronoUnit.SECONDS));
            this.usuarioRepository.save(usuarioLogado);
            return jwtService.gerarToken(usuarioLogado);
        }
        throw new PermissaoNegadaException();
    }

    public String atualizarSenha(UsuarioSenhaUpdateDTO usuarioSenhaUpdateDTO, String usuarioLogadoEmail) {
        Usuario usuarioLogado = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        if(altorizarRequisicaoViaEmail(usuarioLogadoEmail, usuarioSenhaUpdateDTO.email()))
        {
            if(!this.passwordEncoder.matches(usuarioSenhaUpdateDTO.senhaAtual(), usuarioLogado.getSenhaHash()))
            {
                throw new PermissaoNegadaException("Troca de senha não permitida, senha atual inválida para a troca.");
            }
            usuarioLogado.setSenhaHash(this.passwordEncoder.encode(usuarioSenhaUpdateDTO.senhaNova()));
            usuarioLogado.setTokenValido(Instant.now().truncatedTo(ChronoUnit.SECONDS));
            this.usuarioRepository.save(usuarioLogado);
            return jwtService.gerarToken(usuarioLogado);
        }
        throw new PermissaoNegadaException();
    }

    public void excluir(LoginRequest deletarUsuario, String usuarioLogadoEmail) throws Exception {
        Usuario usuarioLogado = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        if(altorizarRequisicaoViaEmail(usuarioLogado.getEmail(), deletarUsuario.email()))
        {
            if(!this.passwordEncoder.matches(deletarUsuario.senha(), usuarioLogado.getSenhaHash()))
            {
                throw new PermissaoNegadaException("Erro ao apagar conta, senha inválida!");
            }
            this.usuarioRepository.delete(usuarioLogado);
            return;
        }
        throw new PermissaoNegadaException();
    }

}