package com.taagne.ecomme_bestplace.controllers;


import com.taagne.ecomme_bestplace.dto.auth.UserResponse;
import com.taagne.ecomme_bestplace.entities.User;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(

            @AuthenticationPrincipal User user

    ) {

        UserResponse response = new UserResponse(

                user.getId(),

                user.getFirstName(),

                user.getLastName(),

                user.getEmail(),

                user.getRole()
        );

        return ResponseEntity.ok(response);
    }

}
