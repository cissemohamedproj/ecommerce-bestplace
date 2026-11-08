package com.taagne.ecomme_bestplace.services.product;


import com.taagne.ecomme_bestplace.dto.product.ProductRequest;
import com.taagne.ecomme_bestplace.dto.product.ProductResponse;
import com.taagne.ecomme_bestplace.entities.Category;
import com.taagne.ecomme_bestplace.entities.Product;
import com.taagne.ecomme_bestplace.mapper.ProductMapper;
import com.taagne.ecomme_bestplace.repositories.CategoryRepository;
import com.taagne.ecomme_bestplace.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        String trimName = request.getProdName().trim();

        Category category = categoryRepository
                .findByIdAndActiveTrue(request.getCategoryId())
                .orElseThrow(
                        ()-> new RuntimeException("Category not found")
                );

        boolean existingProduct = productRepository
                .existsByNameIgnoreCaseAndCategoryAndActiveTrue(trimName, category);

        if(existingProduct){
            throw new IllegalArgumentException("A Product with the same Name already exists in this Category");
        }



        Product createdProduct = productMapper.toEntity(request);

        return productMapper.toResponse(
                productRepository.save(createdProduct)
        );

    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        String trimName = request.getProdName().trim();

       Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(
                        ()-> new RuntimeException("Product not found")
                );

        Category category = categoryRepository
                .findByIdAndActiveTrue(request.getCategoryId())
                .orElseThrow(
                        ()-> new RuntimeException("Category not found")
                );

        boolean existingProduct = productRepository
                .existsByNameIgnoreCaseAndCategoryAndActiveTrueAndIdNot(trimName, category, id);

        if(existingProduct){
            throw new IllegalArgumentException("A Product with the same Name already exists in this Category");
        }


        Product updatedProduct = productMapper.toUpdateEntity(request, product);

        return productMapper.toResponse(
                productRepository.save(updatedProduct)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository
                .findByActiveTrue()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findByIdAndActiveTrue(Long id) {
      Product product =  productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(
                        ()-> new RuntimeException("Product not found")
                );

        return productMapper.toResponse(product);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategory(Long categoryId){
        Category existingCategory = categoryRepository
                .findByIdAndActiveTrue(categoryId)
                .orElseThrow(
                        ()-> new RuntimeException("Category not found")
                );
        return productRepository
                        .findByCategory_IdAndActiveTrue(existingCategory.getId())
                        .stream()
                        .map(productMapper::toResponse)
                        .toList();
    }





    @Override
    public Product delete(Long id) {
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(
                        ()-> new RuntimeException("Product not found")
                );
        product.setActive(false);
        return productRepository.save(product);
    }
}
