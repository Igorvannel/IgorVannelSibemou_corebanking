package com.banking.IgorVannelSibemou_corebanking.service;


import com.banking.IgorVannelSibemou_corebanking.dto.response.OperationResponseDto;
import com.banking.IgorVannelSibemou_corebanking.dto.resquest.DynamicOperationRequestDto;

;

public interface DynamicOperationService {
    OperationResponseDto executeOperation(DynamicOperationRequestDto request);
}