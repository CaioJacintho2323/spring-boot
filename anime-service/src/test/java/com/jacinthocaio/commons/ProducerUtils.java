package com.jacinthocaio.commons;

import com.jacinthocaio.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Component
public class ProducerUtils {
    public List<Producer> newProducerList(){
        var dateTime = "2025-02-21T14:52:53.941024";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, formatter);

        var ufotable = Producer.builder().id(1L).name("ufotable").createdAt(localDateTime).build();
        var witStudio = Producer.builder().id(2L).name("witStudio").createdAt(localDateTime).build();
        var studioGhibli = Producer.builder().id(3L).name("studioGhibli").createdAt(localDateTime).build();
        return new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }
}
