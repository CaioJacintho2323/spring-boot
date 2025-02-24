package com.jacinthocaio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jacinthocaio")
public class AnimeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimeServiceApplication.class, args);
    }

}
