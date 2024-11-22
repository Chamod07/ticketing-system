package com.w2051922.ticketing.util;

public class ValidateUntil {
    public static boolean positiveInteger(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateCapacity(int maxCapacity, int totalTickets) {
        return maxCapacity >= totalTickets;
    }

    public static boolean validateRate(int rate, int totalTickets) {
        return rate > 0 && rate <= totalTickets;
    }
}
