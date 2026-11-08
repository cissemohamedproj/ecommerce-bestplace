package com.taagne.ecomme_bestplace.repositories;


import com.taagne.ecomme_bestplace.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



    List<Product> findByActiveTrue();

    Optional<Product> findByIdAndActiveTrue(Long id);

    boolean existsByNameIgnoreCaseAndActiveTrueAndIdNot(Long id, String name);

    boolean existsByNameIgnoreCaseAndCategory_IdAndActiveTrue(String name, Long category);

    boolean existsByNameIgnoreCaseAndCategory_IdAndActiveTrueAndIdNot(String name, Long category, Long id);

    boolean existsByNameAndActiveTrue(String name);

    List<Product> findByCategory_IdAndActiveTrue(Long category);

}
