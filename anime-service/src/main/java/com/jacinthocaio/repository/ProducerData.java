package com.jacinthocaio.repository;

import com.jacinthocaio.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
public class ProducerData {
    private final List<Producer> producers = new ArrayList<>();

    {
        Producer anime1 = Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        Producer anime2 = Producer.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        Producer anime3 = Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        producers.addAll(List.of(anime1, anime2, anime3));
    }

    public List<Producer> getProducers() {
        return producers;
    }
}
