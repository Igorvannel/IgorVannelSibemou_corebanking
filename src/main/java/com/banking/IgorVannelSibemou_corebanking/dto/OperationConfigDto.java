package com.banking.IgorVannelSibemou_corebanking.dto;

import com.banking.IgorVannelSibemou_corebanking.dto.AccountingRule;
import com.banking.IgorVannelSibemou_corebanking.dto.FieldDefinition;
import com.banking.IgorVannelSibemou_corebanking.dto.ValidationRule;

import java.util.List;

public class OperationConfigDto {
    private Long id;
    private String operationType;
    private String name;
    private String description;
    private List<FieldDefinition> inputFields;
    private List<ValidationRule> validationRules;
    private List<AccountingRule> accountingRules;
    private boolean active;

    // Constructor
    public OperationConfigDto(Long id, String operationType, String name, String description, List<FieldDefinition> inputFields, List<ValidationRule> validationRules, List<AccountingRule> accountingRules, boolean active) {
        this.id = id;
        this.operationType = operationType;
        this.name = name;
        this.description = description;
        this.inputFields = inputFields;
        this.validationRules = validationRules;
        this.accountingRules = accountingRules;
        this.active = active;
    }

    public OperationConfigDto() {

    }

    // Getters and Setters
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

    public List<FieldDefinition> getInputFields() {
        return inputFields;
    }

    public void setInputFields(List<FieldDefinition> inputFields) {
        this.inputFields = inputFields;
    }

    public List<ValidationRule> getValidationRules() {
        return validationRules;
    }

    public void setValidationRules(List<ValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    public List<AccountingRule> getAccountingRules() {
        return accountingRules;
    }

    public void setAccountingRules(List<AccountingRule> accountingRules) {
        this.accountingRules = accountingRules;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
