package com.jacinthocaio.controller;

import com.jacinthocaio.domain.Anime;
import com.jacinthocaio.mapper.ProducerMapper;
import com.jacinthocaio.request.AnimePostRequest;
import com.jacinthocaio.request.AnimePutRequest;
import com.jacinthocaio.request.ProducerPostRequest;
import com.jacinthocaio.response.AnimeGetResponse;
import com.jacinthocaio.response.AnimePostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jacinthocaio.mapper.AnimeMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;



@RestController
@RequestMapping("v1/anime")
@Slf4j
public class AnimeController {
    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;

//    @GetMapping()
//    public List<String> listAll() throws InterruptedException {
//       log.info(Thread.currentThread().getName());
//       TimeUnit.SECONDS.sleep(2);
//        return List.of("Anime 1", "Anime 2", "Anime 3", "Anime 4", "Anime 5");
//

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request received to list all animes, param name : '{}'", name);
        var animes = Anime.getAnimes();
        List<AnimeGetResponse> animeGetResponseList = MAPPER.toAnimeGetResponseList(animes);
        if (name == null) return ResponseEntity.ok(animeGetResponseList);
        var response = animeGetResponseList
                .stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById (@PathVariable Long id) {
        log.debug("Request to find anime by id: {}", id);
        var anime = Anime
                .getAnimes()
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .map(MAPPER::toAnimeGetResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
        return ResponseEntity.ok(anime);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest animePostRequest) {
        log.debug("Request to save anime : {}", animePostRequest);
        var postRequest = MAPPER.toAnime(animePostRequest);
        var response = MAPPER.toAnimePostResponse(postRequest);
        Anime.getAnimes().add(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete anime by id: {}", id);
        var anime = Anime.getAnimes()
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
        Anime.getAnimes().remove(anime);
        return ResponseEntity.noContent().build();
    }


    @PutMapping()
    public ResponseEntity<Void> update (@RequestBody AnimePutRequest request) {
        log.debug("Request to update anime : {}", request);
        var animeToRemove = Anime.getAnimes()
                .stream()
                .filter(a -> a.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
        var animeUpdate = MAPPER.toAnime(request);
        Anime.getAnimes().remove(animeToRemove);
        Anime.getAnimes().add(animeUpdate);
        return ResponseEntity.noContent().build();
    }


}
