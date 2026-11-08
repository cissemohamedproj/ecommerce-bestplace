package com.taagne.ecomme_bestplace.dto.product;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponse {

    private Long productId;
    private String prodName;
    private String prodDescription;
    private BigDecimal prodStock;
    private BigDecimal prodPrice;
    private Long categoryId;
    private String categoryName;
    private boolean isActive;
    private LocalDateTime createdAt;
}
