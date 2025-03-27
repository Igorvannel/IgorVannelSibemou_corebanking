package com.banking.IgorVannelSibemou_corebanking.service;

import com.banking.IgorVannelSibemou_corebanking.dto.OperationConfigDto;

import java.util.List;

public interface OperationConfigService {
    OperationConfigDto createOperationConfig(OperationConfigDto configDto);
    OperationConfigDto getOperationConfigById(Long id);
    OperationConfigDto getOperationConfigByType(String operationType);
    List<OperationConfigDto> getAllOperationConfigs();
    OperationConfigDto updateOperationConfig(Long id, OperationConfigDto configDto);
    void deleteOperationConfig(Long id);
    void activateOperationConfig(Long id, boolean active);
}