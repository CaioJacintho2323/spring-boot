package com.jacinthocaio.user_service.commons;


import com.jacinthocaio.user_service.dominio.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileUtils {
    public List<Profile> newProfileList(){
        var admin = Profile.builder().id(1L).name("Administrador").description("Admins everythings").build();
        var manager = Profile.builder().id(2L).name("Manager").description("Manages users").build();
        return new ArrayList<>(List.of(admin,manager));
    }

    public Profile newProfileToSave(){
        return Profile.builder().name("Regular User").description("Regular user with regular permissions").build();
    }
    public Profile newProfileToSaved(){
        return Profile.builder().id(99L).name("Regular User").description("Regular user with regular permissions").build();
    }
}
