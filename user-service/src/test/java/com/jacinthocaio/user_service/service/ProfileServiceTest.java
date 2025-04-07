package com.jacinthocaio.user_service.service;

import com.jacinthocaio.exception.EmailAlreadyExistsException;
import com.jacinthocaio.user_service.commons.ProfileUtils;
import com.jacinthocaio.user_service.dominio.Profile;
import com.jacinthocaio.user_service.repository.ProfileRepository;
import com.jacinthocaio.user_service.service.ProfileService;
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
class ProfileServiceTest {
    @InjectMocks
    private ProfileService service;
    @Mock
    private ProfileRepository repository;

    private List<Profile> profilesList;

    @InjectMocks
    private ProfileUtils profileUtils;

    @BeforeEach
    void init(){
        profilesList = profileUtils.newProfileList();
    }

    @Test
    @DisplayName("findAll return a list with all profiles")
    @Order(1)
    void findAll_ReturnsAllProfiles_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(profilesList);
        var profiles = service.listAll();
        org.assertj.core.api.Assertions.assertThat(profiles).isNotNull().hasSameElementsAs(profilesList);
    }


    @Test
    @DisplayName("save creates a profiles")
    @Order(6)
    void save_CreatesProfile_WhenSuccessful() {
        var profileToSave = profileUtils.newProfileToSave();

        var profileSaved = profileUtils.newProfileToSaved();

        BDDMockito.when(repository.save(profileToSave)).thenReturn(profileSaved);

        var savedProfile = service.create(profileToSave);

        org.assertj.core.api.Assertions.assertThat(savedProfile).isEqualTo(profileSaved).hasNoNullFieldsOrProperties();
    }

}