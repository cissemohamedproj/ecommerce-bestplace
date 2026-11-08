package com.taagne.ecomme_bestplace.repositories;


import com.taagne.ecomme_bestplace.entities.Category;
import com.taagne.ecomme_bestplace.entities.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



    List<Product> findByActiveTrue();

    Optional<Product> findByIdAndActiveTrue(Long id);

    boolean existsByNameIgnoreCaseAndActiveTrueAndIdNot(Long id, String name);

    boolean existsByNameIgnoreCaseAndCategoryAndActiveTrue(String name, Category category);

    boolean existsByNameIgnoreCaseAndCategoryAndActiveTrueAndIdNot( String name, Category category, Long id);

    boolean existsByNameAndActiveTrue(String name);

    List<Product> findByCategory(Category category);

}
