package com.jacinthocaio.user_service.controller;

import com.jacinthocaio.user_service.dominio.Profile;
import com.jacinthocaio.user_service.mapper.ProfileMapper;
import com.jacinthocaio.user_service.request.ProfilePostRequest;
import com.jacinthocaio.user_service.response.ProfileGetResponse;
import com.jacinthocaio.user_service.response.ProfilePostResponse;
import com.jacinthocaio.user_service.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService service;
    private final ProfileMapper mapper;


    @GetMapping
    public ResponseEntity<List<ProfileGetResponse>> listAll(){
        var profiles = service.listAll();
        var getResponseList = mapper.toProfileGetResponse(profiles);
        return ResponseEntity.ok(getResponseList);
    }

    @PostMapping
    public ResponseEntity<ProfilePostResponse> createProfile(@RequestBody @Valid ProfilePostRequest profile){
        var postToProfile = mapper.toProfile(profile);
        var createProfile = service.create(postToProfile);
        var profilePostResponse = mapper.toProfilePostResponse(createProfile);
        return ResponseEntity.ok(profilePostResponse);
    }

}
