package com.taagne.ecomme_bestplace.repositories;

import com.taagne.ecomme_bestplace.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository
        extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findAllByUserId(Long userId);

    Optional<CustomerOrder> findByIdAndUserId(
            Long id,
            Long userId
    );
}
