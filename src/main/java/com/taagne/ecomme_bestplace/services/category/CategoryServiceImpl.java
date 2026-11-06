package com.taagne.ecomme_bestplace.services.category;


import com.taagne.ecomme_bestplace.dto.category.CategoryRequest;
import com.taagne.ecomme_bestplace.dto.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends CategoryService{

    private final CategoryService categoryService;


    @Override
    public List<CategoryResponse> findAll() {
        return List.of();
    }

    @Override
    public CategoryResponse findById(Long id) {
        return null;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        return null;
    }

    @Override
    public CategoryResponse update(Integer id, CategoryRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
