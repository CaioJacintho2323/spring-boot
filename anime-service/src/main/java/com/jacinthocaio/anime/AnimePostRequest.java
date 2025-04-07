package com.jacinthocaio.anime;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimePostRequest {

    @NotBlank(message = "O nome não pode estar vazio")
    private String name;
}
