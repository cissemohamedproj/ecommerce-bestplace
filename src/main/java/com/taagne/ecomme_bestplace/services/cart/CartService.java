package com.taagne.ecomme_bestplace.services.cart;

import com.taagne.ecomme_bestplace.dto.cart.AddToCartRequest;
import com.taagne.ecomme_bestplace.dto.cart.CartResponse;
import com.taagne.ecomme_bestplace.dto.cart.UpdateCartItemRequest;
import com.taagne.ecomme_bestplace.entities.User;

public interface CartService {

    CartResponse getMyCart(User user);

    CartResponse addToCart(
            User user,
            AddToCartRequest request
    );

    CartResponse updateQuantity(
            User user,
            Long itemId,
            UpdateCartItemRequest request
    );

    void removeItem(User user, Long itemId);

    void clearCart(User user);
}
