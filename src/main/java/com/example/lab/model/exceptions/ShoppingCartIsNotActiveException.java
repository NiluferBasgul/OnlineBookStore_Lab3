package com.example.lab.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ShoppingCartIsNotActiveException extends RuntimeException {
    public ShoppingCartIsNotActiveException(String userId) {

    }
}
