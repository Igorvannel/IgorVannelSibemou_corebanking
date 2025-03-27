package com.banking.IgorVannelSibemou_corebanking.dto;

import java.util.Map;

public class FieldDefinition {
    private String name;
    private String label;
    private String type;
    private boolean required;
    private Map<String, Object> validations;

    // Constructor
    public FieldDefinition(String name, String label, String type, boolean required, Map<String, Object> validations) {
        this.name = name;
        this.label = label;
        this.type = type;
        this.required = required;
        this.validations = validations;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Map<String, Object> getValidations() {
        return validations;
    }

    public void setValidations(Map<String, Object> validations) {
        this.validations = validations;
    }
}
