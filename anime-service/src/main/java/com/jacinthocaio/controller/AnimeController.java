package com.jacinthocaio.controller;

import com.jacinthocaio.Service.AnimeService;
import com.jacinthocaio.mapper.AnimeMapper;
import com.jacinthocaio.request.AnimePostRequest;
import com.jacinthocaio.request.AnimePutRequest;
import com.jacinthocaio.response.AnimeGetResponse;
import com.jacinthocaio.response.AnimePostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("v1/anime")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeMapper mapper;
    private final AnimeService service;

//    @GetMapping()
//    public List<String> listAll() throws InterruptedException {
//       log.info(Thread.currentThread().getName());
//       TimeUnit.SECONDS.sleep(2);
//        return List.of("Anime 1", "Anime 2", "Anime 3", "Anime 4", "Anime 5");
//

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request received to list all animes, param name : '{}'", name);
        var all = service.findAll(name);
        List<AnimeGetResponse> animeGetResponseList = mapper.toAnimeGetResponseList(all);
        return ResponseEntity.ok(animeGetResponseList);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find anime by id: {}", id);
        var byIdOrThrowNotFound = service.findByIdOrThrowNotFound(id);
        var animeGetResponse = mapper.toAnimeGetResponse(byIdOrThrowNotFound);
        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest animePostRequest) {
        log.debug("Request to save anime : {}", animePostRequest);
        var postRequest = mapper.toAnime(animePostRequest);
        var save = service.save(postRequest);
        var response = mapper.toAnimePostResponse(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete anime by id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.debug("Request to update anime : {}", request);
        var animeUpdate = mapper.toAnime(request);
        service.updated(animeUpdate);
        return ResponseEntity.noContent().build();
    }


}
