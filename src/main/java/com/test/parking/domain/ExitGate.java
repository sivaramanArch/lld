package com.test.parking.domain;

import com.test.parking.actors.PaymentService;
import com.test.parking.actors.PrinterUtil;
import com.test.parking.domain.enums.ParkingSlotType;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
        ticket.parkingSlot.setIsOperational(false);
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
        return ParkingReceipt.builder().amount(ticket.amount).build();
    }
}
