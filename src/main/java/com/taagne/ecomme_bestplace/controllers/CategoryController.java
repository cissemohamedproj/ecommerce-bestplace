package com.taagne.ecomme_bestplace.controllers;


import com.taagne.ecomme_bestplace.dto.category.CategoryRequest;
import com.taagne.ecomme_bestplace.dto.category.CategoryResponse;
import com.taagne.ecomme_bestplace.services.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> create(
            @Valid
            @RequestBody
            CategoryRequest request
    ){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.create(request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            CategoryRequest request
    ){
      return  ResponseEntity.ok(categoryService.update(
                id,
                request
        ));
    }


    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll(){

        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(
            @PathVariable
            Long id
    ){
        return ResponseEntity.ok(
                categoryService.findByIdAndActiveTrue(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable
            Long id
    ){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
