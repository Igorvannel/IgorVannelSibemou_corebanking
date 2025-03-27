package com.banking.IgorVannelSibemou_corebanking.service.impl;


import com.banking.IgorVannelSibemou_corebanking.dto.response.OperationResponseDto;
import com.banking.IgorVannelSibemou_corebanking.dto.resquest.OperationRequestDto;
import com.banking.IgorVannelSibemou_corebanking.entity.Account;
import com.banking.IgorVannelSibemou_corebanking.entity.Operation;
import com.banking.IgorVannelSibemou_corebanking.exception.AccountNotFoundException;
import com.banking.IgorVannelSibemou_corebanking.exception.InsufficientFundsException;
import com.banking.IgorVannelSibemou_corebanking.exception.OperationValidationException;
import com.banking.IgorVannelSibemou_corebanking.repository.AccountRepository;
import com.banking.IgorVannelSibemou_corebanking.repository.OperationRepository;
import com.banking.IgorVannelSibemou_corebanking.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    public OperationServiceImpl(OperationRepository operationRepository, AccountRepository accountRepository) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
    }

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public OperationResponseDto creditAccount(OperationRequestDto request) {
        validateOperationRequest(request);

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Compte non trouvé avec le numéro: " + request.getAccountNumber()));

        // Créer l'opération
        Operation operation = new Operation();
        operation.setOperationType("CREDIT");
        operation.setAmount(request.getAmount());
        operation.setDescription(request.getDescription());
        operation.setAccount(account);
        operation.setStatus("COMPLETED");
        operation.setOperationDate(LocalDateTime.now());

        // Mettre à jour le solde du compte
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        // Sauvegarder l'opération
        Operation savedOperation = operationRepository.save(operation);

        return mapToResponseDto(savedOperation);
    }

    @Override
    @Transactional
    public OperationResponseDto debitAccount(OperationRequestDto request) {
        validateOperationRequest(request);

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Compte non trouvé avec le numéro: " + request.getAccountNumber()));

        // Vérifier si le compte a suffisamment de fonds
        BigDecimal availableFunds = account.getBalance().add(account.getCreditLimit());
        if (availableFunds.compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException("Fonds insuffisants pour effectuer cette opération");
        }

        // Créer l'opération
        Operation operation = new Operation();
        operation.setOperationType("DEBIT");
        operation.setAmount(request.getAmount());
        operation.setDescription(request.getDescription());
        operation.setAccount(account);
        operation.setStatus("COMPLETED");
        operation.setOperationDate(LocalDateTime.now());

        // Mettre à jour le solde du compte
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        // Sauvegarder l'opération
        Operation savedOperation = operationRepository.save(operation);

        return mapToResponseDto(savedOperation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperationResponseDto> getAccountOperations(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Compte non trouvé avec le numéro: " + accountNumber));

        return operationRepository.findByAccountId(account.getId()).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OperationResponseDto getOperationById(Long id) {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new OperationValidationException("Opération non trouvée avec l'ID: " + id));

        return mapToResponseDto(operation);
    }

    private void validateOperationRequest(OperationRequestDto request) {
        if (request.getAccountNumber() == null || request.getAccountNumber().trim().isEmpty()) {
            throw new OperationValidationException("Le numéro de compte est requis");
        }

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperationValidationException("Le montant doit être supérieur à zéro");
        }

        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new OperationValidationException("La description est requise");
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