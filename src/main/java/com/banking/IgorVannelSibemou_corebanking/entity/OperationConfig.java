package com.banking.IgorVannelSibemou_corebanking.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation_configs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String operationType;

    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requiredFields;

    @Column(columnDefinition = "TEXT")
    private String validationRules;

    @Column(columnDefinition = "TEXT")
    private String accountingRules;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}