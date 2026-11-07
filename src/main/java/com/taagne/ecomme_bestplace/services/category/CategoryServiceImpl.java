package com.taagne.ecomme_bestplace.services.category;


import com.taagne.ecomme_bestplace.dto.category.CategoryRequest;
import com.taagne.ecomme_bestplace.dto.category.CategoryResponse;
import com.taagne.ecomme_bestplace.entities.Category;
import com.taagne.ecomme_bestplace.mapper.CategoryMaper;
import com.taagne.ecomme_bestplace.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final CategoryMaper categoryMaper;



    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository
                .findAllByActiveTrue()
                .stream()
                .map(categoryMaper::toResponse)
                .toList();
    }



    @Override
    public CategoryResponse findByIdAndActiveTrue(Long id) {

        Category category= categoryRepository
                .findByIdAndActiveTrue(id)
                 .orElseThrow(
                         ()-> new RuntimeException("Category not found")
                 );

        return categoryMaper.toResponse(category);
    }




    @Override
    public CategoryResponse create(CategoryRequest request) {

        String trimmedName = request.getCategoryName().trim();

        boolean existCategory = categoryRepository
                .existsByNameAndActiveTrue(trimmedName);


        if(existCategory){
            throw new RuntimeException("A category with the same name already exists");
        }
        Category createdCategory = categoryMaper.toEntity(request);

        return categoryMaper.toResponse(
                categoryRepository.save(createdCategory)
        );
    }



    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {

        String trimmedName = request.getCategoryName().trim();

        categoryRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(
                        () -> new RuntimeException("Category not found")
                );


        boolean existsCategory = categoryRepository.existsByNameAndActiveTrue(trimmedName);

        if(existsCategory){
            throw new RuntimeException("A category with same name already exists");
        }

        Category createdCategory = categoryMaper.toEntity(request);

        return categoryMaper.toResponse(
                categoryRepository.save(createdCategory)
        );
    }



    @Override
    public Category delete(Long id) {

       Category category = categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(
                        ()-> new RuntimeException("Category not found")
                );
       category.setDeleted(true);

      return  categoryRepository.save(category);

    }
}
