package com.taagne.ecomme_bestplace.mapper;


import com.taagne.ecomme_bestplace.dto.product.ProductRequest;
import com.taagne.ecomme_bestplace.dto.product.ProductResponse;
import com.taagne.ecomme_bestplace.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    public Product toEntity(ProductRequest request){

        return Product.builder()
                .name(request.getProdName().trim())
                .description(request.getProdDescription().trim())
                .category(request.getCategoryId())
                .active(true)
                .build();
    }


    public ProductResponse toResponse(Product product ){

        return ProductResponse.builder()
                .productId(product.getId())
                .prodName(product.getName().trim())
                .prodDescription(product.getDescription().trim())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .createdAt(product.getCreatedAt())
                .isActive(product.isActive())
                .build();
    }


    public Product toUpdateEntity(
            ProductRequest request,
            Product product

    ){
        product.setName(request.getProdName());
        product.setDescription(request.getProdDescription());
        product.setStock(request.getProdStock());
        product.setCategory(request.getCategoryId());
        return product;
    }
}
