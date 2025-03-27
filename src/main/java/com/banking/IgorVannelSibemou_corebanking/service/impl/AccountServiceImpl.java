package com.banking.IgorVannelSibemou_corebanking.service.impl;


import com.banking.IgorVannelSibemou_corebanking.dto.AccountDto;
import com.banking.IgorVannelSibemou_corebanking.entity.Account;
import com.banking.IgorVannelSibemou_corebanking.exception.AccountNotFoundException;
import com.banking.IgorVannelSibemou_corebanking.repository.AccountRepository;
import com.banking.IgorVannelSibemou_corebanking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = mapToEntity(accountDto);
        Account savedAccount = accountRepository.save(account);
        return mapToDto(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Compte non trouvé avec l'ID: " + id));
        return mapToDto(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Compte non trouvé avec le numéro: " + accountNumber));
        return mapToDto(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Compte non trouvé avec l'ID: " + id));

        account.setAccountName(accountDto.getAccountName());
        account.setCreditLimit(accountDto.getCreditLimit());
        account.setActive(accountDto.isActive());

        Account updatedAccount = accountRepository.save(account);
        return mapToDto(updatedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getAccountBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Compte non trouvé avec le numéro: " + accountNumber));
        return account.getBalance();
    }

    private Account mapToEntity(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountName(dto.getAccountName());
        account.setBalance(dto.getBalance() != null ? dto.getBalance() : BigDecimal.ZERO);
        account.setCreditLimit(dto.getCreditLimit() != null ? dto.getCreditLimit() : BigDecimal.ZERO);
        account.setActive(dto.isActive());
        return account;
    }

    private AccountDto mapToDto(Account entity) {
        AccountDto dto = new AccountDto();
        dto.setId(entity.getId());
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setAccountName(entity.getAccountName());
        dto.setBalance(entity.getBalance());
        dto.setCreditLimit(entity.getCreditLimit());
        dto.setActive(entity.isActive());
        return dto;
    }
}