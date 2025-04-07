package com.jacinthocaio.producer;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerPostRequest {
    @NotBlank(message = "O nome não pode estar vazio")
    private String name;

}
