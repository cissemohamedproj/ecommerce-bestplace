package com.taagne.ecomme_bestplace.entities;


import com.taagne.ecomme_bestplace.entities.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {


    @Column(nullable = false, length = 50)
    @NotNull(message = "You mast enter User Name")
    private String fullName;

    @Column(nullable = false, length = 70)
    @Email(message = "The entered email is not valid")
    @NotNull(message = "You mast enter a Email value")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Yous mast entered the password")
    private String password;


}
