package com.banking.IgorVannelSibemou_corebanking.service;




import com.banking.IgorVannelSibemou_corebanking.dto.response.OperationResponseDto;
import com.banking.IgorVannelSibemou_corebanking.dto.resquest.OperationRequestDto;

import java.util.List;

public interface OperationService {
    OperationResponseDto creditAccount(OperationRequestDto request);
    OperationResponseDto debitAccount(OperationRequestDto request);
    List<OperationResponseDto> getAccountOperations(String accountNumber);
    OperationResponseDto getOperationById(Long id);
}
