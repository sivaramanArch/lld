package com.test.parking.utils;

import com.test.parking.domain.enums.Command;

import java.util.Arrays;

public class InputValidator {
    public static boolean isValidInput(String inputLine) {
        String[] splitString = inputLine.split(" ");
        var commandString = splitString[0];
        return Arrays.stream(Command.values()).anyMatch(s-> s.toString().equalsIgnoreCase(commandString));
    }
}
