package com.jacinthocaio.controller;

import com.jacinthocaio.domain.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

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
    public List<Anime> listAll() {
        return Anime.getAnimes();
    }
}
