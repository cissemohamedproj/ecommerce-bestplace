package com.taagne.ecomme_bestplace.dto.auth;

import com.taagne.ecomme_bestplace.entities.base.Role;

public record UserResponse(

        Long id,

        String firstName,

        String lastName,

        String email,

        Role role

) {}
