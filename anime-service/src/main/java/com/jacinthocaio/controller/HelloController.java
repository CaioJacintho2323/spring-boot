package com.jacinthocaio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloController {


    @GetMapping("hi")
    public String hi(){
        return "OMAE WA MOU SHINDE IRU";
    }



}
