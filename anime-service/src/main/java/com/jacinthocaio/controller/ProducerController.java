package com.jacinthocaio.controller;

import com.jacinthocaio.Service.ProducerService;
import com.jacinthocaio.mapper.ProducerMapper;
import com.jacinthocaio.request.ProducerPostRequest;
import com.jacinthocaio.request.ProducerPutRequest;
import com.jacinthocaio.response.ProducerGetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerMapper mapper;
    private final ProducerService service;

//    @GetMapping()
//    public List<String> listAll() throws InterruptedException {
//       log.info(Thread.currentThread().getName());
//       TimeUnit.SECONDS.sleep(2);
//        return List.of("Producer 1", "Producer 2", "Producer 3", "Producer 4", "Producer 5");
//


    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request received to list all producers, param name '{}'", name);
        var producers = service.findAll(name);

        var producerGetResponses = mapper.toProducerGetResponseList(producers);

        return ResponseEntity.ok(producerGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find producer by id: {}", id);
        var producer = service.findByIdOrThrowNotFound(id);

        var producerGetResponse = mapper.toProducerGetResponse(producer);

        return ResponseEntity.ok(producerGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        var producer = mapper.toProducer(producerPostRequest);

        var producerSaved = service.save(producer);

        var producerPostResponse = mapper.toProducerGetResponse(producerSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(producerPostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete producer by id: {}", id);
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        var producerToUpdate = mapper.toProducer(request);

        service.updated(producerToUpdate);

        return ResponseEntity.noContent().build();
    }
}
