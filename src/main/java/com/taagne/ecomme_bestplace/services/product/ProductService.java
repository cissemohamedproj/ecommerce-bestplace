package com.taagne.ecomme_bestplace.services.product;


import com.taagne.ecomme_bestplace.dto.product.ProductRequest;
import com.taagne.ecomme_bestplace.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product create(ProductRequest request);

    Product update(Long id, ProductRequest request);

    List<Product> findAll();

    Optional<Product> findById(Long id);


    void delete(Long id);
}
