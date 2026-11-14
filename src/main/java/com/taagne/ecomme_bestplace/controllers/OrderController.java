package com.taagne.ecomme_bestplace.controllers;

import com.taagne.ecomme_bestplace.dto.cart.OrderResponse;
import com.taagne.ecomme_bestplace.entities.User;
import com.taagne.ecomme_bestplace.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Créer une commande à partir du panier
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(
            @AuthenticationPrincipal User user
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.checkout(user));
    }

    // Récupérer toutes mes commandes
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(
            @AuthenticationPrincipal User user
    ) {

        return ResponseEntity.ok(
                orderService.getMyOrders(user)
        );
    }

    // Récupérer une commande
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId
    ) {

        return ResponseEntity.ok(
                orderService.getOrderById(user, orderId)
        );
    }


    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId
    ) {

        return ResponseEntity.ok(
                orderService.cancelOrder(user, orderId)
        );
    }
}
