package com.test.parking.utils;

import com.test.parking.domain.parkinglot.ParkingSlot;
import com.test.parking.domain.parkinglot.ParkingSystem;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@Data
@RequiredArgsConstructor
public class DisplayHelper {

    static {
        PrinterUtil.printTitle("WELCOME TO PARKING LOT");
    }

    public void displayVacantSlots(ParkingSystem parkingSystem){
        Map<String, String> toDisplay = new HashMap<>();

        parkingSystem.getParkingSlotListMap().keySet().forEach(parkingSlotType ->  {
            var allSlots = parkingSystem.getParkingSlotListMap().get(parkingSlotType);
            var vacantCount = allSlots.stream().filter(ParkingSlot::isVacant).count();
            var occupiedCount = allSlots.size() - vacantCount;

            toDisplay.put(parkingSlotType.toString(), String.format("%s | %s | %s", allSlots.size(), occupiedCount, vacantCount));
        });

        PrinterUtil.printInfo("CATEGORY : ( ALL | OCCUPIED | VACANT)");
        PrinterUtil.printPrompt(toDisplay.toString(), 5);
    }

}
