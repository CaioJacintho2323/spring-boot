package com.jacinthocaio.controller;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/greetings")
public class HelloController {


    @GetMapping
    public String hi() {
        return "OMAE WA MOU SHINDE IRU";
    }

    @PostMapping
    public Long save(@RequestBody String name) {
        return ThreadLocalRandom.current().nextLong(1, 1000);
    }


}
