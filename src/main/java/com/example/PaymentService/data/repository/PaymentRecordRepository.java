package com.example.PaymentService.data.repository;

import com.example.PaymentService.data.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, String> {
}
