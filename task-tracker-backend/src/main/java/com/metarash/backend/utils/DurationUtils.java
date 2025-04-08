package com.metarash.backend.utils;

import java.time.DateTimeException;
import java.time.Duration;

public final class DurationUtils {
    public static final Duration parseDuration(String durationStr) {
        if (durationStr == null || durationStr.isEmpty()) {
            return Duration.ofHours(1);
        }

        try {
            char unit = durationStr.charAt(durationStr.length() - 1);
            long value = Long.parseLong(durationStr.substring(0, durationStr.length() - 1));

            return switch (Character.toLowerCase(unit)) {
                case 'd' -> Duration.ofDays(value);
                case 'h' -> Duration.ofHours(value);
                case 'm' -> Duration.ofMinutes(value);
                case 's' -> Duration.ofSeconds(value);
                default -> Duration.parse(durationStr); // Для стандартных форматов (PT15M)
            };
        } catch (NumberFormatException | DateTimeException e) {
            System.out.println("Invalid duration format: " + durationStr);
            return Duration.ofHours(1);
        }
    }
}
