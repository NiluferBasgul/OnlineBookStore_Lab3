package com.example.lab.web.rest;

import com.example.lab.model.ShoppingCart;
import com.example.lab.service.AuthService;
import com.example.lab.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartRestController {
    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public ShoppingCartRestController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @GetMapping
    public List<ShoppingCart> findAllByUsername(){
        return this.shoppingCartService.findAllByUsername(this.authService.getCurrentUserId());
    }

    @PostMapping
    public ShoppingCart createNewShoppingCart(){
        return this.shoppingCartService.createNewShoppingCart(this.authService.getCurrentUserId());
    }

    @PatchMapping("/{bookId}/books")
    public ShoppingCart addNewBookToCart(@PathVariable Long bookId){
        return this.shoppingCartService.addNewBookToCart(bookId, this.authService.getCurrentUserId());
    }

    @DeleteMapping("/{bookId}/books")
    public ShoppingCart removeBookFromCart(@PathVariable Long bookId){
        return this.shoppingCartService.removeBookFromCart(bookId, this.authService.getCurrentUserId());
    }

    @PatchMapping("/cancel")
    public ShoppingCart cancelActiveCart(){
        return this.shoppingCartService.cancelActiveCart(this.authService.getCurrentUserId());
    }

    @PatchMapping("/checkout")
    public ShoppingCart checkoutCart(){
        return null;
    }
}
