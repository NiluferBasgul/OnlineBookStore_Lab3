package com.example.lab.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class TransactionExceptionFailed extends RuntimeException {
    public TransactionExceptionFailed(String userId, String message) {
        super(String.format("The transaction with %s user was failed! Message $s: ", userId, message));
    }
}
