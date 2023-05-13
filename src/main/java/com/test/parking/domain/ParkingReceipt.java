package com.test.parking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
public class ParkingReceipt {
    @Builder.Default
    LocalDateTime timeStamp = LocalDateTime.now();
    @Builder.Default
    String message = "THANKS FOR USING OUR SERVICES, PLEASE VISIT AGAIN !";
    Double amount;
    Map<String, String> parkingStationMetaData;
}
