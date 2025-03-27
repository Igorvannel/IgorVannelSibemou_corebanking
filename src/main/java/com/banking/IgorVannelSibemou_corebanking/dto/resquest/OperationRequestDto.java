package com.banking.IgorVannelSibemou_corebanking.dto.resquest;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OperationRequestDto {

    private String accountNumber;
    private BigDecimal amount;
    private String description;

    // Constructeur par défaut
    public OperationRequestDto() {
    }

    // Constructeur avec tous les champs
    public OperationRequestDto(String accountNumber, BigDecimal amount, String description) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = description;
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
