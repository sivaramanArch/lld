package com.test.parking.domain.vehicle;

import com.test.parking.domain.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Vehicle {

    @NonNull
    String registrationNumber;

    VehicleType vehicleType;
}
