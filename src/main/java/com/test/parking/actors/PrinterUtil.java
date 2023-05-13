package com.test.parking.actors;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PrinterUtil {


    public static void print(String message) {
        System.out.println(message);
    }

    public static void printWarning(String message) {
        System.out.println("[WARNING] " + message);
    }

    public static void printDebug(String message) {
        System.out.println("[DEBUG] " + message);
    }

    public static void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void printError(String message) {
        System.err.println("[ERROR] " + message);
    }

    public static void printPrompt(String message, int numStars) {
        StringBuilder sb = new StringBuilder();
        sb.append("*".repeat(Math.max(0, numStars)));
        sb.append(" ").append(message).append(" ");
        sb.append("*".repeat(Math.max(0, numStars)));
        System.out.println(sb.toString());
    }


    public static void waitAndPrint(int seconds) throws InterruptedException {
        long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds);
        while (System.currentTimeMillis() < endTime) {
            Thread.sleep(100);
            System.out.print("\033[32m.\033[0m"); // Print a green-colored dot
        }
        System.out.println("");
    }


    public static void printTitle(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(Math.max(0, message.length() + 4)));
        System.out.println(sb.toString());
        System.out.println("| " + message + " |");
        System.out.println(sb.toString());
    }


    private static int getMaxLength(String[][] arr) {
        int maxLength = 0;
        for (String[] row : arr) {
            for (String cell : row) {
                 if(cell != null) {
                    maxLength = Math.max(maxLength, cell.length());
                 }
            }
        }
        return maxLength;
    }

    private static String repeat(String str, int n) {
        return new String(new char[n]).replace("\0", str);
    }

    private static String padRight(String str, int n) {
        return String.format("%-" + n + "s", str);
    }

    public static void displayGrid(int[][] grid, List<String> rowNames, List<String> colNames) {
        // Print column names
        System.out.print("  ");
        for (String colName : colNames) {
            System.out.print(colName + " ");
        }
        System.out.println();

        // Print grid contents with row names
        for (int i = 0; i < grid.length; i++) {
            System.out.print(rowNames.get(i) + " ");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
