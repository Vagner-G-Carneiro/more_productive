package br.com.moreproductive.dto;

import jakarta.validation.constraints.*;

public class UsuarioSenhaUpdateDTO {

    @NotBlank(message = "A senha atual é um campo obrigatório para troca de credenciais")
    private String senhaAtual;

    @NotBlank(message = "A senha nova parece não estar preenchida")
    @Size(min= 8, message = "A senha deve ter no mínimo 8 caracteres, tente uma maior!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#]).{8,}$", message = "A senha deve conter ao menos uma letra maiuscula, " +
            "uma minuscula, e  um caractere especial!")
    private String senhaNova;

    @NotBlank(message = "E-mail atual é um campo obrigatório e precisa ser preenchido para troca de credenciais.")
    @Email(message = "Oops, seu e-mail atual está em um formato inválido.")
    private String email;

    public UsuarioSenhaUpdateDTO(String senhaAtual, String senhaNova, String email) {
        this.senhaAtual = senhaAtual;
        this.senhaNova = senhaNova;
        this.email = email;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
