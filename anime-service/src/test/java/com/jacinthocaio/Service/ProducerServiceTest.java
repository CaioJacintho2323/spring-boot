package com.jacinthocaio.Service;

import com.jacinthocaio.domain.Producer;
import com.jacinthocaio.repository.ProducerHardCodedRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ProducerServiceTest {
    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerHardCodedRepository repository;

    private List<Producer> producersList;

    @BeforeEach
    void init(){
        var ufotable = Producer.builder().id(1L).name("ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("witStudio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("studioGhibli").createdAt(LocalDateTime.now()).build();
        producersList = new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));

    }

    @Test
    @DisplayName("findAll return a list with all producers when argument is null")
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
}