package com.taagne.ecomme_bestplace.dto.category;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long Id;
    private String categoryName;
    private String categoryDescription;

}
