package com.banking.IgorVannelSibemou_corebanking.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounting_entries")
public class AccountingEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountingCode;  // Code comptable selon le plan comptable

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean isDebit;  // true = débit, false = crédit

    @Column(nullable = false)
    private String journal;  // Journal comptable

    @ManyToOne
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Constructeur sans arguments
    public AccountingEntry() {}

    // Constructeur avec tous les arguments
    public AccountingEntry(Long id, String accountingCode, String description, BigDecimal amount, boolean isDebit, String journal, Operation operation, LocalDateTime createdAt) {
        this.id = id;
        this.accountingCode = accountingCode;
        this.description = description;
        this.amount = amount;
        this.isDebit = isDebit;
        this.journal = journal;
        this.operation = operation;
        this.createdAt = createdAt;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(String accountingCode) {
        this.accountingCode = accountingCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isDebit() {
        return isDebit;
    }

    public void setDebit(boolean debit) {
        isDebit = debit;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Méthodes de persistance
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void setIsDebit(boolean debit) {
        this.isDebit=isDebit;
    }
}
