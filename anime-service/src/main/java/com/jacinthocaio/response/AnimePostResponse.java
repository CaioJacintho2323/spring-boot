package com.jacinthocaio.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AnimePostResponse {
    private Long id;
    private String name;
}
