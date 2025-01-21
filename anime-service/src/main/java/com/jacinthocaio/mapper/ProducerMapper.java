package com.jacinthocaio.mapper;

import com.jacinthocaio.domain.Producer;
import com.jacinthocaio.request.ProducerPostRequest;
import com.jacinthocaio.response.ProducerGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProducerMapper {
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100))")
    Producer toProducer(ProducerPostRequest postRequest);

    ProducerGetResponse toProducerGetResponse(Producer producer);
}
