package com.test.parking.domain.parkinglot;

import com.test.parking.domain.enums.ParkingSlotType;
import com.test.parking.domain.enums.VehicleType;
import com.test.parking.domain.vehicle.Vehicle;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Data
public class EntryGate {
    @Builder.Default
    String gateId = RandomStringUtils.randomAlphabetic(1);
    Boolean isOperational;

    public ParkingTicket accept(Vehicle vehicle, Map<ParkingSlotType, List<ParkingSlot>> parkingSlotListMap) {
        if(!canOccupy(vehicle, parkingSlotListMap)){
            throw new RuntimeException("PARKING SLOT IS FULL , CHECK DISPLAY");
        }

        // get assigned slot for vehicle
        ParkingSlot parkingSlot = getAssignedSlot(vehicle, parkingSlotListMap);

        // create ticket
        ParkingTicket parkingTicket = ParkingTicket.builder()
                .parkingSlot(parkingSlot)
                .startTime(LocalDateTime.now())
                .isPaid(false)
                .build();

        return parkingTicket;
    }

    private ParkingSlot getAssignedSlot(Vehicle vehicle, Map<ParkingSlotType, List<ParkingSlot>> slotTypeListMap) {
        var allowedSlotForVehicle = getAllowedSlot(vehicle.getVehicleType());

        var allVacantSlots = allowedSlotForVehicle.stream().map(slotTypeListMap::get).flatMap(Collection::stream).filter(ParkingSlot::isVacant).collect(Collectors.toList());

        var assignedSlot = allVacantSlots.get(0);
        assignedSlot.setVehicle(vehicle);
        return assignedSlot;
    }

    private boolean canOccupy(Vehicle v, Map<ParkingSlotType, List<ParkingSlot>> slotTypeListMap){
        var allowedSlot = getAllowedSlot(v.getVehicleType());
        var vacantSlotOptional = allowedSlot.stream().filter(parkingSlotType -> slotTypeListMap.get(parkingSlotType).stream().anyMatch(ParkingSlot::isVacant)).findFirst();
        return vacantSlotOptional.isPresent();
    }

    private List<ParkingSlotType> getAllowedSlot(VehicleType vehicleType) {
        Map<VehicleType, List<ParkingSlotType>> parkingSlotConfig = new HashMap<>();
        parkingSlotConfig.put(VehicleType.CAR, List.of(ParkingSlotType.MEDIUM, ParkingSlotType.COMPACT));
        parkingSlotConfig.put(VehicleType.TWO_WHEELER, List.of(ParkingSlotType.COMPACT));
        parkingSlotConfig.put(VehicleType.LARGE_MOTOR_VEHICLE, List.of(ParkingSlotType.LARGE));

        return parkingSlotConfig.get(vehicleType);
    }
}
