package com.banking.IgorVannelSibemou_corebanking.dto;


public class ValidationRule {
    private String name;
    private String description;
    private String expression;

    // Constructor
    public ValidationRule(String name, String description, String expression) {
        this.name = name;
        this.description = description;
        this.expression = expression;
    }

    // Getters and Setters
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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
