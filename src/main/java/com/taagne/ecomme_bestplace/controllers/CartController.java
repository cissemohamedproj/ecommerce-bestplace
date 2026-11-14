package com.taagne.ecomme_bestplace.controllers;

import com.taagne.ecomme_bestplace.dto.cart.AddToCartRequest;
import com.taagne.ecomme_bestplace.dto.cart.CartResponse;
import com.taagne.ecomme_bestplace.dto.cart.UpdateCartItemRequest;
import com.taagne.ecomme_bestplace.entities.User;
import com.taagne.ecomme_bestplace.services.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Récupérer mon panier
    @GetMapping
    public ResponseEntity<CartResponse> getMyCart(
            @AuthenticationPrincipal User user
    ) {

        return ResponseEntity.ok(
                cartService.getMyCart(user)
        );
    }

    // Ajouter un produit
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody AddToCartRequest request
    ) {

        return ResponseEntity.ok(
                cartService.addToCart(user, request)
        );
    }

    // Modifier la quantité
    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateQuantity(
            @AuthenticationPrincipal User user,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {

        return ResponseEntity.ok(
                cartService.updateQuantity(
                        user,
                        itemId,
                        request
                )
        );
    }

    // Supprimer une ligne
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long itemId
    ) {

        cartService.removeItem(user, itemId);

        return ResponseEntity.noContent().build();
    }

    // Vider le panier
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(
            @AuthenticationPrincipal User user
    ) {

        cartService.clearCart(user);

        return ResponseEntity.noContent().build();
    }
}
