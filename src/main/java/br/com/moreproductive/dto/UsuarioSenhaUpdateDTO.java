package br.com.moreproductive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioSenhaUpdateDTO(
        @NotBlank(message = "A senha atual é um campo obrigatório para troca de credenciais")
        String senhaAtual,

        @NotBlank(message = "A senha nova parece não estar preenchida")
        @Size(min= 8, message = "A senha deve ter no mínimo 8 caracteres, tente uma maior!")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#]).{8,}$", message = "A senha deve conter ao menos uma letra maiuscula, " +
                "uma minuscula, e  um caractere especial!")
        String senhaNova,

        @NotBlank(message = "E-mail atual é um campo obrigatório e precisa ser preenchido para troca de credenciais.")
        @Email(message = "Oops, seu e-mail atual está em um formato inválido.")
        String email
){}
