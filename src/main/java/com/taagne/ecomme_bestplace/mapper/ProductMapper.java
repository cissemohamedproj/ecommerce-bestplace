package com.taagne.ecomme_bestplace.mapper;


import com.taagne.ecomme_bestplace.dto.product.ProductRequest;
import com.taagne.ecomme_bestplace.dto.product.ProductResponse;
import com.taagne.ecomme_bestplace.entities.Category;
import com.taagne.ecomme_bestplace.entities.Product;
import com.taagne.ecomme_bestplace.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryRepository categoryRepository;

    public Product toEntity(ProductRequest request){

    Category category = categoryRepository
            .findByIdAndActiveTrue(request.getCategoryId())
            .orElseThrow(
                    ()-> new RuntimeException("Category not found")
            );

        return Product.builder()
                .name(request.getProdName().trim())
                .description(request.getProdDescription().trim())
                .category(category)
                .stock(request.getProdStock())
                .price(request.getProdPrice())
                .active(true)
                .build();
    }


    public ProductResponse toResponse(Product product ){

        return ProductResponse.builder()
                .productId(product.getId())
                .prodName(product.getName().trim())
                .prodDescription(product.getDescription().trim())
                .prodStock(product.getStock())
                .prodPrice(product.getPrice())
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

        Category category = categoryRepository
                .findByIdAndActiveTrue(request.getCategoryId())
                .orElseThrow(
                        ()-> new RuntimeException("Category not found")
                );
        product.setName(request.getProdName());
        product.setDescription(request.getProdDescription());
        product.setStock(request.getProdStock());
        product.setPrice(request.getProdPrice());
        product.setCategory(category);
        return product;
    }
}
