package com.banking.IgorVannelSibemou_corebanking.controller;


import com.banking.IgorVannelSibemou_corebanking.dto.UserDto;
import com.banking.IgorVannelSibemou_corebanking.dto.response.JwtResponse;
import com.banking.IgorVannelSibemou_corebanking.dto.response.MessageResponse;
import com.banking.IgorVannelSibemou_corebanking.dto.resquest.LoginRequest;
import com.banking.IgorVannelSibemou_corebanking.security.JwtUtil;
import com.banking.IgorVannelSibemou_corebanking.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            // Vérifier si l'utilisateur existe déjà
            try {
                userService.getUserByUsername(userDto.getUsername());
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Erreur: Ce nom d'utilisateur est déjà pris!"));
            } catch (Exception e) {
                // L'utilisateur n'existe pas, on peut continuer
            }

            // Par défaut, attribuer le rôle "ROLE_USER"
            if (userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
                Set<String> roles = new HashSet<>();
                roles.add("ROLE_USER");
                userDto.setRoles(roles);
            }

            // Créer le nouvel utilisateur
            userService.createUser(userDto);

            return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur lors de l'inscription: " + e.getMessage()));
        }
    }
}