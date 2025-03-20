package com.jacinthocaio.user_service.service;

import com.jacinthocaio.user_service.dominio.User;
import com.jacinthocaio.user_service.repository.UserHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserHardCodedRepository userHardCodedRepository;

    public List<User> findAll(String name){
        return name == null ? userHardCodedRepository.findAll() : userHardCodedRepository.findByName(name);
    }

    public User findById(Long id){
        return userHardCodedRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User createUser(User user){
        user.setId(ThreadLocalRandom.current().nextLong(100));
        return userHardCodedRepository.createUser(user);
    }

    public void deleteUser(User user){
        userHardCodedRepository.delete(user);
    }
    public void update(User user){
        userHardCodedRepository.uptade(user);
    }
}
