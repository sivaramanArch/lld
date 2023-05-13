package com.test.parking.domain.parkinglot;

import com.test.parking.utils.PaymentService;
import com.test.parking.utils.PrinterUtil;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@Builder
@Data
public class ExitGate {
    @Builder.Default
    String gateId = RandomStringUtils.randomAlphabetic(1);
    Boolean isOperational;

    public ParkingReceipt accept(ParkingTicket ticket) {
        // validate ticket
        if(!ticket.isValid()){
            throw new RuntimeException("TICKET VALIDATION ERROR , " + ticket);
        }

        // lock the parking slot - as we await payment
        // set end time as vehicle reached ending gate
        ticket.getParkingSlot().setIsOperational(false);
        ticket.setEndTime(LocalDateTime.now());


        // request payment
        var paymentService = new PaymentService(ticket);
        var paymentId = paymentService.initiatePayment();

        while(paymentService.isPaymentInprogress(paymentId)){
            // poll toll payment is success / failed
            PrinterUtil.print("Waiting for payment to complete ...");
        }

        // validate payment status
        if(!paymentService.isPaymentConfirmed(paymentId)){
            throw new RuntimeException("PAYMENT REJECTED OR CANCELLED BY USER !");
        }

        // mark slot as vacant
        ticket.getParkingSlot().setVehicle(null);
        ticket.getParkingSlot().setIsOperational(true);

        // return receipt
        return ParkingReceipt.builder().amount(ticket.getAmount()).build();
    }
}
