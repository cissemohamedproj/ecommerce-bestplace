package com.taagne.ecomme_bestplace.controllers;


import com.taagne.ecomme_bestplace.dto.product.ProductRequest;
import com.taagne.ecomme_bestplace.dto.product.ProductResponse;
import com.taagne.ecomme_bestplace.services.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid
            @RequestBody
            ProductRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.create(request));
    }



    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            ProductRequest request
    ){
        return ResponseEntity
                .ok(productService.update(id,request));
    }



    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllAndActiveTrue(){
        return ResponseEntity
                .ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable
            Long id
    ){
        return ResponseEntity
                .ok(productService.findByIdAndActiveTrue(id));
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductResponse>> findByCategoryId(
            @PathVariable
            Long id
    ){
        return ResponseEntity
                .ok(productService.findByCategory(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            Long id
    ){

        productService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}

