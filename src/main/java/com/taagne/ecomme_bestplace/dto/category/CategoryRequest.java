package com.taagne.ecomme_bestplace.dto.category;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank(message = "You mast enter category name")
    @Size(max = 50, message = "Category name mast be 50 max")
    private String categoryName;

    @Size(max = 150, message = "Category description name mast be 150 max")
    private String categoryDescription;

}
