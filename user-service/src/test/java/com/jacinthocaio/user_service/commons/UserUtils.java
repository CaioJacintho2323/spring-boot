package com.jacinthocaio.user_service.commons;


import com.jacinthocaio.user_service.dominio.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {
    public List<User> newUserList(){
        var cesar = User.builder().id(1L).firstName("Cesar").lastName("jacintho").email("cesar@gmail.com").build();
        var lucca = User.builder().id(2L).firstName("Lucca").lastName("jacintho").email("lucca@gmail.com").build();
        return new ArrayList<>(List.of(cesar,lucca));
    }

    public User newUserToSave(){
        return User.builder().id(99L).firstName("Rivanda").lastName("Mota").email("rivanda@gmail.com").build();
    }
}
