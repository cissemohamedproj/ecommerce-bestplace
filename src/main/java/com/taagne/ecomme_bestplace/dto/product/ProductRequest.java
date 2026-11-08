package com.taagne.ecomme_bestplace.dto.product;


import com.taagne.ecomme_bestplace.entities.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductRequest {

    @NotBlank(message = "You mast enter product name")
    private String prodName;

    private String prodDescription;

    @NotNull(message = "Stock is not define")
    @Positive(message = "Stock is not valid")
    private BigDecimal prodStock;

    @NotNull(message = "Price is not define")
    @Positive(message = "Price is not valid")
    private BigDecimal prodPrice;

    @NotNull(message = "Product category is not defined")
    private Long categoryId;

}
