package com.taagne.ecomme_bestplace.repositories;


import com.taagne.ecomme_bestplace.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



    List<Product> findAllAndActiveTrue();

    Optional<Product> findByIdAndActiveTrue(Long id);

    boolean existsByNameAndActiveTrueAndIdNot(Long id, String name);

    boolean existsByNameAndActiveTrue(String name);

}
