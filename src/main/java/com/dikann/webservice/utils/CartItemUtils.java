package com.dikann.webservice.utils;

import com.dikann.webservice.entity.CartItem;

import java.util.List;

public class CartItemUtils {
    public static double totalDiscount(List<CartItem> cartItems) {
        double result = 0.0d;
        for (CartItem cartItem : cartItems)
            if (cartItem.getProduct().getDiscount() != null)
                if (cartItem.getProduct().getDiscount().isActive())
                    result += (Double) (cartItem.getProduct().getPrice() * (cartItem.getProduct().getDiscount().getDiscountPercentage() / 100.0D));

        return result;
    }

    public static double totalPrice(List<CartItem> cartItems) {
        double result = 0.0d;
        double totalDiscount = totalDiscount(cartItems);
        for (CartItem cartItem : cartItems)
            result += (cartItem.getProduct().getPrice());

        result = result - totalDiscount;
        return result;
    }
}
