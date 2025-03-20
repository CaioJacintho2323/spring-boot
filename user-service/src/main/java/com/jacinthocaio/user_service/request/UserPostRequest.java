package com.jacinthocaio.user_service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UserPostRequest {
    @NotBlank(message = "O primeiro nome não pode estar vazio")
    private String firstName;

    @NotBlank(message = "O ultimo nome não pode estar vazio")
    private String lastName;

    @Email(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$",message = "E-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;
}
