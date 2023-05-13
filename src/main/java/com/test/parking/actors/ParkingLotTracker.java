package com.test.parking.actors;


import com.test.parking.domain.ParkingSlot;
import com.test.parking.domain.enums.ParkingSlotType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class ParkingLotTracker {

    @Getter
    @Setter
    private static Map<ParkingSlotType, List<ParkingSlot>> inventory;

}
