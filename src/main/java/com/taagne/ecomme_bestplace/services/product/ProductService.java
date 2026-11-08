package com.taagne.ecomme_bestplace.services.product;


import com.taagne.ecomme_bestplace.dto.product.ProductRequest;
import com.taagne.ecomme_bestplace.dto.product.ProductResponse;
import com.taagne.ecomme_bestplace.entities.Category;
import com.taagne.ecomme_bestplace.entities.Product;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    List<ProductResponse> findAll();

    ProductResponse findByIdAndActiveTrue(Long id);

    List<ProductResponse> findByCategory(Long categoryId);

    Product delete(Long id);
}
