package com.jacinthocaio.commons;

import com.jacinthocaio.domain.Anime;
import com.jacinthocaio.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {
    public List<Anime> newProducerList(){
        var fullMetal = Anime.builder().id(1L).name("Full Metal Brotherhood").build();
        var steinsGate = Anime.builder().id(2L).name("Steins Gate").build();
        var mashle = Anime.builder().id(3L).name("Mashle").build();
        return new ArrayList<>(List.of(fullMetal, steinsGate, mashle));
    }
}
