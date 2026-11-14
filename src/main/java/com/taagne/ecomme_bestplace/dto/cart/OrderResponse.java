package com.taagne.ecomme_bestplace.dto.cart;

import com.taagne.ecomme_bestplace.entities.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(

        Long id,
        OrderStatus status,
        List<OrderItemResponse> items,
        BigDecimal totalAmount

) {}