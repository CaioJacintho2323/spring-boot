package com.jacinthocaio.user_service.controller;

import com.jacinthocaio.user_service.dominio.User;
import com.jacinthocaio.user_service.mapper.UserMapper;
import com.jacinthocaio.user_service.request.UserPostRequest;
import com.jacinthocaio.user_service.request.UserPutRequest;
import com.jacinthocaio.user_service.response.UserGetResponse;
import com.jacinthocaio.user_service.response.UserPostResponse;
import com.jacinthocaio.user_service.service.UserService;
import jakarta.validation.Valid;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class Usercontroller {
    private final UserService userService;
    private final UserMapper mapper;


    @GetMapping
    public ResponseEntity<List<UserGetResponse>> findAll(@RequestParam(required = false)String name){
        List<User> all = userService.findAll(name);
        var userGetResponses = mapper.toListGetResponse(all);
        return ResponseEntity.ok(userGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id){
        var byId = userService.findById(id);
        var userGetResponse = mapper.toUserGetResponse(byId);
        return ResponseEntity.ok(userGetResponse);
    }

    @PostMapping
    public ResponseEntity<UserPostResponse> createUser(@RequestBody @Valid UserPostRequest userPostRequest){
        var user = mapper.toUser(userPostRequest);
        var createUser = userService.createUser(user);
        var getResponse = mapper.toUserPostResponse(createUser);
        return ResponseEntity.ok(getResponse);
    }

    @PutMapping
    public ResponseEntity<Void> uptadeUser(@RequestBody @Valid UserPutRequest userPutRequest){
        var request = mapper.toUserPutRequest(userPutRequest);
        userService.update(request);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody User user){
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }

}
