package com.example.lab.web;

import com.example.lab.model.ShoppingCart;
import com.example.lab.model.dto.Request;
import com.example.lab.service.AuthService;
import com.example.lab.service.ShoppingCartService;
import com.stripe.exception.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Value("${STRIPE_P_KEY}")
    private String publicKey;

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public PaymentController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @GetMapping("/charge")
    public String getCheckoutPage(Model model){
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.findActiveByUsername(this.authService.getCurrentUserId());
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("currency", "eur");
            model.addAttribute("amount", (int)(shoppingCart.getBooks().stream().mapToDouble(book -> book.getPrice()).sum() * 100));
            model.addAttribute("stripePublicKey", this.publicKey);
            return "checkout";
        }catch(RuntimeException ex){
            return "redirect:/books?error=" + ex.getLocalizedMessage();
        }
    }

    @PostMapping("/charge")
    public String checkout(Request request, Model model){
        try{
            ShoppingCart shoppingCart = this.shoppingCartService.checkoutCart(this.authService.getCurrentUserId(),
                request);
            return "redirect:/books";
        } catch (RuntimeException | CardException | APIException | AuthenticationException | InvalidRequestException | APIConnectionException e) {
            return "redirect:/payments/charge?error=" + e.getLocalizedMessage();
        }
    }

}
