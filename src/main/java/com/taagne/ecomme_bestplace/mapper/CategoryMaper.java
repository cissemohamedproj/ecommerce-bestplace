package com.taagne.ecomme_bestplace.mapper;

import com.taagne.ecomme_bestplace.dto.category.CategoryRequest;
import com.taagne.ecomme_bestplace.dto.category.CategoryResponse;
import com.taagne.ecomme_bestplace.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMaper {

   public Category toEntity(CategoryRequest request){
       return Category
                .builder()
                .name(request.getCategoryName().trim())
                .description(request.getCategoryDescription().trim())
               .isActive(true)
               .isDeleted(false)
                .build();
    }


    public CategoryResponse toResponse(Category category){

       return CategoryResponse
               .builder()
               .Id(category.getId())
               .categoryName(category.getName())
               .categoryDescription(category.getDescription())
               .isActive(category.isActive())
               .createdAt(category.getCreatedAt())
               .build();
    }
}
