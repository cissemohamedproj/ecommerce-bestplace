package com.taagne.ecomme_bestplace.controllers;


import com.taagne.ecomme_bestplace.dto.auth.AuthResponse;
import com.taagne.ecomme_bestplace.dto.auth.LoginRequest;
import com.taagne.ecomme_bestplace.dto.auth.RegisterRequest;
import com.taagne.ecomme_bestplace.dto.auth.UserResponse;

import com.taagne.ecomme_bestplace.services.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    // =========================================
    // REGISTER
    // =========================================

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(

            @Valid @RequestBody RegisterRequest request

    ) {

        UserResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =========================================
    // LOGIN
    // =========================================

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(

            @Valid @RequestBody LoginRequest request

    ) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

}
