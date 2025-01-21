package com.jacinthocaio.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Producer {
    private Long id;
    @JsonProperty("full_name")
    private String name;
    private LocalDateTime createdAt;
    private static List<Producer> producers = new ArrayList<>();

    static {
        Producer anime1 = builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        Producer anime2 = builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        Producer anime3 = builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        producers.addAll(List.of(anime1, anime2, anime3));
    }

    public static List<Producer> getProducers() {
        return producers;
    }
}

