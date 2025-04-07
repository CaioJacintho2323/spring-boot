package com.jacinthocaio.user_service.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProfilePostRequest {
    @NotBlank(message = "O name não pode estar vazio")
    private String name;

    @NotBlank(message = "A descricao não pode estar vazia")
    private String description;
}
