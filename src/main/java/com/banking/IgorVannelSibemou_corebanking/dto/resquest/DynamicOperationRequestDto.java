package com.banking.IgorVannelSibemou_corebanking.dto.resquest;

import lombok.Data;

import java.util.Map;

@Data
public class DynamicOperationRequestDto {

    private String operationType;
    private Map<String, Object> fields;  // Champs dynamiques de l'opération

    // Constructeur par défaut
    public DynamicOperationRequestDto() {
    }

    // Constructeur avec tous les paramètres
    public DynamicOperationRequestDto(String operationType, Map<String, Object> fields) {
        this.operationType = operationType;
        this.fields = fields;
    }

    // Getters et Setters
    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}
