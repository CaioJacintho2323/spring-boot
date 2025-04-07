package com.jacinthocaio.user_service.repository;

import com.jacinthocaio.user_service.commons.UserUtils;
import com.jacinthocaio.user_service.dominio.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(UserUtils.class)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserUtils userUtils;

    @Test
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    @DisplayName("save creates a users")
    @Order(1)
    void save_CreatesUser_WhenSuccessful() {
        var userToSave = User.builder()
                .id(99L)
                .firstName("enzo")
                .lastName("levy")
                .email("enzolevy@gmail.com")
                .build();
        var savedUser = repository.save(userToSave);

        Assertions.assertThat(savedUser).hasNoNullFieldsOrProperties();
        Assertions.assertThat(savedUser.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("findAll return a list with all users")
    @Order(2)
    @Sql("/sql/init_one_user.sql")
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        var users = repository.findAll();
        Assertions.assertThat(users).isNotEmpty();
    }

}