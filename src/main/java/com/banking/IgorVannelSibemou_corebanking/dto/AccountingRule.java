package com.banking.IgorVannelSibemou_corebanking.dto;

public class AccountingRule {
    private String accountingCode;
    private boolean isDebit;
    private String journal;
    private String amountExpression;
    private String description;

    // Constructor
    public AccountingRule(String accountingCode, boolean isDebit, String journal, String amountExpression, String description) {
        this.accountingCode = accountingCode;
        this.isDebit = isDebit;
        this.journal = journal;
        this.amountExpression = amountExpression;
        this.description = description;
    }

    // Getters and Setters
    public String getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(String accountingCode) {
        this.accountingCode = accountingCode;
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

    public String getAmountExpression() {
        return amountExpression;
    }

    public void setAmountExpression(String amountExpression) {
        this.amountExpression = amountExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
