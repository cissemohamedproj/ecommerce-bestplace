package com.taagne.ecomme_bestplace.dto.product;


import com.taagne.ecomme_bestplace.entities.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductRequest {

    @NotBlank(message = "You mast enter product name")
    private String prodName;

    private String prodDescription;

    @NotBlank(message = "Stock is not define")
    private BigDecimal prodStock;

    @NotBlank(message = "Product category is not defined")
    private Category categoryId;

}
