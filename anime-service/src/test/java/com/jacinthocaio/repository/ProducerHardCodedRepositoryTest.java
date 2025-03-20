package com.jacinthocaio.repository;

import com.jacinthocaio.commons.ProducerUtils;
import com.jacinthocaio.domain.Producer;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapping;
import org.mockito.BDDMockito;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ProducerHardCodedRepositoryTest {
    @InjectMocks
    private ProducerHardCodedRepository repository;

    @Mock
    private ProducerData producerData;

    private List<Producer> producersList;

    @InjectMocks
    private ProducerUtils producerUtils;

    @BeforeEach
    void init(){
        producersList = producerUtils.newProducerList();
    }

    @Test
    @DisplayName("findAll return a list with all producers")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);
        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().hasSize(producers.size());
    }

    @Test
    @DisplayName("findAll return a producer with given id ")
    @Order(2)
    void findById_ReturnsProducerById_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);
        var expectedProducer = producersList.getFirst();
        var producers = repository.findById(expectedProducer.getId());
        Assertions.assertThat(producers).isPresent().contains(expectedProducer);
    }

    @Test
    @DisplayName("findAll returns empty list when name is null ")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);
        var producers = repository.findByName(null);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findAll returns list when found object when name exists ")
    @Order(4)
    void findByName_ReturnsFoundProducerInList_WhenNameIsFound() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);
        var expectedProducer = producersList.getFirst();
        var producers = repository.findByName(expectedProducer.getName());
        Assertions.assertThat(producers).contains(expectedProducer);
    }

    @Test
    @DisplayName("save creates a producer")
    @Order(5)
    void save_CreatesProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);
        var producerToSave = Producer.builder()
                .id(99L)
                .name("Caio")
                .createdAt(LocalDateTime.now())
                .build();
        var producer = repository.save(producerToSave);
        Assertions.assertThat(producer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
        Optional<Producer> producerSavedOptional = repository.findById(producerToSave.getId());
        Assertions.assertThat(producerSavedOptional).isPresent().contains(producerToSave);
    }
    @Test
    @DisplayName("delete removes a producer ")
    @Order(6)
    void delete_RemoveProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);
        var producerToDelete = producersList.getFirst();
        repository.delete(producerToDelete);
        Assertions.assertThat(this.producersList).doesNotContain(producerToDelete);
    }

    @Test
    @DisplayName("update updates a producer")
    @Order(5)
    void update_UpdatesProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);
        var producerToUpdate = this.producersList.getFirst();
        producerToUpdate.setName("Aniplex");
        repository.update(producerToUpdate);
        Assertions.assertThat(this.producersList).contains(producerToUpdate);
        var producerUpdatedOptional = repository.findById(producerToUpdate.getId());
        Assertions.assertThat(producerUpdatedOptional).isPresent();
        Assertions.assertThat(producerUpdatedOptional.get().getName()).isEqualTo(producerToUpdate.getName());


    }

}