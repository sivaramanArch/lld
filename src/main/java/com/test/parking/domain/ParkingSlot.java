package com.test.parking.domain;

import com.test.parking.domain.enums.ParkingSlotType;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingSlot {
    @Builder.Default
    String id = RandomStringUtils.randomAlphanumeric(3);

    ParkingSlotType slotType;
    Vehicle vehicle;
    Boolean isOperational;

    public Boolean isVacant() {
        return isOperational && (vehicle == null);
    }
}
