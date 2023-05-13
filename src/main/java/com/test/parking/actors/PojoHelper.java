package com.test.parking.actors;

import com.test.parking.domain.EntryGate;
import com.test.parking.domain.ExitGate;
import com.test.parking.domain.ParkingSlot;
import com.test.parking.domain.Vehicle;
import com.test.parking.domain.enums.ParkingSlotType;
import com.test.parking.domain.enums.VehicleType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Helper class that returns a series of vehicles
 */

public class PojoHelper {
    @Getter
    private static List<Vehicle> vehicles;

    private static List<EntryGate> entryGates;
    private static List<ExitGate> exitGates;

    private static final Random random = new Random();


    static  {
        vehicles = List.of(
                new Vehicle("KA-01-0123", VehicleType.CAR),
                new Vehicle("KA-01-0124", VehicleType.TWO_WHEELER),
                new Vehicle("KA-01-0125", VehicleType.CAR),
                new Vehicle("KA-01-0126", VehicleType.LARGE_MOTOR_VEHICLE),
                new Vehicle("KA-01-0127", VehicleType.CAR),
                new Vehicle("KA-01-0128", VehicleType.TWO_WHEELER),
                new Vehicle("KA-01-0129", VehicleType.CAR)
        );

    }

    static Map<ParkingSlotType, List<ParkingSlot>> getParkingSlotMap(List<ParkingSlot> slots) {
        return slots.stream().collect(Collectors.groupingBy(ParkingSlot::getSlotType));
    }

    public static Vehicle getRandomVehicle() {
        int index = random.nextInt(vehicles.size());
        return vehicles.get(index);
    }

    public static EntryGate getRandomEntryGate() {
        int index = random.nextInt(entryGates.size());
        return entryGates.get(index);
    }

    public static ExitGate getRandomExitGate() {
        int index = random.nextInt(exitGates.size());
        return exitGates.get(index);
    }

    public static Map<String, EntryGate> getEntryGateMap(int size){
        return getEntryGates(size).stream().collect(Collectors.toMap(EntryGate::getGateId, eg->eg));
    }

    public static Map<String, ExitGate> getExitGateMap(int size){
        return getExitGates(size).stream().collect(Collectors.toMap(ExitGate::getGateId, eg->eg));
    }

    public static List<EntryGate> getEntryGates(int size) {
        var allGates = new ArrayList<EntryGate>();
        for (int i = 0; i < size; i++) {
            allGates.add(EntryGate.builder().isOperational(true).build());
        }
        entryGates = allGates;
        return allGates;
    }

    public static List<ExitGate> getExitGates(int size) {
        var allGates = new ArrayList<ExitGate>();
        for (int i = 0; i < size; i++) {
            allGates.add(ExitGate.builder().isOperational(true).build());
        }
        exitGates = allGates;
        return allGates;
    }

    public static List<ParkingSlot> getSlots(int size) {
        var allSlots = new ArrayList<ParkingSlot>();

        for (int i = 0; i < size; i++) {
            if(i % 3 == 0){
                allSlots.add(ParkingSlot.builder().slotType(ParkingSlotType.COMPACT).isOperational(true).build());
            }
            if(i % 3 == 1){
                allSlots.add(ParkingSlot.builder().slotType(ParkingSlotType.MEDIUM).isOperational(true).build());
            }
            if(i % 3 == 2){
                allSlots.add(ParkingSlot.builder().slotType(ParkingSlotType.LARGE).isOperational(true).build());
            }
        }
        return allSlots;
    }


}
