package com.taagne.ecomme_bestplace.repositories;


import com.taagne.ecomme_bestplace.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean findByNameIgnoreCase(String name);


    boolean existsByNameIgnoreCaseAndActiveTrue(String name);

    boolean existsByNameIgnoreCaseAndActiveTrueAndIdNot(String name, Long id);



    List<Category> findAllByActiveTrue();

    Optional<Category> findByIdAndActiveTrue(Long id);
}
