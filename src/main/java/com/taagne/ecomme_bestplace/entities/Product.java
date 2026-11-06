package com.taagne.ecomme_bestplace.entities;


import com.taagne.ecomme_bestplace.entities.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product extends BaseEntity {

    @Column(nullable = false, length = 50)
    @NotNull
    private String name;

    @Column( length = 50)
    private String description;

    @Column(nullable = false)
    @NotNull(message = "You mast enter product stock")
    private BigDecimal stock;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;
}
