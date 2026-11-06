package com.taagne.ecomme_bestplace.services.category;

import com.taagne.ecomme_bestplace.dto.category.CategoryRequest;
import com.taagne.ecomme_bestplace.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> findAll();

    CategoryResponse findById(Long id);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Integer id, CategoryRequest request);

    void delete(Long id);
}
