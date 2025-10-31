package br.com.moreproductive.dto;

import br.com.moreproductive.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public class UsuarioDTO {

    @NotBlank(message = "Nome é um campo obrigatório e precisa ser preenchido" +
            " O que acha de tentar usar seu nome ou até mesmo um apelido!")
    private String nome;
    @NotBlank(message = "E-mail é um campo obrigatório e precisa ser preenchido, vamos ajuda-lo?")
    @Email(message = "Oops, seu e-mail está em um formato inválido.")
    private String email;
    private String fotoUrl;
    @NotBlank(message = "A senha é um campo obrigatório, é sua proteção e chave na nossa aplicação! :D")
    @Size(min= 8, message = "A senha deve ter no mínimo 8 caracteres, tente uma maior!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#]).{8,}$", message = "A senha deve conter ao menos uma letra maiuscula, " +
            "uma minuscula, e  um caractere especial!")
    private String senha;
    private Instant tokenValido;

    public UsuarioDTO()
    {

    }

    public UsuarioDTO(Usuario usuario)
    {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.fotoUrl = usuario.getFotoUrl();
        this.senha = usuario.getSenhaHash();
        this.tokenValido = usuario.getTokenValido();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
