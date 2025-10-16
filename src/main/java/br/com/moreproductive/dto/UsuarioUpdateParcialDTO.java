package br.com.moreproductive.dto;

import br.com.moreproductive.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioUpdateParcialDTO {

    @NotBlank(message = "Nome é um campo obrigatório e precisa ser preenchido" +
            " O que acha de tentar usar seu nome ou até mesmo um apelido!")
    private String nome;

    @NotBlank(message = "E-mail é um campo obrigatório e precisa ser preenchido, vamos ajuda-lo?")
    @Email(message = "Oops, seu e-mail está em um formato inválido.")
    private String email;

    private String fotoUrl;

    public UsuarioUpdateParcialDTO()
    {

    }

    public UsuarioUpdateParcialDTO(Usuario usuario)
    {
        this.nome = usuario.getNome();
        this.fotoUrl = usuario.getFotoUrl();
        this.email = usuario.getEmail();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
