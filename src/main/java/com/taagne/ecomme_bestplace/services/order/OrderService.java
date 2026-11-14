package com.taagne.ecomme_bestplace.services.order;

import com.taagne.ecomme_bestplace.dto.cart.OrderResponse;
import com.taagne.ecomme_bestplace.entities.User;

import java.util.List;

public interface OrderService {

    OrderResponse checkout(User user);

    OrderResponse getOrderById(User user, Long orderId);

    List<OrderResponse> getMyOrders(User user);

    OrderResponse cancelOrder(User user, Long orderId);
}