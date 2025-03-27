package com.banking.IgorVannelSibemou_corebanking.dto.resquest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationRequestDto {
    private String accountNumber;
    private BigDecimal amount;
    private String description;
}