package br.com.moreproductive.service;

import br.com.moreproductive.dto.UsuarioDTO;
import br.com.moreproductive.entities.Usuario;
import br.com.moreproductive.exceptions.InformacaoNaoEncontradaException;
import br.com.moreproductive.exceptions.PersistenciaException;
import br.com.moreproductive.exceptions.UsuarioException;
import br.com.moreproductive.repository.UsuarioRepository;
import br.com.moreproductive.utils.SegurancaConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SegurancaConfig segurancaConfig;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, SegurancaConfig segurancaConfig, PasswordEncoder passwordEncoder)
    {
        this.usuarioRepository = usuarioRepository;
        this.segurancaConfig = segurancaConfig;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) throws Exception {
        try {
            Optional<Usuario> email = this.usuarioRepository.findByEmail(usuarioDTO.getEmail());

            if (email.isPresent())
            {
                throw new UsuarioException(" Email já cadastrado!");
            }

            String senhaHash = this.segurancaConfig.passwordEncoder().encode(usuarioDTO.getSenha());
            Usuario novoUsuario = new Usuario(usuarioDTO, senhaHash);
            this.usuarioRepository.save(novoUsuario);
            return new UsuarioDTO(novoUsuario);
        } catch (Exception exception) {
            throw new Exception("Erro ao salvar usuario!" + exception.getMessage());
        }
    }

    public UsuarioDTO encontrarPorId(int id) {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioEncontrado = usuarioOptional.get();
            return new UsuarioDTO(usuarioEncontrado);
        } else {
            throw new InformacaoNaoEncontradaException(" Este usuario não existe!. [ID: " + id + "]");
        }
    }

    public UsuarioDTO encontrarPorEmail(String email) {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(email);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioEncontrado = usuarioOptional.get();
            return new UsuarioDTO(usuarioEncontrado);
        } else {
            throw new InformacaoNaoEncontradaException("Email não cadastrado!");
        }
    }

    public UsuarioDTO atualizar(UsuarioDTO usuarioDtoAtualizado) {
        Optional<Usuario> usuarioAntigo = this.usuarioRepository.findByEmail(usuarioDtoAtualizado.getEmail());
        if (usuarioAntigo.isPresent()) {
            Usuario usuarioDeveSerAtualizado = usuarioAntigo.get();
            usuarioDeveSerAtualizado.setFotoUrl(usuarioDtoAtualizado.getFotoUrl());
            usuarioDeveSerAtualizado.setNome(usuarioDtoAtualizado.getNome());
            this.usuarioRepository.save(usuarioDeveSerAtualizado);
            return new UsuarioDTO(usuarioDeveSerAtualizado);
        } else {
            throw new InformacaoNaoEncontradaException("Erro ao atualizar usuario, credencial email invalida!");
        }
    }

    public UsuarioDTO atualizarEmail(String emailAntigo, String novoEmail, String senha) {
        Optional <Usuario> usuarioEmailAntigo = this.usuarioRepository.findByEmail(emailAntigo);
        if(usuarioEmailAntigo.isPresent())
        {
            Usuario usuarioDesatualizado = usuarioEmailAntigo.get();
            if(!this.passwordEncoder.matches(senha, usuarioDesatualizado.getSenhaHash()))
            {
                throw new UsuarioException("Senha invalida! Troca de email não autorizada.");
            }
            usuarioDesatualizado.setEmail(novoEmail);
            Usuario usuarioAtualizado = this.usuarioRepository.save(usuarioDesatualizado);
            return new UsuarioDTO(usuarioAtualizado);
        } else{
            throw new InformacaoNaoEncontradaException("Erro ao atualizar email do usuario.");
        }
    }

    public UsuarioDTO atualizarSenha(String email, String novaSenha, String senhaAtual)
    {
        Optional<Usuario> usuarioSenhaDesatualizada = this.usuarioRepository.findByEmail(email);
        if(usuarioSenhaDesatualizada.isPresent())
        {
            Usuario usuarioDesatualizado = usuarioSenhaDesatualizada.get();
            if(!this.passwordEncoder.matches(senhaAtual, usuarioDesatualizado.getSenhaHash()))
            {
                throw new UsuarioException("Senha atual invalida! Troca de senha não autorizada.");
            }
            usuarioDesatualizado.setSenhaHash(this.segurancaConfig.passwordEncoder().encode(novaSenha));
            Usuario usuarioAtualizado = this.usuarioRepository.save(usuarioDesatualizado);
            return new UsuarioDTO(usuarioAtualizado);
        } else {
            throw new InformacaoNaoEncontradaException("Erro ao recuperar email do usuario logado.");
        }
    }

    public void excluir(int id) throws Exception {
        try {
            this.usuarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Erro ao excluir: " + e.getMessage());
        }
    }

}
