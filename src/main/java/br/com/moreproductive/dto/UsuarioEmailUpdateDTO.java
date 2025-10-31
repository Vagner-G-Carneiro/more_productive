package br.com.moreproductive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioEmailUpdateDTO(
        @NotBlank(message = "E-mail atual é um campo obrigatório e precisa ser preenchido para troca de credenciais.")
        @Email(message = "Oops, seu e-mail atual está em um formato inválido.")
        String atualEmail,

        @NotBlank(message = "E-mail novo é um campo obrigatório e precisa ser preenchido para troca de credenciais.")
        @Email(message = "Oops, seu e-mail novo está em um formato inválido.")
        String novoEmail,

        @NotBlank(message = "A senha atual é um campo obrigatório para troca de credenciais")
        String senha
){}
