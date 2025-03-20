package com.jacinthocaio.repository;

import com.jacinthocaio.commons.AnimeUtils;
import com.jacinthocaio.domain.Anime;
import com.jacinthocaio.domain.Producer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class AnimeHardCodedRepositoryTest {

    @InjectMocks
    private AnimeHardCodedRepository repository;

    @Mock
    private AnimeData animeData;


    private List<Anime> animeList;
    @InjectMocks
    private AnimeUtils animeUtils;

    @BeforeEach
    void init(){
        animeList = animeUtils.newProducerList();
    }
    

    @Test
    @DisplayName("findAll return a list with all animes")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animes = repository.findAll();
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().hasSize(animes.size());
    }

    @Test
    @DisplayName("findAll return a anime with given id ")
    @Order(2)
    void findById_ReturnsAnimesById_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeListFirst = animeList.getFirst();
        var anime = repository.findById(animeListFirst.getId());
        org.assertj.core.api.Assertions.assertThat(anime).isPresent().contains(animeListFirst);
    }

    @Test
    @DisplayName("findAll returns empty list when name is null ")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animes = repository.findByName(null);
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findAll returns list when found object when name exists ")
    @Order(4)
    void findByName_ReturnsFoundAnimeInList_WhenNameIsFound() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeListFirst = animeList.getFirst();
        var anime = repository.findByName(animeListFirst.getName());
        org.assertj.core.api.Assertions.assertThat(anime).contains(animeListFirst);
    }

    @Test
    @DisplayName("save creates a anime")
    @Order(5)
    void save_CreatesAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeToSave = Anime.builder()
                .id(99L)
                .name("Caio")
                .build();
        var anime = repository.save(animeToSave);
        org.assertj.core.api.Assertions.assertThat(anime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();
        Optional<Anime> animeOptional = repository.findById(animeToSave.getId());
        org.assertj.core.api.Assertions.assertThat(animeOptional).isPresent().contains(animeToSave);
    }
    @Test
    @DisplayName("delete removes a anime ")
    @Order(6)
    void delete_RemoveAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeListFirst = animeList.getFirst();
        repository.delete(animeListFirst);
        org.assertj.core.api.Assertions.assertThat(this.animeList).doesNotContain(animeListFirst);
    }

    @Test
    @DisplayName("update updates a anime")
    @Order(5)
    void update_UpdatesAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeListFirst = this.animeList.getFirst();
        animeListFirst.setName("Aniplex");
        repository.update(animeListFirst);
        org.assertj.core.api.Assertions.assertThat(this.animeList).contains(animeListFirst);
        var animeOptional = repository.findById(animeListFirst.getId());
        org.assertj.core.api.Assertions.assertThat(animeOptional).isPresent();
        org.assertj.core.api.Assertions.assertThat(animeOptional.get().getName()).isEqualTo(animeListFirst.getName());


    }

}