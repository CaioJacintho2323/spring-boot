package com.jacinthocaio.user_service.repository;


import com.jacinthocaio.user_service.dominio.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserData {
    private final List<User> listUsers = new ArrayList<>();

    {
        User caio = User.builder().id(1L).firstName("Caio").lastName("jacintho").email("jacinthocaio@gmail.com").build();
        User joana = User.builder().id(2L).firstName("Joana").lastName("ribeiro").email("joana@gmail.com").build();
        listUsers.addAll(List.of(caio,joana));
    }

    public List<User> getListUsers(){
        return listUsers;
    }
}
