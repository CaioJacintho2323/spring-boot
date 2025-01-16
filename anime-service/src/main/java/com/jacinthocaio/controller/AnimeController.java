package com.jacinthocaio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = {"v1/animes","v1/animes/"})
public class AnimeController {

    @GetMapping()
    public List<String> returnAnimes(){
        return List.of("Anime 1", "Anime 2", "Anime 3", "Anime 4", "Anime 5");
    }
}
