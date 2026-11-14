package com.taagne.ecomme_bestplace.mapper;

import com.taagne.ecomme_bestplace.dto.cart.OrderItemResponse;
import com.taagne.ecomme_bestplace.dto.cart.OrderResponse;
import com.taagne.ecomme_bestplace.entities.CustomerOrder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class OrderMapper {

    // Conversion Entity vers DTO
    public OrderResponse mapToResponse(CustomerOrder order) {

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProductName(),
                        item.getUnitPrice(),
                        item.getQuantity(),
                        item.getSubtotal()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                items,
                order.getTotalAmount()
        );
    }

}
