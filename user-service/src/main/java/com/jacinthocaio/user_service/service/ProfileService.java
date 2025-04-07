package com.jacinthocaio.user_service.service;

import com.jacinthocaio.exception.NotFoundException;
import com.jacinthocaio.user_service.dominio.Profile;
import com.jacinthocaio.user_service.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;

    public List<Profile> listAll(){
        return repository.findAll();
    }

    public Profile create(Profile profile){
        return repository.save(profile);
    }
}
