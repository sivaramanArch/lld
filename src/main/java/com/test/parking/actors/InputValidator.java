package com.test.parking.actors;

import com.test.parking.domain.Command;

import java.util.Arrays;

public class InputValidator {
    public static boolean isValidInput(String inputLine) {
        String[] splitString = inputLine.split(" ");
        var commandString = splitString[0];
        return Arrays.stream(Command.values()).anyMatch(s-> s.toString().equalsIgnoreCase(commandString));
    }
}
