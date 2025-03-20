package com.jacinthocaio.user_service.repository;

import com.jacinthocaio.user_service.dominio.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserHardCodedRepository {
    private final UserData userData;

    public List<User> findAll(){
        return userData.getListUsers();
    }

    public List<User> findByName(String name){
        return userData.getListUsers()
                .stream()
                .filter(x -> x.getFirstName().equalsIgnoreCase(name))
                .toList();
    }

    public Optional<User> findById(Long id){
        return userData.getListUsers()
                .stream()
                .filter(user -> user.getId() != null && user.getId().equals(id))
                .findFirst();
    }

    public User createUser(User user){
        userData.getListUsers().add(user);
        return user;
    }

    public void delete (User user){
        userData.getListUsers().remove(user);
    }

    public void uptade(User user){
        delete(user);
        createUser(user);
    }


}
