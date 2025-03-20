package com.jacinthocaio.user_service.controller;


import com.jacinthocaio.user_service.commons.FileUtils;
import com.jacinthocaio.user_service.commons.UserUtils;
import com.jacinthocaio.user_service.dominio.User;
import com.jacinthocaio.user_service.repository.UserData;
import com.jacinthocaio.user_service.repository.UserHardCodedRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(controllers = Usercontroller.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.jacinthocaio.user_service")
class UsercontrollerTest {
    private static final String URL = "/v1/users";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserData userData;

    @SpyBean
    private UserHardCodedRepository repository;
    private List<User> usersList;

    @Autowired
    private FileUtils fileUtils;

    @InjectMocks
    private UserUtils userUtils;

    @BeforeEach
    void init() {
        usersList = userUtils.newUserList();
    }

    @Test
    @DisplayName("GET v1/users returns a list with all users when argument is null")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var response = fileUtils.readResourceFile("user/get-user-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/users?name=Mashle returns list with found object when name exists")
    @Order(2)
    void findAll_ReturnsFoundUserList_WhenNameIsFound() throws Exception {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var response = fileUtils.readResourceFile("user/get-user-cesar-name-200.json");
        var name = "Cesar";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/users?name=x returns empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var response = fileUtils.readResourceFile("user/get-user-x-name-200.json");
        var name = "x";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/users/1 returns an user with given id")
    @Order(4)
    void findById_ReturnsUserById_WhenSuccessful() throws Exception {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var response = fileUtils.readResourceFile("user/get-user-by-id-200.json");
        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL +"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/users/99 throws ResponseStatusException 404 when user is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenUserIsNotFound() throws Exception {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL +"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));
    }

    @Test
    @DisplayName("POST v1/users creates an user")
    @Order(6)
    void save_CreatesUser_WhenSuccessful() throws Exception {
        var request = fileUtils.readResourceFile("user/post-request-user-200.json");
        var response = fileUtils.readResourceFile("user/post-response-user-201.json");
        var userToSave = User.builder().id(99L).firstName("Overlord").lastName("yield").email("caca@gmail.com").build();

        BDDMockito.when(repository.createUser(ArgumentMatchers.any())).thenReturn(userToSave);

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
    @DisplayName("PUT v1/users updates an user")
    @Order(7)
    void update_UpdatesUser_WhenSuccessful() throws Exception {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);

        var request = fileUtils.readResourceFile("user/put-request-user-200.json");
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT v1/users throws ResponseStatusException when user is not found")
    @Order(8)
    void update_ThrowsResponseStatusException_WhenUserIsNotFound() throws Exception {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var request = fileUtils.readResourceFile("user/put-request-user-404.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));

    }

    @Test
    @DisplayName("DELETE v1/users/1 removes an user")
    @Order(9)
    void delete_RemoveUser_WhenSuccessful() throws Exception {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);

        var id = usersList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL +"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/users/99 throws ResponseStatusException when user is not found")
    @Order(10)
    void delete_ThrowsResponseStatusException_WhenUserIsNotFound() throws Exception {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL +"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));

    }

}