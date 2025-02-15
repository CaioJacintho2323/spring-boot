package com.jacinthocaio.repository;

import com.jacinthocaio.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {
    private final List<Anime> animes = new ArrayList<>();

    {
        Anime anime1 = Anime.builder().id(1L).name("Mappa").build();
        Anime anime2 = Anime.builder().id(2L).name("Kyoto Animation").build();
        Anime anime3 = Anime.builder().id(3L).name("Madhouse").build();
        animes.addAll(List.of(anime1, anime2, anime3));
    }

    public List<Anime> getAnimes() {
        return animes;
    }
}
