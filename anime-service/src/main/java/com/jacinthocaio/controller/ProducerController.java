package com.jacinthocaio.controller;

import com.jacinthocaio.domain.Producer;
import com.jacinthocaio.mapper.ProducerMapper;
import com.jacinthocaio.request.ProducerPostRequest;
import com.jacinthocaio.response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
        private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

//    @GetMapping()
//    public List<String> listAll() throws InterruptedException {
//       log.info(Thread.currentThread().getName());
//       TimeUnit.SECONDS.sleep(2);
//        return List.of("Producer 1", "Producer 2", "Producer 3", "Producer 4", "Producer 5");
//

    @GetMapping
    public List<Producer> listAll(@RequestParam(required = false) String name) {
        var producers = Producer.getProducers();
        if (name == null) return producers;
        return producers
                .stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .toList();
    }

    @GetMapping("{id}")
    public Producer findById (@PathVariable Long id) {
        return Producer
                .getProducers()
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest) {
        var producer1 = MAPPER.toProducer(producerPostRequest);
        var getResponse = MAPPER.toProducerGetResponse(producer1);
        Producer.getProducers().add(producer1);
        return ResponseEntity.status(HttpStatus.CREATED).body(getResponse);
    }
}
