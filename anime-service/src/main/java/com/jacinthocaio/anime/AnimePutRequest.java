package com.jacinthocaio.anime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnimePutRequest {
    @NotNull(message = "O id não pode estar nulo")
    private Long id;
    @NotBlank(message = "O nome não pode estar vazio")
    private String name;
}
