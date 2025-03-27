package com.banking.IgorVannelSibemou_corebanking.service.impl;


import com.banking.IgorVannelSibemou_corebanking.dto.AccountingRule;
import com.banking.IgorVannelSibemou_corebanking.dto.FieldDefinition;
import com.banking.IgorVannelSibemou_corebanking.dto.ValidationRule;
import com.banking.IgorVannelSibemou_corebanking.dto.response.OperationResponseDto;
import com.banking.IgorVannelSibemou_corebanking.dto.resquest.DynamicOperationRequestDto;
import com.banking.IgorVannelSibemou_corebanking.entity.Account;
import com.banking.IgorVannelSibemou_corebanking.entity.AccountingEntry;
import com.banking.IgorVannelSibemou_corebanking.entity.Operation;
import com.banking.IgorVannelSibemou_corebanking.entity.OperationConfig;
import com.banking.IgorVannelSibemou_corebanking.exception.AccountNotFoundException;
import com.banking.IgorVannelSibemou_corebanking.exception.InsufficientFundsException;
import com.banking.IgorVannelSibemou_corebanking.exception.OperationValidationException;
import com.banking.IgorVannelSibemou_corebanking.repository.AccountRepository;
import com.banking.IgorVannelSibemou_corebanking.repository.AccountingEntryRepository;
import com.banking.IgorVannelSibemou_corebanking.repository.OperationConfigRepository;
import com.banking.IgorVannelSibemou_corebanking.repository.OperationRepository;
import com.banking.IgorVannelSibemou_corebanking.service.DynamicOperationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class DynamicOperationServiceImpl implements DynamicOperationService {

    private final OperationConfigRepository operationConfigRepository;
    private final AccountRepository accountRepository;

    public DynamicOperationServiceImpl(OperationConfigRepository operationConfigRepository, AccountRepository accountRepository, OperationRepository operationRepository, AccountingEntryRepository accountingEntryRepository, ObjectMapper objectMapper) {
        this.operationConfigRepository = operationConfigRepository;
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.accountingEntryRepository = accountingEntryRepository;
        this.objectMapper = objectMapper;
    }

    private final OperationRepository operationRepository;
    private final AccountingEntryRepository accountingEntryRepository;
    private final ObjectMapper objectMapper;
    private final ExpressionParser expressionParser = new SpelExpressionParser();

    @Override
    @Transactional
    public OperationResponseDto executeOperation(DynamicOperationRequestDto request) {
        // Récupérer la configuration de l'opération
        OperationConfig config = operationConfigRepository.findByOperationType(request.getOperationType())
                .orElseThrow(() -> new OperationValidationException("Type d'opération non configuré: " + request.getOperationType()));

        if (!config.isActive()) {
            throw new OperationValidationException("L'opération demandée est désactivée: " + request.getOperationType());
        }

        // Valider les champs de l'opération
        validateOperationFields(config, request.getFields());

        // Valider les règles métier
        validateBusinessRules(config, request.getFields());

        // Récupérer le compte concerné
        String accountNumber = (String) request.getFields().get("accountNumber");
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Compte non trouvé avec le numéro: " + accountNumber));

        // Créer l'opération
        Operation operation = createOperation(config, account, request.getFields());

        // Traiter l'opération selon sa nature (crédit/débit)
        processOperationAmount(operation, account, request.getFields());

        // Générer les écritures comptables
        generateAccountingEntries(config, operation, request.getFields());

        // Sauvegarder l'opération
        Operation savedOperation = operationRepository.save(operation);

        return mapToResponseDto(savedOperation);
    }

    private void validateOperationFields(OperationConfig config, Map<String, Object> fields) {
        try {
            List<FieldDefinition> fieldDefinitions = objectMapper.readValue(config.getInputFields(),
                    new TypeReference<List<FieldDefinition>>() {});

            for (FieldDefinition fieldDef : fieldDefinitions) {
                // Vérifier si les champs requis sont présents
                if (fieldDef.isRequired() && (fields.get(fieldDef.getName()) == null
                        || fields.get(fieldDef.getName()).toString().trim().isEmpty())) {
                    throw new OperationValidationException("Le champ obligatoire est manquant: " + fieldDef.getName());
                }

                // Vérifier le type des champs
                if (fields.get(fieldDef.getName()) != null) {
                    validateFieldType(fieldDef, fields.get(fieldDef.getName()));

                    // Appliquer les validations spécifiques
                    if (fieldDef.getValidations() != null) {
                        applyFieldValidations(fieldDef, fields.get(fieldDef.getName()));
                    }
                }
            }
        } catch (Exception e) {
            throw new OperationValidationException("Erreur lors de la validation des champs: " + e.getMessage());
        }
    }

    private void validateFieldType(FieldDefinition fieldDef, Object value) {
        switch (fieldDef.getType().toUpperCase()) {
            case "NUMBER":
                try {
                    if (value instanceof String) {
                        new BigDecimal((String) value);
                    } else if (!(value instanceof Number)) {
                        throw new OperationValidationException("Le champ " + fieldDef.getName() + " doit être un nombre");
                    }
                } catch (NumberFormatException e) {
                    throw new OperationValidationException("Le champ " + fieldDef.getName() + " doit être un nombre valide");
                }
                break;
            case "DATE":
                // Validation de date à implémenter selon le format attendu
                break;
            case "TEXT":
            default:
                // Pour les champs texte, pas de validation de type spécifique
                break;
        }
    }

    private void applyFieldValidations(FieldDefinition fieldDef, Object value) {
        if (fieldDef.getType().equalsIgnoreCase("NUMBER")) {
            BigDecimal numValue;
            if (value instanceof String) {
                numValue = new BigDecimal((String) value);
            } else if (value instanceof Number) {
                numValue = BigDecimal.valueOf(((Number) value).doubleValue());
            } else {
                return; // Impossible d'appliquer des validations numériques sur ce type
            }

            // Valider min/max
            if (fieldDef.getValidations().containsKey("min")) {
                BigDecimal min = new BigDecimal(fieldDef.getValidations().get("min").toString());
                if (numValue.compareTo(min) < 0) {
                    throw new OperationValidationException("Le champ " + fieldDef.getName()
                            + " doit être supérieur ou égal à " + min);
                }
            }

            if (fieldDef.getValidations().containsKey("max")) {
                BigDecimal max = new BigDecimal(fieldDef.getValidations().get("max").toString());
                if (numValue.compareTo(max) > 0) {
                    throw new OperationValidationException("Le champ " + fieldDef.getName()
                            + " doit être inférieur ou égal à " + max);
                }
            }
        }

    }

    private void validateBusinessRules(OperationConfig config, Map<String, Object> fields) {
        try {
            if (config.getValidationRules() != null && !config.getValidationRules().trim().isEmpty()) {
                List<ValidationRule> rules = objectMapper.readValue(config.getValidationRules(),
                        new TypeReference<List<ValidationRule>>() {});

                StandardEvaluationContext context = new StandardEvaluationContext();
                context.setVariables(fields);

                for (ValidationRule rule : rules) {
                    Expression expression = expressionParser.parseExpression(rule.getExpression());
                    Boolean result = expression.getValue(context, Boolean.class);

                    if (result == null || !result) {
                        throw new OperationValidationException("Validation échouée: " + rule.getDescription());
                    }
                }
            }
        } catch (Exception e) {
            throw new OperationValidationException("Erreur lors de la validation des règles métier: " + e.getMessage());
        }
    }

    private Operation createOperation(OperationConfig config, Account account, Map<String, Object> fields) {
        Operation operation = new Operation();
        operation.setOperationType(config.getOperationType());
        operation.setAccount(account);
        operation.setStatus("PENDING");
        operation.setOperationDate(LocalDateTime.now());

        // Récupérer le montant de l'opération
        if (fields.containsKey("amount")) {
            BigDecimal amount;
            if (fields.get("amount") instanceof String) {
                amount = new BigDecimal((String) fields.get("amount"));
            } else if (fields.get("amount") instanceof Number) {
                amount = BigDecimal.valueOf(((Number) fields.get("amount")).doubleValue());
            } else {
                throw new OperationValidationException("Format de montant invalide");
            }
            operation.setAmount(amount);
        } else {
            throw new OperationValidationException("Le montant est obligatoire pour toute opération");
        }

        // Récupérer la description
        if (fields.containsKey("description")) {
            operation.setDescription((String) fields.get("description"));
        } else {
            operation.setDescription(config.getName() + " - " + LocalDateTime.now());
        }

        return operation;
    }

    private void processOperationAmount(Operation operation, Account account, Map<String, Object> fields) {
        BigDecimal amount = operation.getAmount();

        // Déterminer si c'est un crédit ou un débit selon le type d'opération
        boolean isCredit = operation.getOperationType().startsWith("CREDIT_");
        boolean isDebit = operation.getOperationType().startsWith("DEBIT_");

        if (isCredit) {
            // Pour les opérations de crédit, ajouter le montant au solde
            account.setBalance(account.getBalance().add(amount));
            operation.setStatus("COMPLETED");
        } else if (isDebit) {
            // Pour les opérations de débit, vérifier les fonds disponibles
            BigDecimal availableFunds = account.getBalance().add(account.getCreditLimit());

            if (availableFunds.compareTo(amount) < 0) {
                throw new InsufficientFundsException("Fonds insuffisants pour effectuer cette opération");
            }

            // Soustraire le montant du solde
            account.setBalance(account.getBalance().subtract(amount));
            operation.setStatus("COMPLETED");
        } else {
            // Pour les autres types d'opérations, comportement spécifique à implémenter
            // Par défaut, on ne modifie pas le solde
            operation.setStatus("COMPLETED");
        }

        // Sauvegarder le compte mis à jour
        accountRepository.save(account);
    }

    private void generateAccountingEntries(OperationConfig config, Operation operation, Map<String, Object> fields) {
        try {
            if (config.getAccountingRules() != null && !config.getAccountingRules().trim().isEmpty()) {
                List<AccountingRule> rules = objectMapper.readValue(config.getAccountingRules(),
                        new TypeReference<List<AccountingRule>>() {});

                StandardEvaluationContext context = new StandardEvaluationContext();
                context.setVariables(fields);
                context.setVariable("operationAmount", operation.getAmount());

                for (AccountingRule rule : rules) {
                    // Calculer le montant selon l'expression
                    BigDecimal entryAmount;
                    if (rule.getAmountExpression() != null && !rule.getAmountExpression().trim().isEmpty()) {
                        Expression expression = expressionParser.parseExpression(rule.getAmountExpression());
                        entryAmount = expression.getValue(context, BigDecimal.class);
                    } else {
                        entryAmount = operation.getAmount();
                    }

                    // Créer l'écriture comptable
                    AccountingEntry entry = new AccountingEntry();
                    entry.setAccountingCode(rule.getAccountingCode());
                    entry.setAmount(entryAmount);
                    entry.setIsDebit(rule.isDebit());
                    entry.setJournal(rule.getJournal());
                    entry.setDescription(rule.getDescription());
                    entry.setOperation(operation);

                    // Sauvegarder l'écriture comptable
                    accountingEntryRepository.save(entry);
                }
            }
        } catch (Exception e) {
            throw new OperationValidationException("Erreur lors de la génération des écritures comptables: " + e.getMessage());
        }
    }

    private OperationResponseDto mapToResponseDto(Operation operation) {
        OperationResponseDto dto = new OperationResponseDto();
        dto.setId(operation.getId());
        dto.setOperationType(operation.getOperationType());
        dto.setAccountNumber(operation.getAccount().getAccountNumber());
        dto.setAmount(operation.getAmount());
        dto.setDescription(operation.getDescription());
        dto.setStatus(operation.getStatus());
        dto.setOperationDate(operation.getOperationDate());
        return dto;
    }
}