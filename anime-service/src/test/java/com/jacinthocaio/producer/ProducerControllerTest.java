package com.jacinthocaio.producer;

import com.jacinthocaio.commons.FileUtils;
import com.jacinthocaio.commons.ProducerUtils;
import com.jacinthocaio.domain.Producer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"com.jacinthocaio.producer", "com.jacinthocaio.commons"})
//@Import({ProducerMapperImpl.class, ProducerService.class, ProducerHardCodedRepository.class, ProducerData.class})
class ProducerControllerTest {
    private static final String URL = "/v1/producers";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProducerRepository repository;
    private List<Producer> producerList;
    @Autowired
    private FileUtils fileUtils;
    @InjectMocks
    private ProducerUtils producerUtils;

    @BeforeEach
    void init() {
        producerList = producerUtils.newProducerList();
    }

    @Test
    @DisplayName("GET v1/producers returns a list with all producers when argument is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(repository.findAll()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers?name=Ufotable returns list with found object when name exists")
    @Order(2)
    void findAll_ReturnsFoundProducerInList_WhenNameIsFound() throws Exception {
        var response = fileUtils.readResourceFile("producer/get-producer-ufotable-name-200.json");
        var name = "Ufotable";
        var ufotable = producerList.stream().filter(producer -> producer.getName().equals(name)).findFirst().orElse(null);

        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.singletonList(ufotable));

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers?name=x returns empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        var response = fileUtils.readResourceFile("producer/get-producer-x-name-200.json");
        var name = "x";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers/1 returns a producer with given id")
    @Order(4)
    void findById_ReturnsProducerById_WhenSuccessful() throws Exception {
        BDDMockito.when(repository.findAll()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-by-id-200.json");
        var id = 1L;
        var foundProducer = producerList.stream().filter(anime -> anime.getId().equals(id)).findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(foundProducer);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers/99 throws NotFound 404 when producer is not found")
    @Order(5)
    void findById_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        var response = fileUtils.readResourceFile("producer/get-producer-by-id-404.json");
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("POST v1/producers creates a producer")
    @Order(6)
    void save_CreatesProducer_WhenSuccessful() throws Exception {
        var request = fileUtils.readResourceFile("producer/post-request-producer-200.json");
        var response = fileUtils.readResourceFile("producer/post-response-producer-201.json");
        var producerToSave = Producer.builder().id(99L).name("MAPPA").createdAt(LocalDateTime.now()).build();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT v1/producers updates a producer")
    @Order(7)
    void update_UpdatesProducer_WhenSuccessful() throws Exception {
        var request = fileUtils.readResourceFile("producer/put-request-producer-200.json");
        var id = 1L;
        var foundProducer = producerList.stream().filter(producer -> producer.getId().equals(id)).findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(foundProducer);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT v1/producers throws NotFound when producer is not found")
    @Order(8)
    void update_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        var request = fileUtils.readResourceFile("producer/put-request-producer-404.json");
        var response = fileUtils.readResourceFile("producer/put-producer-by-id-404.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("DELETE v1/producers/1 removes a producer ")
    @Order(7)
    void delete_RemoveProducer_WhenSuccessful() throws Exception {
        var id = producerList.getFirst().getId();
        var foundProducer = producerList.stream().filter(producer -> producer.getId().equals(id)).findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(foundProducer);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @DisplayName("DELETE v1/producers/99 throws NotFound when producer is not found ")
    @Order(8)
    void delete_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        var response = fileUtils.readResourceFile("producer/delete-producer-by-id-404.json");
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @ParameterizedTest
    @MethodSource("postProducerBadRequestSource")
    @DisplayName("POST v1/producer returns bad request when fields are invalid")
    @Order(9)
    void save_ReturnsBadRequest_WhenFieldsAreBlank(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("producer/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        assertThat(resolvedException).isNotNull();

        assertThat(resolvedException.getMessage()).contains(errors);


    }

    @ParameterizedTest
    @MethodSource("putAnimeBadRequestSource")
    @DisplayName("UPDATE v1/anime returns bad request when fields are invalid")
    @Order(10)
    void update_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("producer/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        assertThat(resolvedException).isNotNull();

        assertThat(resolvedException.getMessage()).contains(errors);


    }

    private static Stream<Arguments> putAnimeBadRequestSource() throws Exception {
        var allErrors = allError();
        allErrors.add("O id não pode estar nulo");


        return Stream.of(
                Arguments.of("put-request-producer-blank-fields-400.json", allErrors),
                Arguments.of("put-request-producer-empty-fields-400.json", allErrors)
        );
    }

    private static Stream<Arguments> postProducerBadRequestSource() throws Exception {
        var allErrors = allError();

        return Stream.of(
                Arguments.of("post-request-producer-blank-fields-400.json", allErrors),
                Arguments.of("post-request-producer-empty-fields-400.json", allErrors));
    }


    private static List<String> allError() {
        var nameRequiredError = "O nome não pode estar vazio";

        return new ArrayList<>(List.of(nameRequiredError));
    }

}