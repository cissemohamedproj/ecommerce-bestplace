package com.taagne.ecomme_bestplace.services;


import com.taagne.ecomme_bestplace.dto.auth.AuthResponse;
import com.taagne.ecomme_bestplace.dto.auth.LoginRequest;
import com.taagne.ecomme_bestplace.dto.auth.RegisterRequest;
import com.taagne.ecomme_bestplace.dto.auth.UserResponse;
import com.taagne.ecomme_bestplace.entities.User;
import com.taagne.ecomme_bestplace.entities.base.Role;
import com.taagne.ecomme_bestplace.exceptions.EmailAlreadyExistsException;
import com.taagne.ecomme_bestplace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;


    // =========================================
    // REGISTER
    // =========================================

    @Transactional
    public UserResponse register(RegisterRequest request) {

        // 1. Normaliser l'email
        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);


        // 2. Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(email)) {

            throw new EmailAlreadyExistsException(
                    "This email is already registered"
            );
        }


        // 3. Créer un nouvel utilisateur
        User user = new User();

        user.setFirstName(request.firstName().trim());

        user.setLastName(request.lastName().trim());

        user.setEmail(email);


        // 4. Encoder le mot de passe
        user.setPassword(
                passwordEncoder.encode(request.password())
        );


        // 5. Définir le rôle par défaut
        user.setRole(Role.USER);


        // 6. Activer le compte
        user.setActive(true);


        // 7. Enregistrer l'utilisateur
        User savedUser = userRepository.save(user);


        // 8. Retourner les informations publiques
        return new UserResponse(

                savedUser.getId(),

                savedUser.getFirstName(),

                savedUser.getLastName(),

                savedUser.getEmail(),

                savedUser.getRole()
        );
    }


    // =========================================
    // LOGIN
    // =========================================

    public AuthResponse login(LoginRequest request) {

        // 1. Normaliser l'email
        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);


        // 2. Créer la demande d'authentification
        Authentication authenticationRequest =
                new UsernamePasswordAuthenticationToken(

                        email,

                        request.password()
                );


        // 3. Demander à Spring Security
        // de vérifier les identifiants
        Authentication authentication =
                authenticationManager.authenticate(
                        authenticationRequest
                );


        // 4. Récupérer l'utilisateur authentifié
        User user = (User) authentication.getPrincipal();


        // 5. Générer le JWT
        String token = jwtService.generateToken(user);


        // 6. Retourner la réponse
        return new AuthResponse(

                token,

                "Bearer",

                jwtExpiration,

                user.getEmail(),

                user.getRole().name()
        );
    }

}
