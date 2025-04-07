package com.jacinthocaio.user_service.mapper;

import com.jacinthocaio.user_service.dominio.User;
import com.jacinthocaio.user_service.request.UserPostRequest;
import com.jacinthocaio.user_service.request.UserPutRequest;
import com.jacinthocaio.user_service.response.UserGetResponse;
import com.jacinthocaio.user_service.response.UserPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toUser(UserPostRequest postRequest);

    UserPostResponse toUserPostResponse(User user);

    List<UserGetResponse> toListGetResponse(List<User> user);

    UserGetResponse toUserGetResponse(User user);

    User toUserPutRequest(UserPutRequest userPutRequest);
}
