package com.jacinthocaio.domain;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private static List<Anime> animes = new ArrayList<>();

    static {
        var anime1 = Anime.builder().id(1L).name("Anime1").build();
        var anime2 = Anime.builder().id(2L).name("Anime2").build();
        var anime3 = Anime.builder().id(3L).name("Anime3").build();
        animes.addAll(List.of(anime1, anime2, anime3));
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}


