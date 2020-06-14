package com.example.lab.web;

import com.example.lab.model.ShoppingCart;
import com.example.lab.service.AuthService;
import com.example.lab.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }


    @PostMapping("/{bookId}/add-book")
    public String addNewBookToCart(@PathVariable Long bookId){
        try{
            ShoppingCart shoppingCart = this.shoppingCartService.addNewBookToCart(bookId, this.authService.getCurrentUserId());
        }catch(RuntimeException e){
            e.printStackTrace();
            return "redirect:/books";
        }
        return "redirect:/books";
    }

    @DeleteMapping("/{bookId}/remove-book")
    public String removeBookFromCart(@PathVariable Long bookId){
        ShoppingCart shoppingCart = this.shoppingCartService.removeBookFromCart(bookId, this.authService.getCurrentUserId());
        return "redirect:/books";
    }
}
