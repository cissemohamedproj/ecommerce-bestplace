package com.taagne.ecomme_bestplace.filters;


import com.taagne.ecomme_bestplace.entities.User;
import com.taagne.ecomme_bestplace.services.CustomUserDetailsService;
import com.taagne.ecomme_bestplace.services.JwtService;
import io.jsonwebtoken.JwtException;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {


        // 1. Récupérer le header Authorization
        String authHeader =
                request.getHeader("Authorization");


        // 2. Vérifier si le token existe
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }


        // 3. Extraire le JWT
        String token = authHeader.substring(7);


        try {

            // 4. Extraire l'email du JWT
            String email = jwtService.extractUsername(token);


            // 5. Vérifier si l'utilisateur n'est pas
            // déjà authentifié dans cette requête
            if (email != null &&
                    SecurityContextHolder.getContext()
                            .getAuthentication() == null) {


                // 6. Charger l'utilisateur
                User user = (User) userDetailsService
                        .loadUserByUsername(email);


                // 7. Vérifier le JWT
                if (user.isEnabled() &&
                        jwtService.isTokenValid(token, user)) {


                    // 8. Créer un objet Authentication
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(

                                    user,

                                    null,

                                    user.getAuthorities()
                            );


                    // 9. Ajouter les détails de la requête
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );


                    // 10. Créer le SecurityContext
                    SecurityContext context =
                            SecurityContextHolder.createEmptyContext();


                    // 11. Enregistrer l'Authentication
                    context.setAuthentication(authentication);


                    // 12. Enregistrer le contexte
                    SecurityContextHolder.setContext(context);

                }

            }

        } catch (
                JwtException |
                IllegalArgumentException |
                UsernameNotFoundException exception
        ) {

            // Token invalide, mal formé ou utilisateur absent
            SecurityContextHolder.clearContext();

        }


        // 13. Continuer la chaîne de filtres
        filterChain.doFilter(request, response);

    }

}