package com.example.PaymentService.event;

import com.example.PaymentService.data.entity.PaymentRecord;
import com.example.PaymentService.data.repository.PaymentRecordRepository;
import com.myeshop.Core.payment.event.PaymentProcessedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentEventHandler.class);

    private final PaymentRecordRepository paymentRecordRepository;

    public PaymentEventHandler(PaymentRecordRepository paymentRecordRepository) {
        this.paymentRecordRepository = paymentRecordRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        LOGGER.info("PaymentProcessedEvent is called for orderId: " + event.getOrderId());

        PaymentRecord paymentRecord = new PaymentRecord();
        BeanUtils.copyProperties(event, paymentRecord);
        paymentRecordRepository.save(paymentRecord);
    }
}
