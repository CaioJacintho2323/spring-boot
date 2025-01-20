package com.jacinthocaio.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Anime {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private static List<Anime> animes = new ArrayList<>();

    static {
        var anime1 = new Anime(1L, "Caio");
        var anime2 = new Anime(2L, "Lucca Jacintho");
        var anime3 = new Anime(3L, "Rivanda Mota");
        animes.addAll(List.of(anime1, anime2, anime3));
    }

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public static List<Anime> getAnimes() {
        return animes;
    }
}


