package br.com.moreproductive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioEmailUpdateDTO {

    @NotBlank(message = "E-mail atual é um campo obrigatório e precisa ser preenchido para troca de credenciais.")
    @Email(message = "Oops, seu e-mail atual está em um formato inválido.")
    private String atualEmail;

    @NotBlank(message = "E-mail novo é um campo obrigatório e precisa ser preenchido para troca de credenciais.")
    @Email(message = "Oops, seu e-mail novo está em um formato inválido.")
    private String novoEmail;

    @NotBlank(message = "A senha atual é um campo obrigatório para troca de credenciais")
    private String senha;

    public UsuarioEmailUpdateDTO(String atualEmail, String novoEmail, String senha) {
        this.atualEmail = atualEmail;
        this.novoEmail = novoEmail;
        this.senha = senha;
    }

    public String getAtualEmail() {
        return atualEmail;
    }

    public void setAtualEmail(String atualEmail) {
        this.atualEmail = atualEmail;
    }

    public String getNovoEmail() {
        return novoEmail;
    }

    public void setNovoEmail(String novoEmail) {
        this.novoEmail = novoEmail;
    }

    public String getSenha() {
        return senha;
    }
}
