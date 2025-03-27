package com.banking.IgorVannelSibemou_corebanking.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OperationResponseDto {

    private Long id;
    private String operationType;
    private String accountNumber;
    private BigDecimal amount;
    private String description;
    private String status;
    private LocalDateTime operationDate;

    // Constructeur par défaut
    public OperationResponseDto() {
    }

    // Constructeur avec tous les champs
    public OperationResponseDto(Long id, String operationType, String accountNumber,
                                BigDecimal amount, String description, String status,
                                LocalDateTime operationDate) {
        this.id = id;
        this.operationType = operationType;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.operationDate = operationDate;
    }

    // Getters et Setters

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }
}
