package com.taagne.ecomme_bestplace.dto.auth;

public record AuthResponse(

        String accessToken,

        String tokenType,

        long expiresIn,

        String email,

        String role

) {}