package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;
import static java.util.stream.Collectors.summingDouble;

public class CheckoutService {

    private PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkOut(Cart cart){
        startTimer();
        List<CartItem> cartItems = cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> {
                    boolean isPriceItemInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceItemInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        if(!cartItems.isEmpty()){
            return new CheckoutResponse(CheckoutStatus.FAILURE,cartItems);
        }

        double price = calculateFinalPriceReduce(cart);
        log("checkout price "+price);
        timeTaken();
        return new CheckoutResponse(CheckoutStatus.SUCCESS,price);
    }

    private double calculateFinalPrice(Cart cart) {

        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .mapToDouble(Double::doubleValue)
                .sum(); //sum the price
    }

    private double calculateFinalPriceReduce(Cart cart) {

        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .reduce(0.0,(x,y) -> x+y);
    }
}
