package com.taagne.ecomme_bestplace.dto.product;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotNull(message = "You mast enter product name")
    private String prodName;

    private String prodDescription;

    @NotNull(message = "Product category is not defined")
    private Integer categoryId;

}
