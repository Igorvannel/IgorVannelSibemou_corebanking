package com.banking.IgorVannelSibemou_corebanking.service;



import com.banking.IgorVannelSibemou_corebanking.entity.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditService {
    void logAction(String action, String entityType, String entityId, String details, String username);
    List<AuditLog> getAuditLogsByEntity(String entityType, String entityId);
    List<AuditLog> getAuditLogsByDateRange(LocalDateTime start, LocalDateTime end);
    List<AuditLog> getAuditLogsByUsername(String username);
}
