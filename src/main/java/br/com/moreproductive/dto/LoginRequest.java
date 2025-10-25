package br.com.moreproductive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "E-mail é um campo obrigatório.")
        @Email(message = "Oops, seu e-mail está em um formato inválido.")
        String email,

        @NotBlank(message = "A senha é um campo obrigatório")
        String senha
) {}
