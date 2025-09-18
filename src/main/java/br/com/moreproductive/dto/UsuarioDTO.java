package br.com.moreproductive.dto;

import br.com.moreproductive.entities.Usuario;

public class UsuarioDTO {
    private String nome;
    private String email;
    private String fotoUrl;
    private String senha;

    public UsuarioDTO()
    {

    }

    public UsuarioDTO(Usuario usuario)
    {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.fotoUrl = usuario.getFotoUrl();
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
