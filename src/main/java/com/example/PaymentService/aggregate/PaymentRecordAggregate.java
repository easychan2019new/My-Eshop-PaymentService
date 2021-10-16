package com.example.PaymentService.aggregate;

import com.myeshop.Core.payment.command.ProcessPaymentCommand;
import com.myeshop.Core.payment.event.PaymentProcessedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aggregate
public class PaymentRecordAggregate {

    @AggregateIdentifier
    private String recordId;

    private String paymentId;

    private String orderId;

    public PaymentRecordAggregate() {}

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentRecordAggregate.class);

    @CommandHandler
    public PaymentRecordAggregate(ProcessPaymentCommand processPaymentCommand) {

        if (processPaymentCommand.getPaymentDetail() == null) {
            LOGGER.info("caught: 1!");
            throw new IllegalArgumentException("Missing payment detail");
        }

        if (processPaymentCommand.getOrderId() == null) {
            LOGGER.info("caught: 2!");
            throw new IllegalArgumentException("Missing orderId");
        }

        if (processPaymentCommand.getPaymentId() == null) {
            LOGGER.info("caught: 3!");
            throw new IllegalArgumentException("Missing paymentId");
        }

        if (!processPaymentCommand.getPaymentDetail().getCardNumber().equals("123456789123") ||
            !processPaymentCommand.getPaymentDetail().getCvv().equals("123")) {
            LOGGER.info("caught: 4!");
            throw new IllegalArgumentException("The payment information is not correct");
        }

        AggregateLifecycle.apply(new PaymentProcessedEvent(
                processPaymentCommand.getRecordId(),
                processPaymentCommand.getOrderId(),
                processPaymentCommand.getPaymentId()));
        LOGGER.info("Publish paymentProcessedEvent!");
    }

    @EventSourcingHandler
    protected void on(PaymentProcessedEvent paymentProcessedEvent) {
        this.recordId = paymentProcessedEvent.getRecordId();
        this.orderId = paymentProcessedEvent.getOrderId();
        this.paymentId = paymentProcessedEvent.getPaymentId();
    }
}
