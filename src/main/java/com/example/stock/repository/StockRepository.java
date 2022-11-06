package com.example.stock.repository;

import com.example.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.persistence.LockModeType;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select s from Stock s where s.id = :id")
    Stock findByIdWithPessimisticLock(@Param(value = "id") final Long id);

    @Lock(value = LockModeType.OPTIMISTIC)
    @Query(value = "select s from Stock s where s.id = :id")
    Stock findByIdWithOptimisticLock(@Param(value = "id") final Long id);
}
