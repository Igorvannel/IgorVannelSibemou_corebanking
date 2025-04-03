package com.banking.IgorVannelSibemou_corebanking.config;

import com.banking.IgorVannelSibemou_corebanking.dto.UserDto;
import com.banking.IgorVannelSibemou_corebanking.exception.ResourceNotFoundException;
import com.banking.IgorVannelSibemou_corebanking.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        // Vérifier si l'utilisateur admin existe déjà
        try {
            userService.getUserByUsername("admin");
            System.out.println("Admin user already exists.");
        } catch (ResourceNotFoundException e) {
            // Créer l'utilisateur admin si non existant
            UserDto adminUser = new UserDto();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123"); // À changer en production !
            adminUser.setFullName("Administrateur");
            adminUser.setActive(true);

            // Ajouter les rôles nécessaires
            Set<String> adminRoles = new HashSet<>();
            adminRoles.add("ROLE_ADMIN");
            adminRoles.add("ROLE_USER");
            adminRoles.add("ROLE_ACCOUNT_MANAGER");
            adminRoles.add("ROLE_CONFIGURATION_MANAGER");
            adminRoles.add("ROLE_AUDITOR");
            adminRoles.add("ROLE_TELLER");
            adminUser.setRoles(adminRoles);

            userService.createUser(adminUser);
            System.out.println("Admin user created successfully.");
        }

        // Création d'un utilisateur standard
        try {
            userService.getUserByUsername("user");
            System.out.println("Regular user already exists.");
        } catch (ResourceNotFoundException e) {
            UserDto regularUser = new UserDto();
            regularUser.setUsername("user");
            regularUser.setPassword("user123");
            regularUser.setFullName("Utilisateur Standard");
            regularUser.setActive(true);

            Set<String> userRoles = new HashSet<>();
            userRoles.add("ROLE_USER");
            regularUser.setRoles(userRoles);

            userService.createUser(regularUser);
            System.out.println("Regular user created successfully.");
        }

        // Création d'un guichetier
        try {
            userService.getUserByUsername("teller");
            System.out.println("Teller user already exists.");
        } catch (ResourceNotFoundException e) {
            UserDto tellerUser = new UserDto();
            tellerUser.setUsername("teller");
            tellerUser.setPassword("teller123");
            tellerUser.setFullName("Guichetier");
            tellerUser.setActive(true);

            Set<String> tellerRoles = new HashSet<>();
            tellerRoles.add("ROLE_USER");
            tellerRoles.add("ROLE_TELLER");
            tellerUser.setRoles(tellerRoles);

            userService.createUser(tellerUser);
            System.out.println("Teller user created successfully.");
        }

        // Création d'un auditeur
        try {
            userService.getUserByUsername("auditor");
            System.out.println("Auditor user already exists.");
        } catch (ResourceNotFoundException e) {
            UserDto auditorUser = new UserDto();
            auditorUser.setUsername("auditor");
            auditorUser.setPassword("auditor123");
            auditorUser.setFullName("Auditeur");
            auditorUser.setActive(true);

            Set<String> auditorRoles = new HashSet<>();
            auditorRoles.add("ROLE_USER");
            auditorRoles.add("ROLE_AUDITOR");
            auditorUser.setRoles(auditorRoles);

            userService.createUser(auditorUser);
            System.out.println("Auditor user created successfully.");
        }

        // Création d'un gestionnaire de configuration
        try {
            userService.getUserByUsername("config");
            System.out.println("Configuration manager already exists.");
        } catch (ResourceNotFoundException e) {
            UserDto configUser = new UserDto();
            configUser.setUsername("config");
            configUser.setPassword("config123");
            configUser.setFullName("Gestionnaire de Configuration");
            configUser.setActive(true);

            Set<String> configRoles = new HashSet<>();
            configRoles.add("ROLE_USER");
            configRoles.add("ROLE_CONFIGURATION_MANAGER");
            configUser.setRoles(configRoles);

            userService.createUser(configUser);
            System.out.println("Configuration manager created successfully.");
        }
    }
}