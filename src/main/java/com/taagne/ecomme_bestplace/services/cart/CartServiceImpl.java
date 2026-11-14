package com.taagne.ecomme_bestplace.services.cart;

import com.taagne.ecomme_bestplace.dto.cart.AddToCartRequest;
import com.taagne.ecomme_bestplace.dto.cart.CartResponse;
import com.taagne.ecomme_bestplace.dto.cart.UpdateCartItemRequest;
import com.taagne.ecomme_bestplace.entities.Cart;
import com.taagne.ecomme_bestplace.entities.CartItem;
import com.taagne.ecomme_bestplace.entities.Product;
import com.taagne.ecomme_bestplace.entities.User;
import com.taagne.ecomme_bestplace.mapper.CartMapper;
import com.taagne.ecomme_bestplace.repositories.CartItemRepository;
import com.taagne.ecomme_bestplace.repositories.CartRepository;
import com.taagne.ecomme_bestplace.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    // Récupérer le panier ou le créer automatiquement
    private Cart getOrCreateCart(User user) {

        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {

                    Cart cart = new Cart();
                    cart.setUser(user);

                    return cartRepository.save(cart);
                });
    }

    // Récupérer le panier
    @Override
    @Transactional(readOnly = true)
    public CartResponse getMyCart(User user) {

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Cart not found")
                );

        return cartMapper.mapToResponse(cart);
    }

    // Ajouter un produit au panier
    @Override
    public CartResponse addToCart(
            User user,
            AddToCartRequest request
    ) {

        Cart cart = getOrCreateCart(user);

        Product product = productRepository
                .findByIdAndActiveTrue(request.productId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );

        // Vérifier si le produit existe déjà dans le panier
        CartItem item = cartItemRepository
                .findByCartIdAndProductId(
                        cart.getId(),
                        product.getId()
                )
                .orElse(null);

        if (item != null) {

            // Augmenter la quantité
            item.setQuantity(
                    item.getQuantity() + request.quantity()
            );

        } else {

            // Créer une nouvelle ligne
            item = new CartItem();

            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(request.quantity());

            cart.addItem(item);
        }

        cartRepository.save(cart);

        return cartMapper.mapToResponse(cart);
    }

    // Modifier la quantité
    @Override
    public CartResponse updateQuantity(
            User user,
            Long itemId,
            UpdateCartItemRequest request
    ) {

        Cart cart = getOrCreateCart(user);

        CartItem item = cartItemRepository
                .findByIdAndCartId(itemId, cart.getId())
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found")
                );

        item.setQuantity(request.quantity());

        cartRepository.save(cart);

        return cartMapper.mapToResponse(cart);
    }

    // Supprimer un produit du panier
    @Override
    public void removeItem(User user, Long itemId) {

        Cart cart = getOrCreateCart(user);

        CartItem item = cartItemRepository
                .findByIdAndCartId(itemId, cart.getId())
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found")
                );

        cart.removeItem(item);

        cartRepository.save(cart);
    }

    // Vider le panier
    @Override
    public void clearCart(User user) {

        Cart cart = getOrCreateCart(user);

        cart.getItems().clear();

        cartRepository.save(cart);
    }


}