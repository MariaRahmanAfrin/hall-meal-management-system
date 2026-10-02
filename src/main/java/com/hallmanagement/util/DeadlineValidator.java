package com.hallmanagement.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DeadlineValidator {

    // Cutoff time: 9:00 PM
    private static final LocalTime CUTOFF = LocalTime.of(21, 0);

    public static boolean canSelectForNextDay() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        return currentTime.isBefore(CUTOFF);
    }
}
