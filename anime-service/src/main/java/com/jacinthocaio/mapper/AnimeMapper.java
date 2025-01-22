package com.jacinthocaio.mapper;

import com.jacinthocaio.domain.Anime;

import com.jacinthocaio.request.AnimePostRequest;
import com.jacinthocaio.response.AnimeGetResponse;
import com.jacinthocaio.response.AnimePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnimeMapper {
    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100))")
    Anime toAnime(AnimePostRequest postRequest);
    AnimePostResponse toAnimePostResponse(Anime anime);

    AnimeGetResponse toAnimeGetResponse(Anime anime);

    List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> animes);
}
