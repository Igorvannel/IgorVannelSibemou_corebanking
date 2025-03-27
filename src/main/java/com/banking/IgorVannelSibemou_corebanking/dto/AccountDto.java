package com.banking.IgorVannelSibemou_corebanking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private Long id;
    private String accountNumber;
    private String accountName;
    private BigDecimal balance;
    private BigDecimal creditLimit;
    private boolean active;

    // Constructeur par défaut
    public AccountDto() {
    }

    // Constructeur avec tous les champs
    public AccountDto(Long id, String accountNumber, String accountName, BigDecimal balance, BigDecimal creditLimit, boolean active) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.creditLimit = creditLimit;
        this.active = active;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
