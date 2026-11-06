package com.taagne.ecomme_bestplace.dto.product;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long productId;
    private String prodName;
    private String prodDescription;
    private Long categoryId;
    private String categoryName;
}
