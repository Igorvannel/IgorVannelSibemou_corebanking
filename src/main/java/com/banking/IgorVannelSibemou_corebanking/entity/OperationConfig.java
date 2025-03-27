package com.banking.IgorVannelSibemou_corebanking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation_configs")
public class OperationConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String operationType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String inputFields;  // Format JSON pour les champs d'entrée et leurs validations

    @Column(columnDefinition = "TEXT")
    private String validationRules;  // Règles de validation globales (format JSON ou expression)

    @Column(columnDefinition = "TEXT")
    private String accountingRules;  // Règles pour générer les écritures comptables

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    // Constructeur sans arguments
    public OperationConfig() {}

    // Constructeur avec tous les arguments
    public OperationConfig(Long id, String operationType, String name, String description, String inputFields, String validationRules, String accountingRules, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.operationType = operationType;
        this.name = name;
        this.description = description;
        this.inputFields = inputFields;
        this.validationRules = validationRules;
        this.accountingRules = accountingRules;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters et Setters manuels

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInputFields() {
        return inputFields;
    }

    public void setInputFields(String inputFields) {
        this.inputFields = inputFields;
    }

    public String getValidationRules() {
        return validationRules;
    }

    public void setValidationRules(String validationRules) {
        this.validationRules = validationRules;
    }

    public String getAccountingRules() {
        return accountingRules;
    }

    public void setAccountingRules(String accountingRules) {
        this.accountingRules = accountingRules;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Méthodes de persistance
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
