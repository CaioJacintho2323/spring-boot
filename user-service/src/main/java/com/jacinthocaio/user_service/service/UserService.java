package com.jacinthocaio.user_service.service;

import com.jacinthocaio.exception.EmailAlreadyExistsException;
import com.jacinthocaio.exception.NotFoundException;

import com.jacinthocaio.user_service.dominio.User;
import com.jacinthocaio.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.http.HttpStatus.*;

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

//    @Transactional(rollbackFor = Exception.class)
    public User createUser(User user) {
        assertEmailDoesNotExist(user.getEmail());
        return repository.save(user);
    }

    public void deleteUser(User user) {
        repository.delete(user);
    }

    public void update(User user) {
        findById(user.getId());
        assertEmailDoesNotExist(user.getEmail(), user.getId());
        repository.save(user);
    }

    public void assertEmailDoesNotExist(String email) {
        repository.findByEmail(email)
                .ifPresent(this::throwEmailExistsException);

    }
    public void assertEmailDoesNotExist(String email,Long id) {
        repository.findByEmailAndIdNot(email,id)
                .ifPresent(this::throwEmailExistsException);

    }

    private void throwEmailExistsException(User user) {
        throw new EmailAlreadyExistsException("Email %s already exists".formatted(user.getEmail()));
    }
}
