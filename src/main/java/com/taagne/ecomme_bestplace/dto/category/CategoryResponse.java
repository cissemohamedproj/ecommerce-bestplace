package com.taagne.ecomme_bestplace.dto.category;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class CategoryResponse {

    private Long Id;
    private String categoryName;
    private String categoryDescription;
    private boolean isActive;
    private LocalDateTime createdAt;

}
