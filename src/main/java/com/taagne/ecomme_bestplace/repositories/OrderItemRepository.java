package com.taagne.ecomme_bestplace.repositories;

import com.taagne.ecomme_bestplace.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {
}
