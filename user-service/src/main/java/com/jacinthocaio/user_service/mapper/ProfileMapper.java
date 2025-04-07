package com.jacinthocaio.user_service.mapper;

import com.jacinthocaio.user_service.dominio.Profile;
import com.jacinthocaio.user_service.request.ProfilePostRequest;
import com.jacinthocaio.user_service.response.ProfileGetResponse;
import com.jacinthocaio.user_service.response.ProfilePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {
    List<ProfileGetResponse> toProfileGetResponse(List<Profile> profile);

    ProfilePostResponse toProfilePostResponse(Profile profile);
    Profile toProfile(ProfilePostRequest profile);
}
