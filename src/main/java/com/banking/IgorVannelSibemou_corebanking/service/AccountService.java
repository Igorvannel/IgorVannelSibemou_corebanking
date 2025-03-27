package com.banking.IgorVannelSibemou_corebanking.service;

import com.banking.IgorVannelSibemou_corebanking.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    AccountDto getAccountByNumber(String accountNumber);
    List<AccountDto> getAllAccounts();
    AccountDto updateAccount(Long id, AccountDto accountDto);
    BigDecimal getAccountBalance(String accountNumber);
}