package com.jacinthocaio.anime;

import com.jacinthocaio.commons.AnimeUtils;
import com.jacinthocaio.domain.Anime;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeRepository repository;

    private List<Anime> animesList;
    @InjectMocks
    private AnimeUtils animeUtils;

    @BeforeEach
    void init(){
        animesList = animeUtils.newProducerList();
    }

    @Test
    @DisplayName("findAll return a list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(animesList);
        var animes = service.findAll(null);
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().hasSize(animes.size());
    }
    @Test
    @DisplayName("findAllPaginated return a paginated list of animes")
    @Order(1)
    void findAllPaginated_ReturnsPaginatedAnimes_WhenSuccessful() {
        var pageRequest = PageRequest.of(0, animesList.size());
        var pageAnime = new PageImpl<>(animesList, pageRequest, animesList.size());

        BDDMockito.when(repository.findAll(BDDMockito.any(Pageable.class))).thenReturn(pageAnime);

        var animesFound = service.findAllPaginated(pageRequest);

        org.assertj.core.api.Assertions.assertThat(animesFound).hasSameElementsAs(animesList);
    }

    @Test
    @DisplayName("findAll returns list when found object when name exists ")
    @Order(2)
    void findByName_ReturnsFoundAnimeInList_WhenNameIsFound() {
        var animesListFirst = animesList.getFirst();
        List<Anime> expectedAnimeFound = singletonList(animesListFirst);

        BDDMockito.when(repository.findByName(animesListFirst.getName())).thenReturn(expectedAnimeFound);

        var animes = service.findAll(animesListFirst.getName());
        org.assertj.core.api.Assertions.assertThat(animes).containsAll(expectedAnimeFound);
    }

    @Test
    @DisplayName("findAll returns empty list when name is not found ")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "not-found";
        BDDMockito.when(repository.findByName(name)).thenReturn(emptyList());
        var animes = service.findAll(name);
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById return a animes with given id ")
    @Order(4)
    void findById_ReturnsAnimeById_WhenSuccessful() {
        var expectedAnime = animesList.getFirst();
        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.of(expectedAnime));
        var animes = service.findByIdOrThrowNotFound(expectedAnime.getId());
        org.assertj.core.api.Assertions.assertThat(animes).isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("findId throws ResponseStatusException when animes is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var expectedAnime = animesList.getFirst();
        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.empty());
        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedAnime.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @DisplayName("save creates a animes")
    @Order(6)
    void save_CreatesAnime_WhenSuccessful() {
        var animeToSave = Anime.builder()
                .id(99L)
                .name("Caio")
                .build();
        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);
        var savedAnime = service.save(animeToSave);
        org.assertj.core.api.Assertions.assertThat(savedAnime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a animes ")
    @Order(7)
    void delete_RemoveAnime_WhenSuccessful() {
        var expectedAnimeToDelete = animesList.getFirst();

        BDDMockito.when(repository.findById(expectedAnimeToDelete.getId()))
                .thenReturn(Optional.of(expectedAnimeToDelete));

        BDDMockito.doNothing().when(repository).delete(expectedAnimeToDelete);
        org.assertj.core.api.Assertions.assertThatNoException().isThrownBy(() ->service.delete(expectedAnimeToDelete.getId()));

    }

    @Test
    @DisplayName("delete throws ResponseStatusException when animes is not found ")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var expectedAnimeToDelete = animesList.getFirst();

        BDDMockito.when(repository.findById(expectedAnimeToDelete.getId()))
                .thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.delete(expectedAnimeToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @DisplayName("update updates a animes")
    @Order(9)
    void update_UpdatesAnime_WhenSuccessful() {
        var expectedAnimeToUpdate = animesList.getFirst();
        expectedAnimeToUpdate.setName("Aniplex");

        BDDMockito.when(repository.findById(expectedAnimeToUpdate.getId()))
                .thenReturn(Optional.of(expectedAnimeToUpdate));

        BDDMockito.when(repository.save(expectedAnimeToUpdate)).thenReturn(expectedAnimeToUpdate);

        service.updated(expectedAnimeToUpdate);


        org.assertj.core.api.Assertions.assertThatNoException().isThrownBy(() ->service.updated(expectedAnimeToUpdate));

    }

    @Test
    @DisplayName("update throws ResponseStatusException when animes is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var expectedAnimeToUpdate = animesList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.updated(expectedAnimeToUpdate))
                .isInstanceOf(ResponseStatusException.class);


    }
}