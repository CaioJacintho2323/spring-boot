package com.jacinthocaio.user_service.repository;

import com.jacinthocaio.user_service.commons.UserUtils;
import com.jacinthocaio.user_service.dominio.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class UserHardCodedRepositoryTest {
    @InjectMocks
    private UserHardCodedRepository repository;

    @Mock
    private UserData userData;

    private List<User> usersList;

    @InjectMocks
    private UserUtils userUtils;

    @BeforeEach
    void init(){
        usersList = userUtils.newUserList();
    }

    @Test
    @DisplayName("findAll return a list with all users")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var users = repository.findAll();
        assertThat(users).isNotNull().hasSize(users.size());
    }
    @Test
    @DisplayName("findAll return a user with given id ")
    @Order(2)
    void findById_ReturnsUsersById_WhenSuccessful() {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var userListFirst = usersList.getFirst();
        var user = repository.findById(userListFirst.getId());
        org.assertj.core.api.Assertions.assertThat(user).isPresent().contains(userListFirst);
    }

    @Test
    @DisplayName("findAll returns empty list when name is null ")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var users = repository.findByName(null);
        org.assertj.core.api.Assertions.assertThat(users).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findAll returns list when found object when name exists ")
    @Order(4)
    void findByName_ReturnsFoundUserInList_WhenNameIsFound() {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var userListFirst = usersList.getFirst();
        var user = repository.findByName(userListFirst.getFirstName());
        org.assertj.core.api.Assertions.assertThat(user).contains(userListFirst);
    }

    @Test
    @DisplayName("save creates a user")
    @Order(5)
    void save_CreatesUser_WhenSuccessful() {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var userToSave = User.builder()
                .id(99L)
                .firstName("Davi")
                .lastName("dantas")
                .email("davi@gmail.com")
                .build();
        var user = repository.createUser(userToSave);
        org.assertj.core.api.Assertions.assertThat(user).isEqualTo(userToSave).hasNoNullFieldsOrProperties();
        Optional<User> userOptional = repository.findById(userToSave.getId());
        org.assertj.core.api.Assertions.assertThat(userOptional).isPresent().contains(userToSave);
    }
    @Test
    @DisplayName("delete removes a user ")
    @Order(6)
    void delete_RemoveUser_WhenSuccessful() {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var userListFirst = usersList.getFirst();
        repository.delete(userListFirst);
        org.assertj.core.api.Assertions.assertThat(this.usersList).doesNotContain(userListFirst);
    }

    @Test
    @DisplayName("update updates a user")
    @Order(5)
    void update_UpdatesUser_WhenSuccessful() {
        BDDMockito.when(userData.getListUsers()).thenReturn(usersList);
        var userListFirst = this.usersList.getFirst();
        userListFirst.setFirstName("Aniplex");
        repository.uptade(userListFirst);
        org.assertj.core.api.Assertions.assertThat(this.usersList).contains(userListFirst);
        var userOptional = repository.findById(userListFirst.getId());
        org.assertj.core.api.Assertions.assertThat(userOptional).isPresent();
        org.assertj.core.api.Assertions.assertThat(userOptional.get().getFirstName()).isEqualTo(userListFirst.getFirstName());


    }

}