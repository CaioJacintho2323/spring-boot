package com.jacinthocaio.user_service.service;

import com.jacinthocaio.exception.NotFoundException;
import com.jacinthocaio.user_service.dominio.User;
import com.jacinthocaio.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;

    public List<User> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByFirstNameIgnoreCase(name);
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User createUser(User user) {
        user.setId(ThreadLocalRandom.current().nextLong(100));
        return repository.save(user);
    }

    public void deleteUser(User user) {
        repository.delete(user);
    }

    public void update(User user) {
        findById(user.getId());
        repository.save(user);
    }
}
