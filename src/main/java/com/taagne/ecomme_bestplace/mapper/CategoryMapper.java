package com.taagne.ecomme_bestplace.mapper;

import com.taagne.ecomme_bestplace.dto.category.CategoryRequest;
import com.taagne.ecomme_bestplace.dto.category.CategoryResponse;
import com.taagne.ecomme_bestplace.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

   public Category toEntity(CategoryRequest request){
       return Category
                .builder()
                .name(request.getCategoryName().trim())
                .description(request.getCategoryDescription().trim())
               .active(true)
               .deleted(false)
                .build();
    }




    public CategoryResponse toResponse(Category category){

       return CategoryResponse
               .builder()
               .Id(category.getId())
               .categoryName(category.getName())
               .categoryDescription(category.getDescription())
               .isActive(category.isActive())
               .isDeleted(category.isDeleted())
               .createdAt(category.getCreatedAt())
               .build();
    }


    public Category toUpdateEntityRequest(CategoryRequest request, Category category){

       category.setName(request.getCategoryName());
       category.setDescription(request.getCategoryDescription());
        return category;
    }
}
