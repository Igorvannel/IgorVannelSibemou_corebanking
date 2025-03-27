package com.banking.IgorVannelSibemou_corebanking.controller;

import com.banking.IgorVannelSibemou_corebanking.dto.OperationConfigDto;
import com.banking.IgorVannelSibemou_corebanking.service.OperationConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operation-configs")
@Tag(name = "Operation Configuration API", description = "API pour la gestion des configurations d'opérations")
public class OperationConfigController {

    private final OperationConfigService operationConfigService;

    public OperationConfigController(OperationConfigService operationConfigService) {
        this.operationConfigService = operationConfigService;
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle configuration d'opération")
    public ResponseEntity<OperationConfigDto> createOperationConfig(@RequestBody OperationConfigDto configDto) {
        return new ResponseEntity<>(operationConfigService.createOperationConfig(configDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une configuration d'opération par son ID")
    public ResponseEntity<OperationConfigDto> getOperationConfigById(@PathVariable Long id) {
        return ResponseEntity.ok(operationConfigService.getOperationConfigById(id));
    }

    @GetMapping("/type/{operationType}")
    @Operation(summary = "Récupérer une configuration d'opération par son type")
    public ResponseEntity<OperationConfigDto> getOperationConfigByType(@PathVariable String operationType) {
        return ResponseEntity.ok(operationConfigService.getOperationConfigByType(operationType));
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les configurations d'opération")
    public ResponseEntity<List<OperationConfigDto>> getAllOperationConfigs() {
        return ResponseEntity.ok(operationConfigService.getAllOperationConfigs());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une configuration d'opération")
    public ResponseEntity<OperationConfigDto> updateOperationConfig(
            @PathVariable Long id, @RequestBody OperationConfigDto configDto) {
        return ResponseEntity.ok(operationConfigService.updateOperationConfig(id, configDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une configuration d'opération")
    public ResponseEntity<Void> deleteOperationConfig(@PathVariable Long id) {
        operationConfigService.deleteOperationConfig(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activer ou désactiver une configuration d'opération")
    public ResponseEntity<Void> activateOperationConfig(@PathVariable Long id, @RequestParam boolean active) {
        operationConfigService.activateOperationConfig(id, active);
        return ResponseEntity.noContent().build();
    }
}