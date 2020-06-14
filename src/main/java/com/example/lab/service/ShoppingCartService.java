package com.example.lab.service;

import com.example.lab.model.ShoppingCart;
import com.example.lab.model.dto.Request;
import com.stripe.exception.*;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart createNewShoppingCart(String userId);
    List<ShoppingCart> findAllByUsername(String userId);
    ShoppingCart addNewBookToCart(Long bookId, String userId);
    ShoppingCart removeBookFromCart(Long bookId, String userId);
    ShoppingCart getActiveCart(String userId);
    ShoppingCart checkoutCart(String userId, Request request) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException;
    ShoppingCart findActiveByUsername(String userId);
    ShoppingCart cancelActiveCart(String userId);
}
