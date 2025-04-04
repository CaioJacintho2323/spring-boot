package com.jacinthocaio.Service;

import com.jacinthocaio.domain.Producer;
import com.jacinthocaio.exception.NotFoundException;
import com.jacinthocaio.repository.ProducerHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ProducerHardCodedRepository repository;

    public List<Producer> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Producer findByIdOrThrowNotFound(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Producer not found"));
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public void delete(Long id) {
        Producer producer = findByIdOrThrowNotFound(id);
        repository.delete(producer);
    }

    public void updated(Producer producerToUpdate) {
        var producer = findByIdOrThrowNotFound(producerToUpdate.getId());
        producerToUpdate.setCreatedAt(producer.getCreatedAt());
        repository.update(producerToUpdate);

    }
}
