package com.jacinthocaio.user_service.service;

import com.jacinthocaio.user_service.commons.UserUtils;
import com.jacinthocaio.user_service.dominio.User;
import com.jacinthocaio.user_service.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = "com.jacinthocaio.user_service")
class UserServiceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;

    private List<User> usersList;

    @InjectMocks
    private UserUtils userUtils;

    @BeforeEach
    void init(){
        usersList = userUtils.newUserList();
    }

    @Test
    @DisplayName("findAll return a list with all users3 when argument is null")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(usersList);
        var users = service.findAll(null);
        org.assertj.core.api.Assertions.assertThat(users).isNotNull().hasSize(users.size());
    }

    @Test
    @DisplayName("findAll returns list when found object when firstName exists ")
    @Order(2)
    void findByName_ReturnsFoundUserInList_WhenFirstNameIsFound() {
        var usersListFirst = usersList.getFirst();
        List<User> expectedUserFound = singletonList(usersListFirst);

        BDDMockito.when(repository.findByFirstNameIgnoreCase(usersListFirst.getFirstName())).thenReturn(expectedUserFound);

        var users = service.findAll(usersListFirst.getFirstName());
        org.assertj.core.api.Assertions.assertThat(users).containsAll(expectedUserFound);
    }

    @Test
    @DisplayName("findAll returns empty list when firstName is not found ")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenFirstNameIsNotFound() {
        var name = "not-found";
        BDDMockito.when(repository.findByFirstNameIgnoreCase(name)).thenReturn(emptyList());
        var users = service.findAll(name);
        org.assertj.core.api.Assertions.assertThat(users).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById return a users with given id ")
    @Order(4)
    void findById_ReturnsUserById_WhenSuccessful() {
        var expectedUser = usersList.getFirst();
        BDDMockito.when(repository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        var users = service.findById(expectedUser.getId());
        org.assertj.core.api.Assertions.assertThat(users).isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("findId throws ResponseStatusException when users is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var expectedUser = usersList.getFirst();
        BDDMockito.when(repository.findById(expectedUser.getId())).thenReturn(Optional.empty());
        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.findById(expectedUser.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @DisplayName("save creates a users")
    @Order(6)
    void save_CreatesUser_WhenSuccessful() {
        var userToSave = User.builder()
                .id(99L)
                .firstName("enzo")
                .lastName("levy")
                .email("enzolevy@gmail.com")
                .build();
        BDDMockito.when(repository.save(userToSave)).thenReturn(userToSave);
        var savedUser = service.createUser(userToSave);
        org.assertj.core.api.Assertions.assertThat(savedUser).isEqualTo(userToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes an users ")
    @Order(7)
    void delete_RemoveUser_WhenSuccessful() {
        var expectedUserToDelete = usersList.getFirst();

        BDDMockito.when(repository.findById(expectedUserToDelete.getId()))
                .thenReturn(Optional.of(expectedUserToDelete));

        BDDMockito.doNothing().when(repository).delete(expectedUserToDelete);
        org.assertj.core.api.Assertions.assertThatNoException().isThrownBy(() ->service.deleteUser(expectedUserToDelete));

    }

    @Test
    @DisplayName("delete throws ResponseStatusException when users is not found ")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var expectedUserToDelete = usersList.getFirst();

        BDDMockito.when(repository.findById(expectedUserToDelete.getId()))
                .thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.deleteUser(expectedUserToDelete))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @DisplayName("update updates a users")
    @Order(9)
    void update_UpdatesUser_WhenSuccessful() {
        var expectedUserToUpdate = usersList.getFirst();
        expectedUserToUpdate.setFirstName("Aniplex");

        BDDMockito.when(repository.findById(expectedUserToUpdate.getId()))
                .thenReturn(Optional.of(expectedUserToUpdate));

        BDDMockito.when(repository.save(expectedUserToUpdate)).thenReturn(expectedUserToUpdate);

        service.update(expectedUserToUpdate);


        org.assertj.core.api.Assertions.assertThatNoException().isThrownBy(() -> service.update(expectedUserToUpdate));

    }

    @Test
    @DisplayName("update throws ResponseStatusException when users is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var expectedUserToUpdate = usersList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.update(expectedUserToUpdate))
                .isInstanceOf(ResponseStatusException.class);


    }

}