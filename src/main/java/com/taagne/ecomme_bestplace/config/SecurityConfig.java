package com.taagne.ecomme_bestplace.config;


import com.taagne.ecomme_bestplace.filters.JwtAuthenticationFilter;
import com.taagne.ecomme_bestplace.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailsService userDetailsService;


    // 1. Configuration du PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // 2. Configuration de l'AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder
    ) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }


    // 3. Configuration de l'AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }


    // 4. Configuration de SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(

            HttpSecurity http,

            AuthenticationProvider authenticationProvider

    ) throws Exception {

        http

                // Désactiver CSRF pour notre API REST utilisant JWT
                .csrf(csrf -> csrf.disable())


                // Configurer les sessions
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                // Configurer les règles d'accès
                .authorizeHttpRequests(auth -> auth

                        // Routes accessibles sans authentification
                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        // Toutes les autres routes nécessitent
                        // un utilisateur authentifié
                        .anyRequest()
                        .authenticated()
                )


                // Déclarer notre AuthenticationProvider
                .authenticationProvider(authenticationProvider)


                // Désactiver les formulaires HTML et HTTP Basic
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())


                // Ajouter notre filtre JWT
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(exception -> exception

                        // Utilisateur non authentifié
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(
                                        HttpServletResponse.SC_UNAUTHORIZED,
                                        "Unauthorized"
                                )
                        )

                        // Utilisateur authentifié sans permission
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(
                                        HttpServletResponse.SC_FORBIDDEN,
                                        "Forbidden"
                                )
                        )
                );


        return http.build();
    }


    // 5. Empêcher l'enregistrement du filtre JWT
    // directement par le conteneur Servlet.
    // Il doit être exécuté uniquement dans SecurityFilterChain.
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter>
    jwtFilterRegistration(
            JwtAuthenticationFilter filter
    ) {

        FilterRegistrationBean<JwtAuthenticationFilter> registration =
                new FilterRegistrationBean<>(filter);

        registration.setEnabled(false);

        return registration;
    }

}
