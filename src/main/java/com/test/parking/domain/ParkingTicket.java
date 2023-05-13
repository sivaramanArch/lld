package com.test.parking.domain;

import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Duration;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParkingTicket {
    @Builder.Default
    String id = RandomStringUtils.randomAlphanumeric(5);

    @NonNull
    ParkingSlot parkingSlot;

    LocalDateTime startTime;
    LocalDateTime endTime;
    Boolean isPaid;

    @Builder.Default
    Double amount = 0d;

    public Double getAmount(){
        if(endTime == null){
            return amount;
        }

        // calculate
        return Duration.between(startTime, endTime).toSeconds() * 2d;
    }

    public boolean isValid() {
        // validation rules go here
        boolean startTimeValid = !(startTime == null);
        boolean isNotAlreadyPaid = !isPaid;
        boolean isAmountNotSet = (amount == 0d);

        return startTimeValid && isAmountNotSet && isNotAlreadyPaid;
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                "id='" + id + '\'' +
                ", slot id =" + parkingSlot.getId() +
                ", slot id =" + parkingSlot.getSlotType() +
                ", startTime=" + startTime +
                '}';
    }
}
