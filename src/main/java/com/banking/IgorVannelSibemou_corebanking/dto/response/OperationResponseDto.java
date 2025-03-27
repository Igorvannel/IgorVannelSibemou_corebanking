package com.banking.IgorVannelSibemou_corebanking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationResponseDto {
    private Long id;
    private String operationType;
    private String accountNumber;
    private BigDecimal amount;
    private String description;
    private String status;
    private LocalDateTime operationDate;
}