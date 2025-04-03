package com.banking.IgorVannelSibemou_corebanking.config;

import com.banking.IgorVannelSibemou_corebanking.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Ressources publiques - Swagger UI et documentation
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()

                        // API d'authentification publique
                        .requestMatchers("/api/auth/**").permitAll()

                        // Accès basé sur les rôles
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/operation-configs/**").hasAnyRole("ADMIN", "CONFIGURATION_MANAGER")
                        .requestMatchers("/api/audit/**").hasAnyRole("ADMIN", "AUDITOR")

                        // Accès aux opérations
                        .requestMatchers(HttpMethod.GET, "/api/accounts/**").hasAnyRole("USER", "ADMIN", "AUDITOR")
                        .requestMatchers(HttpMethod.POST, "/api/accounts/**").hasAnyRole("ADMIN", "ACCOUNT_MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/accounts/**").hasAnyRole("ADMIN", "ACCOUNT_MANAGER")

                        .requestMatchers(HttpMethod.GET, "/api/operations/**").hasAnyRole("USER", "ADMIN", "AUDITOR")
                        .requestMatchers(HttpMethod.POST, "/api/operations/**").hasAnyRole("USER", "ADMIN", "TELLER")

                        .requestMatchers("/api/dynamic-operations/**").hasAnyRole("USER", "ADMIN", "TELLER")

                        // Tout autre accès nécessite une authentification
                        .anyRequest().authenticated()
                )
                // Désactiver explicitement HTTP Basic
                .httpBasic(httpBasic -> httpBasic.disable())
                // Personnaliser la gestion des exceptions d'authentification
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"error\":\"Non autorisé\",\"message\":\"JWT token manquant ou invalide\"}");
                                })
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"error\":\"Accès refusé\",\"message\":\"Vous n'avez pas les permissions requises\"}");
                                })
                );

        // Ajouter le filtre JWT avant le filtre d'authentification par défaut
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}