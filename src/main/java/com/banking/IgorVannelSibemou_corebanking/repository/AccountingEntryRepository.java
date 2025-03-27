package com.banking.IgorVannelSibemou_corebanking.repository;


import com.banking.IgorVannelSibemou_corebanking.entity.AccountingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountingEntryRepository extends JpaRepository<AccountingEntry, Long> {
    List<AccountingEntry> findByOperationId(Long operationId);
}