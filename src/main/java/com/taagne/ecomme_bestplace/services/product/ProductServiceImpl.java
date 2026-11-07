package com.taagne.ecomme_bestplace.services.product;


import com.taagne.ecomme_bestplace.dto.product.ProductRequest;
import com.taagne.ecomme_bestplace.entities.Product;
import com.taagne.ecomme_bestplace.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductService productService;
    private final ProductRepository productRepository;

    @Override
    public Product create(ProductRequest request) {
        return null;
    }

    @Override
    public Product update(Long id, ProductRequest request) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
