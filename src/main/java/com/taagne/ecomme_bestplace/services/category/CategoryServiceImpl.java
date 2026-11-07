package com.taagne.ecomme_bestplace.services.category;


import com.taagne.ecomme_bestplace.dto.category.CategoryRequest;
import com.taagne.ecomme_bestplace.dto.category.CategoryResponse;
import com.taagne.ecomme_bestplace.entities.Category;
import com.taagne.ecomme_bestplace.mapper.CategoryMapper;
import com.taagne.ecomme_bestplace.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;



    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository
                .findAllByActiveTrue()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }



    @Override
    public CategoryResponse findByIdAndActiveTrue(Long id) {

        Category category= categoryRepository
                .findByIdAndActiveTrue(id)
                 .orElseThrow(
                         ()-> new RuntimeException("Category not found")
                 );

        return categoryMapper.toResponse(category);
    }




    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {

        String trimmedName = request.getCategoryName().trim();

        boolean existCategory = categoryRepository
                .existsByNameAndActiveTrue(trimmedName);


        if(existCategory){
            throw new RuntimeException("A category with the same name already exists");
        }
        Category createdCategory = categoryMapper.toEntity(request);

        return categoryMapper.toResponse(
                categoryRepository.save(createdCategory)
        );
    }



    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {

        String trimmedName = request.getCategoryName().trim();

       Category category = categoryRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(
                        () -> new RuntimeException("Category not found")
                );


        boolean existsCategory = categoryRepository.existsByNameAndActiveTrueAndIdNot(trimmedName, id);

        if(existsCategory){
            throw new IllegalArgumentException("A category with same name already exists");
        }

        Category updatedCategory = categoryMapper.toUpdateEntityRequest(request, category);

        return categoryMapper.toResponse(
                categoryRepository.save(updatedCategory)
        );
    }



    @Override
    public Category delete(Long id) {

       Category category = categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(
                        ()-> new RuntimeException("Category not found")
                );
       category.setActive(false);
       category.setDeletedAt(LocalDateTime.now());

      return  categoryRepository.save(category);

    }
}
