package com.taagne.ecomme_bestplace.dto.product;


import com.taagne.ecomme_bestplace.entities.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "You mast enter product name")
    private String prodName;

    private String prodDescription;

    @NotBlank(message = "Product category is not defined")
    private Category categoryId;

}
