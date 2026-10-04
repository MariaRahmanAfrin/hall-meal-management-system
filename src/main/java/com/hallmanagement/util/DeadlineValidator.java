package com.hallmanagement.util;

import java.time.LocalTime;

public class DeadlineValidator {
    public static boolean canSelectForNextDay() {
        LocalTime now = LocalTime.now();
        LocalTime cutoff = LocalTime.of(21, 0); // 9 PM
        return now.isBefore(cutoff);
    }
}
