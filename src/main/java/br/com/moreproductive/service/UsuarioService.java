package br.com.moreproductive.service;

import br.com.moreproductive.config.JwtService;
import br.com.moreproductive.dto.UsuarioDTO;
import br.com.moreproductive.dto.UsuarioEmailUpdateDTO;
import br.com.moreproductive.dto.UsuarioSenhaUpdateDTO;
import br.com.moreproductive.dto.UsuarioUpdateParcialDTO;
import br.com.moreproductive.entities.Usuario;
import br.com.moreproductive.exceptions.InformacaoNaoEncontradaException;
import br.com.moreproductive.exceptions.UsuarioException;
import br.com.moreproductive.repository.UsuarioRepository;
import br.com.moreproductive.config.SegurancaConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
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

    public UsuarioDTO encontrarPorId(int id, String usuarioLogado) throws AccessDeniedException {
        Optional<Usuario> usuarioRequisicaoOpt = this.usuarioRepository.findUsuarioByEmail(usuarioLogado);
        System.out.println("Email Logado => " + usuarioLogado);
        if(usuarioRequisicaoOpt.isPresent())
        {
            Usuario usuarioRequisicao = usuarioRequisicaoOpt.get();
            if(usuarioRequisicao.getId() == id)
            {
                Optional<Usuario> usuario = this.usuarioRepository.findById(id);
                if(usuario.isPresent())
                {
                    return new UsuarioDTO(usuario.get());
                }
                throw new UsuarioException("Erro ao buscar usuário por ID.");
            }
            throw new AccessDeniedException("Você não tem permissão para essa requisição!");
        }
        throw new UsuarioException("Erro ao buscar usuário logado.");
    }

    public UsuarioDTO encontrarPorEmail(String email, String usuarioLogado) throws AccessDeniedException {
        Optional<Usuario> usuarioRequisicaoOpt = this.usuarioRepository.findUsuarioByEmail(usuarioLogado);
        if(usuarioRequisicaoOpt.isPresent())
        {
            Usuario usuarioRequisicao = usuarioRequisicaoOpt.get();
            if(usuarioRequisicao.getEmail().equals(email))
            {
                Optional<Usuario> usuario = this.usuarioRepository.findUsuarioByEmail(email);
                if(usuario.isPresent())
                {
                    return new UsuarioDTO(usuario.get());
                }
                throw new UsuarioException("Erro ao buscar usuário por e-mail.");
            }
            throw new AccessDeniedException("Você não tem permissão para essa requisição");
        }
        throw new UsuarioException("Erro ao buscar usuário logado.");
    }

    public UsuarioUpdateParcialDTO atualizar(UsuarioUpdateParcialDTO usuarioDtoAtualizado, String usuarioLogado) throws AccessDeniedException {
        Optional<Usuario> usuarioRequisicaoOpt = this.usuarioRepository.findUsuarioByEmail(usuarioLogado);
        if(usuarioRequisicaoOpt.isPresent())
        {
            Usuario usuarioRequisicao = usuarioRequisicaoOpt.get();
            if(usuarioRequisicao.getEmail().equals(usuarioDtoAtualizado.getEmail()))
            {
                Optional<Usuario> usuario = this.usuarioRepository.findUsuarioByEmail(usuarioDtoAtualizado.getEmail());
                if(usuario.isPresent())
                {
                    Usuario usuarioDeveSerAtualizado = usuario.get();
                    usuarioDeveSerAtualizado.setFotoUrl(usuarioDtoAtualizado.getFotoUrl());
                    usuarioDeveSerAtualizado.setNome(usuarioDtoAtualizado.getNome());
                    this.usuarioRepository.save(usuarioDeveSerAtualizado);
                    return new UsuarioUpdateParcialDTO(usuarioDeveSerAtualizado);
                }
                throw new UsuarioException("Erro ao buscar usuário a ser atualizado.");
            }
            throw new AccessDeniedException("Você não tem permissão para essa requisição");
        }
        throw new UsuarioException("Erro ao buscar usuário logado.");
    }

    public String atualizarEmail(UsuarioEmailUpdateDTO usuarioEmailUpdateDTO, String usuarioLogado) throws AccessDeniedException {
        Optional<Usuario> usuarioRequisicaoOpt = this.usuarioRepository.findUsuarioByEmail(usuarioLogado);
        if(usuarioRequisicaoOpt.isPresent())
        {
            Optional <Usuario> usuarioAtualEmailOpt = this.usuarioRepository.findUsuarioByEmail(usuarioEmailUpdateDTO.atualEmail());
            if(usuarioAtualEmailOpt.isPresent())
            {
                Usuario usuarioRequisicao = usuarioRequisicaoOpt.get();
                Usuario usuarioAtualEmail = usuarioAtualEmailOpt.get();
                if(usuarioRequisicao.getEmail().equals(usuarioAtualEmail.getEmail()))
                {
                    Optional <Usuario> usuarioNovoEmail = this.usuarioRepository.findUsuarioByEmail(usuarioEmailUpdateDTO.novoEmail());
                    if(usuarioNovoEmail.isEmpty())
                    {
                        if(!this.passwordEncoder.matches(usuarioEmailUpdateDTO.senha(), usuarioAtualEmail.getSenhaHash()))
                        {
                            throw new AccessDeniedException("Senha invalida! Troca de email não autorizada.");
                        }
                        usuarioAtualEmail.setEmail(usuarioEmailUpdateDTO.novoEmail());
                        usuarioAtualEmail.setTokenValido(Instant.now().truncatedTo(ChronoUnit.SECONDS));
                        this.usuarioRepository.save(usuarioAtualEmail);
                        return this.jwtService.gerarToken(usuarioAtualEmail);
                    } else {
                        throw new UsuarioException("Erro ao atualizar email do usuario: parece que o novo e-mail já possuí registro plataforma");
                    }
                } else {
                    throw new AccessDeniedException("Você não tem permissão para isso.");
                }
            } else{
                throw new InformacaoNaoEncontradaException("Erro ao atualizar email do usuario: Email antigo fornecido inválido");
            }
        } else {
            throw  new UsuarioException("Erro ao buscar usuário logado");
        }
    }

    public String atualizarSenha(UsuarioSenhaUpdateDTO usuarioSenhaUpdateDTO, String usuarioLogado) throws AccessDeniedException {
        Optional<Usuario> usuarioRequisicaoOpt = this.usuarioRepository.findUsuarioByEmail(usuarioLogado);
        if(usuarioRequisicaoOpt.isPresent())
        {
            Optional<Usuario> usuarioSenhaDesatualizada = this.usuarioRepository.findUsuarioByEmail(usuarioSenhaUpdateDTO.email());
            if(usuarioSenhaDesatualizada.isPresent())
            {
                Usuario usuarioRequisicao = usuarioRequisicaoOpt.get();
                Usuario usuarioDesatualizado = usuarioSenhaDesatualizada.get();
                if(usuarioRequisicao.getEmail().equals(usuarioDesatualizado.getEmail()))
                {
                    if(!this.passwordEncoder.matches(usuarioSenhaUpdateDTO.senhaAtual(), usuarioDesatualizado.getSenhaHash()))
                    {
                        throw new UsuarioException("Senha atual invalida! Troca de senha não autorizada.");
                    }
                    usuarioDesatualizado.setSenhaHash(this.segurancaConfig.passwordEncoder().encode(usuarioSenhaUpdateDTO.senhaNova()));
                    usuarioDesatualizado.setTokenValido(Instant.now().truncatedTo(ChronoUnit.SECONDS));
                    this.usuarioRepository.save(usuarioDesatualizado);
                    return this.jwtService.gerarToken(usuarioDesatualizado);
                } else {
                    throw new AccessDeniedException("Você não tem permissão para isso.");
                }
            } else {
                throw new InformacaoNaoEncontradaException("Erro ao recuperar email do usuario para troca de senha.");
            }
        } else {
            throw new UsuarioException("Erro ao buscar usuário logado");
        }
    }

    public void excluir(int id, String emailUsuarioLogado) throws Exception {
        Optional<Usuario> usuarioLogadoOpt = this.usuarioRepository.findUsuarioByEmail(emailUsuarioLogado);
        if(usuarioLogadoOpt.isPresent())
        {
            Usuario usuarioLogado = usuarioLogadoOpt.get();
            if(usuarioLogado.getId() == id)
            {
                try {
                    this.usuarioRepository.deleteById(id);
                } catch (Exception e) {
                    throw new Exception("Erro ao excluir: " + e.getMessage());
                }
            } else {
                throw new AccessDeniedException("Você não tem permissão para isso.");
            }
        } else {
            throw new UsuarioException("Erro ao buscar usuário logado.");
        }
    }

}
