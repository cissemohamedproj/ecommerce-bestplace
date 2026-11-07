package com.taagne.ecomme_bestplace.entities;


import com.taagne.ecomme_bestplace.entities.base.BaseEntity;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "categories")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @Column(nullable = false, length = 50, unique = true)
    @NotBlank(message = "Category name is not define")
    private String name;

    @Column( length = 50)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;


    @PrePersist
    @PreUpdate
    public void formatToLowerCase(){

        if(this.name != null){
            this.name = this.name.trim().toLowerCase();
        }

        if(this.description != null){
            this.description = this.description.trim().toLowerCase();
        }
    }
}
