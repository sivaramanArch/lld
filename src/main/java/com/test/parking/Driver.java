package com.test.parking;

import com.test.parking.domain.enums.Command;
import com.test.parking.domain.enums.VehicleType;
import com.test.parking.domain.parkinglot.EntryGate;
import com.test.parking.domain.parkinglot.ParkingSystem;
import com.test.parking.domain.parkinglot.ParkingTicket;
import com.test.parking.domain.vehicle.Vehicle;
import com.test.parking.utils.DisplayHelper;
import com.test.parking.utils.InputValidator;
import com.test.parking.utils.PojoHelper;
import com.test.parking.utils.PrinterUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Driver class for parking lots
 */
public class Driver {

    private static ParkingSystem ps;
    private static final Map<String, ParkingTicket> ticketVehicleMap = new HashMap<>();

    public static void main(String[] args) {
        readCommandAndExecute();
    }

    private static void readCommandAndExecute() {
        String fileName = "command.txt";

        try (InputStream stream = Driver.class.getClassLoader().getResourceAsStream(fileName)) {
            Scanner scanner = new Scanner(stream);
            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                if (InputValidator.isValidInput(inputLine)) {
                    String[] input = inputLine.split(" ");
                    Command cmd;
                    try {
                        cmd = Command.valueOf(input[0]);
                        var arg = input[1];
                        executeCommand(cmd, arg);
                    } catch (Exception e) {
                        PrinterUtil.printError(e.toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeCommand(Command cmd, String arg) {

        switch (cmd){
            case CREATE_PARKING_LOT:
                int size = Integer.parseInt(arg.split(",")[0]);
                int entryGateSize = Integer.parseInt(arg.split(",")[1]);
                int exitGateSize = Integer.parseInt(arg.split(",")[2]);
                ps = getParkingSystem(size, entryGateSize, exitGateSize);
                return;

            case PARK:
                String[] vehicleDetail = arg.split(",");
                Vehicle v = new Vehicle(vehicleDetail[0], VehicleType.valueOf(vehicleDetail[1]));
                EntryGate gate = PojoHelper.getRandomEntryGate();

                var ticket =ps.acceptVehicleEntry(gate.getGateId(), v);
                ticketVehicleMap.put(v.getRegistrationNumber(),ticket);

                PrinterUtil.print("Vehicle parked ... " + ticket.getParkingSlot().getSlotType() + " | " + ticket.getParkingSlot().getId());
                return;

            case STATUS:
                ps.display();
                return;

            case VACATE:
                String vehicleId = arg.split(",")[0];
                var exitTicket = ticketVehicleMap.get(vehicleId);

                var receipt = ps.acceptVehicleExit(PojoHelper.getRandomExitGate().getGateId(), exitTicket);

                PrinterUtil.printPrompt(vehicleId + "is vacated", 3);
                PrinterUtil.printPrompt(receipt.toString(), 5);
                return;

            default:
                PrinterUtil.printError("Provide valid command");
        }
    }

    private static ParkingSystem getParkingSystem(int size, int entryGateCount, int exitGateCount) {
        var slotMap = PojoHelper.getParkingSlotMap(PojoHelper.getSlots(size));
        var entryGateMap = PojoHelper.getEntryGateMap(entryGateCount);
        var exitGateMap = PojoHelper.getExitGateMap(exitGateCount);

        return ParkingSystem.builder()
                .entryGateMap(entryGateMap)
                .exitGateMap(exitGateMap)
                .parkingSlotListMap(slotMap)
                .displayHelper(new DisplayHelper())
                .build();
    }

}
