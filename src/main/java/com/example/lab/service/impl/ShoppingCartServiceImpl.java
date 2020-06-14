package com.example.lab.service.impl;

import com.example.lab.model.Book;
import com.example.lab.model.ShoppingCart;
import com.example.lab.model.User;
import com.example.lab.model.dto.Request;
import com.example.lab.model.enumerations.CartStatus;
import com.example.lab.model.exceptions.BookIsOutOfStock;
import com.example.lab.model.exceptions.ShoppingCartIsAlreadyCreated;
import com.example.lab.model.exceptions.ShoppingCartIsNotActiveException;
import com.example.lab.model.exceptions.TransactionExceptionFailed;
import com.example.lab.repository.ShoppingCartRepository;
import com.example.lab.service.BookService;
import com.example.lab.service.PaymentService;
import com.example.lab.service.ShoppingCartService;
import com.example.lab.service.UserService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookService bookService;
    private final PaymentService paymentService;

    public ShoppingCartServiceImpl(UserService userService, ShoppingCartRepository shoppingCartRepository, BookService bookService, PaymentService paymentService) {
        this.userService = userService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookService = bookService;
        this.paymentService = paymentService;
    }


    @Override
    public ShoppingCart createNewShoppingCart(String userId) {
        User user = this.userService.findById(userId);
        if(this.shoppingCartRepository.existsByUserUsernameAndStatus(user.getUsername(), CartStatus.CREATED)){
            throw new ShoppingCartIsAlreadyCreated(userId);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
           shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ShoppingCart> findAllByUsername(String userId) {
        return (List<ShoppingCart>) this.shoppingCartRepository.findAllByUserUsername(userId);
    }

    @Override
    @Transactional
    public ShoppingCart addNewBookToCart(Long bookId, String userId) {
        ShoppingCart shoppingCart = this.getActiveCart(userId);
        Book book = this.bookService.findById(bookId);
        shoppingCart.getBooks().add(book);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart removeBookFromCart(Long bookId, String userId) {
//        ShoppingCart shoppingCart = this.getActiveCart(userId);
//        Book book = this.bookService.findById(bookId);
//        shoppingCart.getBooks().remove(book);
//       return this.shoppingCartRepository.save(shoppingCart);
        ShoppingCart shoppingCart = this.getActiveCart(userId);
        shoppingCart.setBooks(shoppingCart.getBooks()
        .stream()
        .filter(book -> !book.getId().equals(bookId))
        .collect(Collectors.toList()));
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveCart(String userId) {
        return this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseGet(()-> {
                    User user = this.userService.findById(userId);
                    ShoppingCart shoppingCart = new ShoppingCart();
//                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart cancelActiveCart(String userId) {
//        ShoppingCart shoppingCart = this.getActiveCart(userId);
//        shoppingCart.setCartStatus(CartStatus.CANCELED);
//        return this.shoppingCartRepository.save(shoppingCart);
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CANCELED).orElseThrow(()-> new ShoppingCartIsNotActiveException(userId));
        shoppingCart.setStatus(CartStatus.CANCELED);
        shoppingCart.setCanceledDate(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    // dali edno ili drugo
    public ShoppingCart checkoutCart(String userId, Request request) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));

        List<Book> books = shoppingCart.getBooks();
        float price=0;

        for(Book book : books){
            if(book.getQuantity() <= 0){
                throw new BookIsOutOfStock(book.getName());
            }
            book.setQuantity(book.getQuantity() - 1);
            price += book.getPrice();

            Charge charge = null;
            try{
                charge = this.paymentService.pay(request);
            }catch(CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException e){
                throw new TransactionExceptionFailed(userId, e.getLocalizedMessage());
            }

            shoppingCart.setBooks(books);
            shoppingCart.setStatus(CartStatus.FINISHED);
            shoppingCart.setCanceledDate(LocalDateTime.now());
        }
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart findActiveByUsername(String userId) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));
    }
}
