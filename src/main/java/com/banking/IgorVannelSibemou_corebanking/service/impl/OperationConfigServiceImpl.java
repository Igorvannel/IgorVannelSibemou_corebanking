package com.banking.IgorVannelSibemou_corebanking.service.impl;



import com.banking.IgorVannelSibemou_corebanking.dto.AccountingRule;
import com.banking.IgorVannelSibemou_corebanking.dto.FieldDefinition;
import com.banking.IgorVannelSibemou_corebanking.dto.OperationConfigDto;
import com.banking.IgorVannelSibemou_corebanking.dto.ValidationRule;
import com.banking.IgorVannelSibemou_corebanking.entity.OperationConfig;
import com.banking.IgorVannelSibemou_corebanking.exception.OperationValidationException;
import com.banking.IgorVannelSibemou_corebanking.repository.OperationConfigRepository;
import com.banking.IgorVannelSibemou_corebanking.service.OperationConfigService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationConfigServiceImpl implements OperationConfigService {

    public OperationConfigServiceImpl(OperationConfigRepository operationConfigRepository, ObjectMapper objectMapper) {
        this.operationConfigRepository = operationConfigRepository;
        this.objectMapper = objectMapper;
    }

    private final OperationConfigRepository operationConfigRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public OperationConfigDto createOperationConfig(OperationConfigDto configDto) {
        // Vérifier si le type d'opération existe déjà
        if (operationConfigRepository.findByOperationType(configDto.getOperationType()).isPresent()) {
            throw new OperationValidationException("Une configuration avec ce type d'opération existe déjà: " + configDto.getOperationType());
        }

        OperationConfig config = mapToEntity(configDto);
        OperationConfig savedConfig = operationConfigRepository.save(config);
        return mapToDto(savedConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public OperationConfigDto getOperationConfigById(Long id) {
        OperationConfig config = operationConfigRepository.findById(id)
                .orElseThrow(() -> new OperationValidationException("Configuration d'opération non trouvée avec l'ID: " + id));
        return mapToDto(config);
    }

    @Override
    @Transactional(readOnly = true)
    public OperationConfigDto getOperationConfigByType(String operationType) {
        OperationConfig config = operationConfigRepository.findByOperationType(operationType)
                .orElseThrow(() -> new OperationValidationException("Configuration d'opération non trouvée avec le type: " + operationType));
        return mapToDto(config);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperationConfigDto> getAllOperationConfigs() {
        return operationConfigRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OperationConfigDto updateOperationConfig(Long id, OperationConfigDto configDto) {
        OperationConfig config = operationConfigRepository.findById(id)
                .orElseThrow(() -> new OperationValidationException("Configuration d'opération non trouvée avec l'ID: " + id));

        // Vérifier si le nouveau type d'opération existe déjà (s'il a été modifié)
        if (!config.getOperationType().equals(configDto.getOperationType())
                && operationConfigRepository.findByOperationType(configDto.getOperationType()).isPresent()) {
            throw new OperationValidationException("Une configuration avec ce type d'opération existe déjà: " + configDto.getOperationType());
        }

        // Mise à jour des champs
        config.setOperationType(configDto.getOperationType());
        config.setName(configDto.getName());
        config.setDescription(configDto.getDescription());
        config.setActive(configDto.isActive());

        try {
            config.setInputFields(objectMapper.writeValueAsString(configDto.getInputFields()));
            config.setValidationRules(objectMapper.writeValueAsString(configDto.getValidationRules()));
            config.setAccountingRules(objectMapper.writeValueAsString(configDto.getAccountingRules()));
        } catch (JsonProcessingException e) {
            throw new OperationValidationException("Erreur lors de la sérialisation des configurations: " + e.getMessage());
        }

        OperationConfig updatedConfig = operationConfigRepository.save(config);
        return mapToDto(updatedConfig);
    }

    @Override
    @Transactional
    public void deleteOperationConfig(Long id) {
        if (!operationConfigRepository.existsById(id)) {
            throw new OperationValidationException("Configuration d'opération non trouvée avec l'ID: " + id);
        }
        operationConfigRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activateOperationConfig(Long id, boolean active) {
        OperationConfig config = operationConfigRepository.findById(id)
                .orElseThrow(() -> new OperationValidationException("Configuration d'opération non trouvée avec l'ID: " + id));

        config.setActive(active);
        operationConfigRepository.save(config);
    }

    private OperationConfig mapToEntity(OperationConfigDto dto) {
        OperationConfig entity = new OperationConfig();
        entity.setId(dto.getId());
        entity.setOperationType(dto.getOperationType());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setActive(dto.isActive());

        try {
            entity.setInputFields(objectMapper.writeValueAsString(dto.getInputFields()));
            entity.setValidationRules(objectMapper.writeValueAsString(dto.getValidationRules()));
            entity.setAccountingRules(objectMapper.writeValueAsString(dto.getAccountingRules()));
        } catch (JsonProcessingException e) {
            throw new OperationValidationException("Erreur lors de la sérialisation des configurations: " + e.getMessage());
        }

        return entity;
    }

    private OperationConfigDto mapToDto(OperationConfig entity) {
        OperationConfigDto dto = new OperationConfigDto();
        dto.setId(entity.getId());
        dto.setOperationType(entity.getOperationType());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setActive(entity.isActive());

        try {
            if (entity.getInputFields() != null) {
                dto.setInputFields(objectMapper.readValue(entity.getInputFields(),
                        new TypeReference<List<FieldDefinition>>() {}));
            }

            if (entity.getValidationRules() != null) {
                dto.setValidationRules(objectMapper.readValue(entity.getValidationRules(),
                        new TypeReference<List<ValidationRule>>() {}));
            }

            if (entity.getAccountingRules() != null) {
                dto.setAccountingRules(objectMapper.readValue(entity.getAccountingRules(),
                        new TypeReference<List<AccountingRule>>() {}));
            }
        } catch (JsonProcessingException e) {
            throw new OperationValidationException("Erreur lors de la désérialisation des configurations: " + e.getMessage());
        }

        return dto;
    }
}