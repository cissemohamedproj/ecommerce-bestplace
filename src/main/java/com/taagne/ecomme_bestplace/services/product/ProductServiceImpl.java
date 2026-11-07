package com.taagne.ecomme_bestplace.services.product;


import com.taagne.ecomme_bestplace.dto.product.ProductRequest;
import com.taagne.ecomme_bestplace.dto.product.ProductResponse;
import com.taagne.ecomme_bestplace.entities.Product;
import com.taagne.ecomme_bestplace.mapper.ProductMapper;
import com.taagne.ecomme_bestplace.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        String trimName = request.getProdName().trim();
        boolean existingProduct = productRepository
                .existsByNameAndActiveTrue(trimName);

        if(existingProduct){
            throw new IllegalArgumentException("A Product with the same name already exists");
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

        boolean existingProduct = productRepository.existsByNameAndActiveTrueAndIdNot(id, trimName);

        if(existingProduct){
            throw new IllegalArgumentException("A Product with the same name already exists");
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
                .findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse findByIdAndActiveTrue(Long id) {
      Product product =  productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(
                        ()-> new RuntimeException("Product not found")
                );

        return productMapper.toResponse(product);
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
