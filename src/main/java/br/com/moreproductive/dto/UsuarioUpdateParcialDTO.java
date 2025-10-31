package br.com.moreproductive.dto;

import br.com.moreproductive.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioUpdateParcialDTO {

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;
    private String fotoUrl;

    public UsuarioUpdateParcialDTO()
    {

    }

    public UsuarioUpdateParcialDTO(Usuario usuario)
    {
        this.nome = usuario.getNome();
        this.fotoUrl = usuario.getFotoUrl();
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
}
