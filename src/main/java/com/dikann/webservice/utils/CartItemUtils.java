package com.dikann.webservice.utils;

import com.dikann.webservice.entity.CartItem;

import java.util.List;

public class CartItemUtils {
    public static Double totalDiscount(List<CartItem> cartItems) {
        Double result = 0D;
        for (CartItem cartItem : cartItems)
            result += (Double) (cartItem.getProduct().getPrice() * (cartItem.getProduct().getDiscount().getDiscountPercentage() / 100.0D));

        return result;
    }

    public static Double totalPrice(List<CartItem> cartItems) {
        Double result = 0D;
        Double totalDiscount = totalDiscount(cartItems);
        for (CartItem cartItem : cartItems)
            result += (cartItem.getProduct().getPrice());

        result = result - totalDiscount;
        return result;
    }
}
