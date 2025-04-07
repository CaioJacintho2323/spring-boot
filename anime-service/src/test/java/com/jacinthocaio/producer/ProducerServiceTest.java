package com.jacinthocaio.producer;

import com.jacinthocaio.commons.ProducerUtils;
import com.jacinthocaio.domain.Producer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ProducerServiceTest {
    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerRepository repository;

    private List<Producer> producersList;

    @InjectMocks
    private ProducerUtils producerUtils;

    @BeforeEach
    void init(){
        producersList = producerUtils.newProducerList();
    }

    @Test
    @DisplayName("findAll return a list with all producers3 when argument is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(producersList);
        var producers = service.findAll(null);
        org.assertj.core.api.Assertions.assertThat(producers).isNotNull().hasSize(producers.size());
    }

    @Test
    @DisplayName("findAll returns list when found object when name exists ")
    @Order(2)
    void findByName_ReturnsFoundProducerInList_WhenNameIsFound() {
        var producer = producersList.getFirst();
        List<Producer> expectedProducerFound = singletonList(producer);

        BDDMockito.when(repository.findByName(producer.getName())).thenReturn(expectedProducerFound);

        var producers = service.findAll(producer.getName());
        org.assertj.core.api.Assertions.assertThat(producers).containsAll(expectedProducerFound);
    }

    @Test
    @DisplayName("findAll returns empty list when name is not found ")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "not-found";
        BDDMockito.when(repository.findByName(name)).thenReturn(emptyList());
        var producers = service.findAll(name);
        org.assertj.core.api.Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById return a producer with given id ")
    @Order(4)
    void findById_ReturnsProducerById_WhenSuccessful() {
        var expectedProducer = producersList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));
        var producers = service.findByIdOrThrowNotFound(expectedProducer.getId());
        org.assertj.core.api.Assertions.assertThat(producers).isEqualTo(expectedProducer);
    }

    @Test
    @DisplayName("findId throws ResponseStatusException when producer is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expectedProducer = producersList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.empty());
        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @DisplayName("save creates a producer")
    @Order(6)
    void save_CreatesProducer_WhenSuccessful() {
        var producerToSave = Producer.builder()
                .id(99L)
                .name("Caio")
                .createdAt(LocalDateTime.now())
                .build();
        BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);
        var savedProducer = service.save(producerToSave);
        org.assertj.core.api.Assertions.assertThat(savedProducer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a producer ")
    @Order(7)
    void delete_RemoveProducer_WhenSuccessful() {
        var expectedProducerToDelete = producersList.getFirst();

        BDDMockito.when(repository.findById(expectedProducerToDelete.getId()))
                .thenReturn(Optional.of(expectedProducerToDelete));

        BDDMockito.doNothing().when(repository).delete(expectedProducerToDelete);
        org.assertj.core.api.Assertions.assertThatNoException().isThrownBy(() ->service.delete(expectedProducerToDelete.getId()));

    }

    @Test
    @DisplayName("delete throws ResponseStatusException when producer is not found ")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expectedProducerToDelete = producersList.getFirst();

        BDDMockito.when(repository.findById(expectedProducerToDelete.getId()))
                .thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.delete(expectedProducerToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @DisplayName("update updates a producer")
    @Order(9)
    void update_UpdatesProducer_WhenSuccessful() {
        var expectedProducerToUpdate = producersList.getFirst();
        expectedProducerToUpdate.setName("Aniplex");

        BDDMockito.when(repository.findById(expectedProducerToUpdate.getId()))
                .thenReturn(Optional.of(expectedProducerToUpdate));

        BDDMockito.when(repository.save(expectedProducerToUpdate)).thenReturn(expectedProducerToUpdate);

        service.updated(expectedProducerToUpdate);


        org.assertj.core.api.Assertions.assertThatNoException().isThrownBy(() ->service.updated(expectedProducerToUpdate));

    }

    @Test
    @DisplayName("update throws ResponseStatusException when producer is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expectedProducerToUpdate = producersList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.updated(expectedProducerToUpdate))
                .isInstanceOf(ResponseStatusException.class);


    }


}