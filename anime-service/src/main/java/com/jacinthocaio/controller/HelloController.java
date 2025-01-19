package com.jacinthocaio.controller;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/greetings")
public class HelloController {


    @GetMapping
    public String hi(){
        return "OMAE WA MOU SHINDE IRU";
    }

    @PostMapping
    public Long save(@RequestBody String name){
        return ThreadLocalRandom.current().nextLong(1,1000);
    }


}
