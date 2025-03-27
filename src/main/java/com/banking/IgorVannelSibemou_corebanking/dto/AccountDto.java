package com.banking.IgorVannelSibemou_corebanking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String accountNumber;
    private String accountName;
    private BigDecimal balance;
    private BigDecimal creditLimit;
    private boolean active;
}
