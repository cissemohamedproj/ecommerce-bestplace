package com.taagne.ecomme_bestplace.services.order;

import com.taagne.ecomme_bestplace.dto.cart.OrderItemResponse;
import com.taagne.ecomme_bestplace.dto.cart.OrderResponse;
import com.taagne.ecomme_bestplace.entities.*;
import com.taagne.ecomme_bestplace.mapper.OrderMapper;
import com.taagne.ecomme_bestplace.repositories.CartItemRepository;
import com.taagne.ecomme_bestplace.repositories.CartRepository;
import com.taagne.ecomme_bestplace.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    // Créer une commande à partir du panier
    @Override
    public OrderResponse checkout(User user) {

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Cart not found")
                );

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Your cart is empty");
        }

        // Créer la commande
        CustomerOrder order = new CustomerOrder();

        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO;

        // Copier les produits du panier dans la commande
        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            if (!product.isActive()) {
                throw new RuntimeException(
                        "Product is no longer available: "
                                + product.getName()
                );
            }

            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(product);

            // Sauvegarder les informations historiques
            orderItem.setProductName(product.getName());

            orderItem.setUnitPrice(product.getPrice());

            orderItem.setQuantity(cartItem.getQuantity());

            BigDecimal subtotal = product.getPrice()
                    .multiply(
                            BigDecimal.valueOf(cartItem.getQuantity())
                    );

            orderItem.setSubtotal(subtotal);

            order.addItem(orderItem);

            totalAmount = totalAmount.add(subtotal);
        }

        order.setTotalAmount(totalAmount);

        // Enregistrer la commande
        CustomerOrder savedOrder = orderRepository.save(order);

        // Vider le panier après création de la commande
        cart.getItems().clear();

        cartRepository.save(cart);

        return orderMapper.mapToResponse(savedOrder);
    }

    // Récupérer une commande par son ID
    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(
            User user,
            Long orderId
    ) {

        CustomerOrder order = orderRepository
                .findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found")
                );

        return orderMapper.mapToResponse(order);
    }

    // Récupérer toutes les commandes de l'utilisateur
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders(User user) {

        return orderRepository.findAllByUserId(user.getId())
                .stream()
                .map(orderMapper::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(User user, Long orderId) {

        // 1. Récupérer la commande de l'utilisateur
        CustomerOrder order = orderRepository
                .findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found")
                );

        // 2. Vérifier le statut de la commande
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException(
                    "This order has already been cancelled"
            );
        }

        if (
                order.getStatus() == OrderStatus.SHIPPED ||
                        order.getStatus() == OrderStatus.DELIVERED
        ) {
            throw new RuntimeException(
                    "This order cannot be cancelled"
            );
        }

        // 3. Récupérer le panier de l'utilisateur
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // 4. Parcourir les produits de la commande
        for (OrderItem orderItem : order.getItems()) {

            Product product = orderItem.getProduct();

            // Ne pas remettre un produit désactivé dans le panier
            if (!product.isActive()) {
                continue;
            }

            // Vérifier si le produit existe déjà dans le panier
            CartItem cartItem = cartItemRepository
                    .findByCartIdAndProductId(
                            cart.getId(),
                            product.getId()
                    )
                    .orElse(null);

            if (cartItem != null) {

                // Ajouter la quantité à celle déjà présente
                cartItem.setQuantity(
                        cartItem.getQuantity() + orderItem.getQuantity()
                );

            } else {

                // Créer une nouvelle ligne dans le panier
                CartItem newItem = new CartItem();

                newItem.setCart(cart);
                newItem.setProduct(product);
                newItem.setQuantity(orderItem.getQuantity());

                cart.addItem(newItem);
            }
        }

        // 5. Annuler la commande
        order.setStatus(OrderStatus.CANCELLED);

        // 6. Enregistrer les modifications
        cartRepository.save(cart);

        CustomerOrder savedOrder = orderRepository.save(order);

        // 7. Retourner la commande
        return orderMapper.mapToResponse(savedOrder);
    }

}
