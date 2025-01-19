package com.jacinthocaio.controller;

import com.jacinthocaio.domain.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/anime")
@Slf4j
public class AnimeController {

//    @GetMapping()
//    public List<String> listAll() throws InterruptedException {
//       log.info(Thread.currentThread().getName());
//       TimeUnit.SECONDS.sleep(2);
//        return List.of("Anime 1", "Anime 2", "Anime 3", "Anime 4", "Anime 5");
//

    @GetMapping
    public List<Anime> listAll(@RequestParam(required = false) String name) {
        var animes = Anime.getAnimes();
        if (name == null) return animes;
        return animes
                .stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .toList();
    }

    @GetMapping("{id}")
    public Anime findById (@PathVariable Long id) {
        return Anime
                .getAnimes()
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Anime save(@RequestBody Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(100));
        Anime.getAnimes().add(anime);
        return anime;
    }
}
