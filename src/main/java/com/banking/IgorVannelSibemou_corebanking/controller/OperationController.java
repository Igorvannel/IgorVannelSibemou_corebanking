package com.banking.IgorVannelSibemou_corebanking.controller;



import com.banking.IgorVannelSibemou_corebanking.dto.response.OperationResponseDto;

import com.banking.IgorVannelSibemou_corebanking.dto.resquest.OperationRequestDto;
import com.banking.IgorVannelSibemou_corebanking.service.OperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
@Tag(name = "Operation API", description = "API pour la gestion des opérations bancaires")
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/credit")
    @Operation(summary = "Créditer un compte")
    public ResponseEntity<OperationResponseDto> creditAccount(@RequestBody OperationRequestDto request) {
        return new ResponseEntity<>(operationService.creditAccount(request), HttpStatus.CREATED);
    }

    @PostMapping("/debit")
    @Operation(summary = "Débiter un compte")
    public ResponseEntity<OperationResponseDto> debitAccount(@RequestBody OperationRequestDto request) {
        return new ResponseEntity<>(operationService.debitAccount(request), HttpStatus.CREATED);
    }

    @GetMapping("/account/{accountNumber}")
    @Operation(summary = "Récupérer les opérations d'un compte")
    public ResponseEntity<List<OperationResponseDto>> getAccountOperations(@PathVariable String accountNumber) {
        return ResponseEntity.ok(operationService.getAccountOperations(accountNumber));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une opération par son ID")
    public ResponseEntity<OperationResponseDto> getOperationById(@PathVariable Long id) {
        return ResponseEntity.ok(operationService.getOperationById(id));
    }
}
