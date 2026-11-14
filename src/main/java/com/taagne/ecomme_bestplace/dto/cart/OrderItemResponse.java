package com.taagne.ecomme_bestplace.dto.cart;

import java.math.BigDecimal;

public record OrderItemResponse(

        Long id,
        Long productId,
        String productName,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal subtotal

) {}
