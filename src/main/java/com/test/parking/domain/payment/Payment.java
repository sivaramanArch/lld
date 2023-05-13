package com.test.parking.domain.payment;

import com.test.parking.utils.PrinterUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;

@Getter
public class Payment {

    private final UUID id;
    private final LocalDateTime time;
    private final Double amount;
    private final Random random = new Random();

    @Setter
    private PaymentState paymentState;


    public Payment(Double amount) {
        id = UUID.randomUUID();
        time = LocalDateTime.now(ZoneId.of("UTC"));
        this.amount = amount;
    }


    public Runnable getPaymentWorker() {

        return () -> {

            // simulates randomness to test many states
            var num = random.nextInt(10) + 1;

            if (num <= 7) {
                // 70 % of payments are successful
                PrinterUtil.printDebug("Payment received");
                paymentState = PaymentState.CONFIRMED;
            } else if (num == 9) {
                // simulates payment cancelled by user
                PrinterUtil.printDebug("Payment cancelled by user");
                paymentState = PaymentState.CANCELLED;
            } else {
                // simulates payment rejected
                PrinterUtil.printDebug("Payment rejected");
                paymentState = PaymentState.REJECTED;
            }

            PrinterUtil.printDebug("Payment processed for ID:" + this.id.toString());
        };
    }

}
