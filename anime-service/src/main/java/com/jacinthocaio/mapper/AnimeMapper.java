package com.jacinthocaio.mapper;

import com.jacinthocaio.domain.Anime;
import com.jacinthocaio.request.AnimePostRequest;
import com.jacinthocaio.request.AnimePutRequest;
import com.jacinthocaio.response.AnimeGetResponse;
import com.jacinthocaio.response.AnimePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimeMapper {
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100))")
    Anime toAnime(AnimePostRequest postRequest);

    AnimePostResponse toAnimePostResponse(Anime anime);

    AnimeGetResponse toAnimeGetResponse(Anime anime);

    List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> animes);

    Anime toAnime(AnimePutRequest request);
}
