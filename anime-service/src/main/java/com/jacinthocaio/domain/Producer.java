package com.jacinthocaio.domain;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public class Producer {
    private Long id;
    @JsonProperty("full_name")
    private String name;
    private LocalDateTime createdAt;
    private static List<Producer> producers = new ArrayList<>();

    static {
        var anime1 =  Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var anime2 =  Producer.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var anime3 =  Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        producers.addAll(List.of(anime1, anime2, anime3));
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Producer> getProducers() {
        return producers;
    }
}


