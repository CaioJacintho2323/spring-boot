package com.jacinthocaio.domain;



import lombok.*;

import java.util.List;


public class Anime {
    private Long id;
    private String name;

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Anime> getAnimes() {
        var anime1 = new Anime(1L, "Caio Jacintho");
        var anime2 = new Anime(2L, "Lucca Jacintho");
        var anime3 = new Anime(3L, "Rivanda Mota");

        return List.of(anime1, anime2, anime3);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}


