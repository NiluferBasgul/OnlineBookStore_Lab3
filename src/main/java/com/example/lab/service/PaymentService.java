package com.example.lab.service;

import com.example.lab.model.dto.Request;
import com.stripe.exception.*;
import com.stripe.model.Charge;

public interface PaymentService {
    Charge pay(Request request) throws CardException, AuthenticationException, APIException, APIConnectionException, InvalidRequestException;
}
