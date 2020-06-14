package com.example.lab.service.impl;

import com.example.lab.model.dto.Request;
import com.example.lab.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${STRIPE_S_KEY}")
    public String secretKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey = this.secretKey;
    }

    @Override
    public Charge pay(Request request) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Map<String, Object> chargeMap = new HashMap<>();
        chargeMap.put("amount", request.getAmount());
        chargeMap.put("currency", request.getCurrency());
        chargeMap.put("description", request.getDescription());
        chargeMap.put("source", request.getStripeToken());
        return Charge.create(chargeMap);
    }
}
