package com.banking.IgorVannelSibemou_corebanking.repository;

import com.banking.IgorVannelSibemou_corebanking.entity.OperationConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationConfigRepository extends JpaRepository<OperationConfig, Long> {
    Optional<OperationConfig> findByOperationType(String operationType);
}