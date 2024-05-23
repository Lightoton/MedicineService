package com.rangers.medicineservice.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code DateTimeFormat} class provides utility methods for formatting {@link LocalDateTime} objects.
 * <p>
 * This class currently offers a single static method to format a {@link LocalDateTime} instance
 * into a string representation with the pattern {@code "dd.MM.yyyy HH:mm"}.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * LocalDateTime now = LocalDateTime.now();
 * String formattedDate = DateTimeFormat.formatLocalDateTime(now);
 * System.out.println(formattedDate); // Outputs: 22.05.2024 15:30
 * }
 * </pre>
 * </p>
 *
 * @see LocalDateTime
 * @see DateTimeFormatter
 *
 * @author Volha Zadziarkouskaya
 */
public class DateTimeFormat {

    /**
     * Formats a {@link LocalDateTime} object into a string representation with the pattern {@code "dd.MM.yyyy HH:mm"}.
     *
     * @param localDateTime the {@link LocalDateTime} instance to format
     * @return a string representation of the {@link LocalDateTime} in the format {@code "dd.MM.yyyy HH:mm"}
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return localDateTime.format(formatter);
    }
}

