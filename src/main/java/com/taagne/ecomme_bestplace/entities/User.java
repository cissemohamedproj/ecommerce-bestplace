package com.taagne.ecomme_bestplace.entities;


import com.taagne.ecomme_bestplace.entities.base.BaseEntity;
import com.taagne.ecomme_bestplace.entities.base.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_email",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity implements UserDetails {


    @Column(nullable = false, length = 100)
    @NotBlank(message = "You mast enter your first name")
    private String firstName;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "You mast enter your last name")
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    @NotBlank(message = "You mast enter your email")
    @Email(message = "The entered Email is not correct")
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Role role;

    @Column(nullable = false)
    private boolean active = true;


    // Retourne les rôles et permissions de l'utilisateur
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + role.name()
                )
        );
    }


    // Retourne le mot de passe encodé
    @Override
    public String getPassword() {
        return password;
    }


    // Retourne l'identifiant de connexion
    // Dans notre application, nous utilisons l'email
    @Override
    public String getUsername() {
        return email;
    }


    // Vérifie si le compte n'est pas expiré
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    // Vérifie si le compte n'est pas verrouillé
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    // Vérifie si les identifiants ne sont pas expirés
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    // Vérifie si le compte est actif
    @Override
    public boolean isEnabled() {
        return active;
    }

}

//package com.taagne.ecomme_bestplace.entities;
//
//
//import com.taagne.ecomme_bestplace.entities.base.BaseEntity;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//
//@Setter
//@Getter
//@SuperBuilder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "users")
//public class User extends BaseEntity {
//
//
//    @Column(nullable = false, length = 50)
//    @NotNull(message = "You mast enter User Name")
//    private String fullName;
//
//    @Column(nullable = false, length = 70)
//    @Email(message = "The entered email is not valid")
//    @NotNull(message = "You mast enter a Email value")
//    private String email;
//
//    @Column(nullable = false)
//    @NotNull(message = "Yous mast entered the password")
//    private String password;
//
//
//}
