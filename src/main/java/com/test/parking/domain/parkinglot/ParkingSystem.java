package com.test.parking.domain.parkinglot;

import com.test.parking.utils.DisplayHelper;
import com.test.parking.domain.enums.ParkingSlotType;
import com.test.parking.domain.vehicle.Vehicle;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Map;

@Data
@Builder
@ToString
public class ParkingSystem {
    @Builder.Default
    String id = RandomStringUtils.randomAlphanumeric(10);
    Map<String, String> metaData; // address , location etc...

    Map<String, EntryGate> entryGateMap;
    Map<String, ExitGate> exitGateMap;
    DisplayHelper displayHelper;
    Map<ParkingSlotType, List<ParkingSlot>> parkingSlotListMap;

    public ParkingTicket acceptVehicleEntry(String entryGateId, Vehicle vehicle) {
        var entryGate = entryGateMap.get(entryGateId);
        return entryGate.accept(vehicle, parkingSlotListMap);
    }

    public ParkingReceipt acceptVehicleExit(String exitGateId, ParkingTicket ticket) {
        var exitGate = exitGateMap.get(exitGateId);
        var receipt = exitGate.accept(ticket);
        receipt.setParkingStationMetaData(getMetaData());
        return receipt;
    }

    public void display() {
        this.displayHelper.displayVacantSlots(this);
    }

}
