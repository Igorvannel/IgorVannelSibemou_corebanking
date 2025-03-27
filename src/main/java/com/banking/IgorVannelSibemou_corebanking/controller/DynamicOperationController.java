package com.banking.IgorVannelSibemou_corebanking.controller;



import com.banking.IgorVannelSibemou_corebanking.dto.response.OperationResponseDto;
import com.banking.IgorVannelSibemou_corebanking.dto.resquest.DynamicOperationRequestDto;
import com.banking.IgorVannelSibemou_corebanking.service.DynamicOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dynamic-operations")
@RequiredArgsConstructor
@Tag(name = "Dynamic Operation API", description = "API pour l'exécution des opérations configurées dynamiquement")
public class DynamicOperationController {

    public DynamicOperationController(DynamicOperationService dynamicOperationService) {
        this.dynamicOperationService = dynamicOperationService;
    }

    private final DynamicOperationService dynamicOperationService;

    @PostMapping("/execute")
    @Operation(summary = "Exécuter une opération dynamique")
    public ResponseEntity<OperationResponseDto> executeOperation(@RequestBody DynamicOperationRequestDto request) {
        return new ResponseEntity<>(dynamicOperationService.executeOperation(request), HttpStatus.CREATED);
    }
}