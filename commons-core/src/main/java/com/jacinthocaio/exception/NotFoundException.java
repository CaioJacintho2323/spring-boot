package com.jacinthocaio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    public NotFoundException(String mensage) {
        super(HttpStatus.NOT_FOUND, mensage);
    }
}
