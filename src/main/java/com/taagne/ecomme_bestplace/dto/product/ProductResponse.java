package com.taagne.ecomme_bestplace.dto.product;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponse {

    private Long productId;
    private String prodName;
    private String prodDescription;
    private Long categoryId;
    private String categoryName;
    private boolean isActive;
    private boolean isDeleted;
    private LocalDateTime createdAt;
}
