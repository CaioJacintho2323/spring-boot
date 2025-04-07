package com.jacinthocaio.producer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProducerPutRequest {
    @NotNull(message = "O id não pode estar nulo")
    private Long id;
    @NotBlank(message = "O nome não pode estar vazio")
    private String name;
}
