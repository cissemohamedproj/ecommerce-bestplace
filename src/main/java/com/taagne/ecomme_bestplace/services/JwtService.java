package com.taagne.ecomme_bestplace.services;


import com.taagne.ecomme_bestplace.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;

@Service
public class JwtService {

    private final SecretKey signingKey;

    private final long jwtExpiration;


    public JwtService(

            @Value("${app.jwt.secret}")
            String secret,

            @Value("${app.jwt.expiration}")
            long jwtExpiration

    ) {

        this.signingKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        this.jwtExpiration = jwtExpiration;
    }


    // Générer un JWT
    public String generateToken(User user) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + jwtExpiration
        );

        return Jwts.builder()

                // Identité de l'utilisateur
                .subject(user.getEmail())

                // Date de création
                .issuedAt(now)

                // Date d'expiration
                .expiration(expiration)

                // Signature du token
                .signWith(signingKey)

                // Génération du JWT
                .compact();
    }


    // Extraire l'email du JWT
    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }


    // Vérifier si le token est valide pour cet utilisateur
    public boolean isTokenValid(
            String token,
            User user
    ) {

        String email = extractUsername(token);

        return email.equals(user.getEmail())
                && extractAllClaims(token)
                .getExpiration()
                .after(new Date());
    }


    // Extraire toutes les informations du JWT
    private Claims extractAllClaims(String token) {

        return Jwts.parser()

                // Clé utilisée pour vérifier la signature
                .verifyWith(signingKey)

                .build()

                // Analyser le token signé
                .parseSignedClaims(token)

                // Récupérer les informations
                .getPayload();
    }

}
