package com.taagne.ecomme_bestplace.dto.category;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

    @NotNull(message = "You mast enter category name")
    private String categoryName;

    private String categoryDescription;

}
