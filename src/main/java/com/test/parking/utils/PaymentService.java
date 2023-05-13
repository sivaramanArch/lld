package com.test.parking.utils;

import com.test.parking.domain.parkinglot.ParkingTicket;
import com.test.parking.domain.payment.Payment;
import com.test.parking.domain.payment.PaymentState;

import java.util.UUID;

/**
 * Mock class to simulate payment event
 */
public class PaymentService {

    private final Payment payment;
    private final ParkingTicket order;

    public PaymentService(ParkingTicket parkingTicket) {
        this.order = parkingTicket;
        this.payment = new Payment(parkingTicket.getAmount());
    }


    // initiate a payment request
    public UUID initiatePayment() {
        payment.setPaymentState(PaymentState.INITIATED);
        PrinterUtil.printInfo("PAYMENT REQUESTED INITIATED FOR: " + order.getAmount());

        // trigger async event
        payment.getPaymentWorker().run();

        return payment.getId();
    }

    public boolean isPaymentConfirmed(UUID id) {
        return this.getPaymentState(id).equals(PaymentState.CONFIRMED);
    }

    public boolean isPaymentInprogress(UUID id) {
        return this.getPaymentState(id).equals(PaymentState.IN_PROGRESS);
    }

    public boolean isPaymentCancelled(UUID id) {
        return this.getPaymentState(id).equals(PaymentState.CANCELLED);
    }

    public boolean isPaymentRejected(UUID id) {
        return this.getPaymentState(id).equals(PaymentState.REJECTED);
    }

    private PaymentState getPaymentState(UUID id) {
        return payment.getPaymentState();
    }
}




