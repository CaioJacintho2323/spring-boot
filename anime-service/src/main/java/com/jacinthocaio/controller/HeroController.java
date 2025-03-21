package com.jacinthocaio.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/heroes")
public class HeroController {
    private static final List<String> HEROES = List.of("Guts", "Iron man", "Thor");

    @GetMapping
    public List<String> listAllHeroes() {
        return HEROES;
    }

    @GetMapping("filter")
    public List<String> listAllHeroesParam(@RequestParam(required = false, defaultValue = "") String name) {
        return HEROES.stream().filter(hero -> hero.equalsIgnoreCase(name)).toList();
    }

    @GetMapping("filterList")
    public List<String> listAllHeroesParamList(@RequestParam(defaultValue = "") List<String> names) {
        return HEROES.stream().filter(names::contains).collect(Collectors.toList());
    }

    @GetMapping("{name}")
    public String findByName(@PathVariable String name) {
        return HEROES
                .stream()
                .filter(hero -> hero.equalsIgnoreCase(name))
                .findFirst()
                .orElse("");
    }

}
