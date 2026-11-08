package com.taagne.ecomme_bestplace.entities;


import com.taagne.ecomme_bestplace.entities.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Product extends BaseEntity {

    @Column(nullable = false, length = 50)
    @NotBlank(message = "You mast enter product name")
    private String name;

    @Column( length = 50)
    private String description;

    @Column(nullable = false)
    @NotNull(message = "You mast enter a valid product stock")
    @Positive(message = "You mast enter product stock")
    private BigDecimal stock;

    @Column(nullable = false)
    @NotNull(message = "You mast enter product price")
    @Positive(message = "You mast enter a valid product price")
    private BigDecimal price;

    @NotNull(message = "category is not define")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;


    @PrePersist
    @PreUpdate
    public void formatToString(){
        if(this.name != null){
            this.name = this.name.trim().toLowerCase();
        }

        if(this.description != null){
            this.description = this.description.trim().toLowerCase();
        }
    }
}
