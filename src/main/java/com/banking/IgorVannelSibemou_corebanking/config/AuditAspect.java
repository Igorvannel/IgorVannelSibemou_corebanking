package com.banking.IgorVannelSibemou_corebanking.config;


import com.banking.IgorVannelSibemou_corebanking.service.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Configuration
public class AuditAspect {

    private final AuditService auditService;

    public AuditAspect(AuditService auditService, ObjectMapper objectMapper) {
        this.auditService = auditService;
        this.objectMapper = objectMapper;
    }

    private final ObjectMapper objectMapper;

    @AfterReturning(
            pointcut = "execution(* com.banking.IgorVannelSibemou_corebanking.controller.*.*(..))",
            returning = "result")
    public void logAfterControllerMethods(JoinPoint joinPoint, Object result) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            // Déterminer le type d'action (GET, POST, PUT, DELETE)
            String action = determineAction(method);
            if (action == null) {
                return; // Pas d'audit pour les méthodes non annotées
            }

            // Déterminer le type d'entité
            String entityType = determineEntityType(method);

            // Déterminer l'ID de l'entité (si disponible)
            String entityId = determineEntityId(joinPoint, method);

            // Obtenir les détails de l'opération
            String details = objectMapper.writeValueAsString(joinPoint.getArgs());

            // Obtenir l'utilisateur courant
            String username = getCurrentUsername();

            // Enregistrer l'audit
            auditService.logAction(action, entityType, entityId, details, username);
        } catch (Exception e) {
            // Logger l'erreur mais ne pas interrompre l'exécution
            System.err.println("Erreur lors de la journalisation d'audit: " + e.getMessage());
        }
    }

    private String determineAction(Method method) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            return "GET";
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            return "CREATE";
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            return "UPDATE";
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            return "DELETE";
        } else if (method.isAnnotationPresent(PatchMapping.class)) {
            return "PATCH";
        }
        return null;
    }

    private String determineEntityType(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        RequestMapping classMapping = declaringClass.getAnnotation(RequestMapping.class);

        if (classMapping != null && classMapping.value().length > 0) {
            String path = classMapping.value()[0];
            // Extraire le nom de l'entité à partir du chemin (ex: /api/accounts -> accounts)
            return path.substring(path.lastIndexOf('/') + 1);
        }

        return declaringClass.getSimpleName().replace("Controller", "");
    }

    private String determineEntityId(JoinPoint joinPoint, Method method) {
        // Vérifier les annotations @PathVariable pour trouver un ID
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            PathVariable pathVar = parameter.getAnnotation(PathVariable.class);

            if (pathVar != null && (pathVar.value().equals("id") || parameter.getName().equals("id"))) {
                return args[i] != null ? args[i].toString() : "unknown";
            }
        }

        return "unknown";
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "anonymous";
    }
}