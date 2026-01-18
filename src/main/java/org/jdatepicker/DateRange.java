package org.jdatepicker;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Immutable value object representing an inclusive date range.
 *
 * <p>This record represents a range between two dates (inclusive on both ends).
 * It provides utility methods for checking date containment and calculating range properties.
 *
 * <p><b>Immutability:</b> As a record, DateRange is inherently immutable and thread-safe.
 *
 * <p><b>Validation:</b> The compact constructor ensures that startDate is not after endDate.
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * // Create a date range for January 2024
 * LocalDate start = LocalDate.of(2024, 1, 1);
 * LocalDate end = LocalDate.of(2024, 1, 31);
 * DateRange january = new DateRange(start, end);
 *
 * // Check if a date falls within the range
 * LocalDate date = LocalDate.of(2024, 1, 15);
 * boolean isInRange = january.contains(date); // true
 *
 * // Get the number of days in the range
 * long days = january.lengthInDays(); // 31
 *
 * // Factory methods
 * DateRange thisMonth = DateRange.ofMonth(2024, 1);
 * DateRange thisYear = DateRange.ofYear(2024);
 * </pre>
 *
 * @param startDate the start date of the range (inclusive), must not be null
 * @param endDate   the end date of the range (inclusive), must not be null
 * @author Claude
 * @since 2.0
 */
public record DateRange(LocalDate startDate, LocalDate endDate) {

    /**
     * Compact constructor with validation.
     *
     * @throws NullPointerException     if either date is null
     * @throws IllegalArgumentException if startDate is after endDate
     */
    public DateRange {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    String.format("startDate (%s) must not be after endDate (%s)",
                            startDate, endDate));
        }
    }

    /**
     * Creates a DateRange for the entire specified month.
     *
     * @param year  the year
     * @param month the month (1-indexed: 1=January, 12=December)
     * @return a DateRange covering the entire month
     */
    public static DateRange ofMonth(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return new DateRange(start, end);
    }

    /**
     * Creates a DateRange for the entire specified year.
     *
     * @param year the year
     * @return a DateRange covering the entire year
     */
    public static DateRange ofYear(int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        return new DateRange(start, end);
    }

    /**
     * Creates a DateRange from a start date with a specified number of days.
     *
     * @param startDate the start date
     * @param days      the number of days in the range (must be positive)
     * @return a DateRange covering the specified number of days
     * @throws IllegalArgumentException if days is less than 1
     */
    public static DateRange ofDays(LocalDate startDate, long days) {
        if (days < 1) {
            throw new IllegalArgumentException("days must be at least 1");
        }
        return new DateRange(startDate, startDate.plusDays(days - 1));
    }

    /**
     * Checks if the given date falls within this range (inclusive).
     *
     * @param date the date to check
     * @return true if the date is within the range, false otherwise
     */
    public boolean contains(LocalDate date) {
        if (date == null) {
            return false;
        }
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Checks if this range overlaps with another range.
     *
     * @param other the other range to check
     * @return true if the ranges overlap, false otherwise
     */
    public boolean overlaps(DateRange other) {
        Objects.requireNonNull(other, "other must not be null");
        return !endDate.isBefore(other.startDate) && !other.endDate.isBefore(startDate);
    }

    /**
     * Returns the number of days in this range (inclusive).
     *
     * @return the length of the range in days
     */
    public long lengthInDays() {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 for inclusive
    }

    /**
     * Checks if this range is a single day.
     *
     * @return true if startDate equals endDate
     */
    public boolean isSingleDay() {
        return startDate.equals(endDate);
    }

    /**
     * Returns a new DateRange that is the intersection of this range and another.
     *
     * @param other the other range
     * @return a new DateRange representing the intersection, or null if they don't overlap
     */
    public DateRange intersection(DateRange other) {
        Objects.requireNonNull(other, "other must not be null");
        if (!overlaps(other)) {
            return null;
        }
        LocalDate newStart = startDate.isAfter(other.startDate) ? startDate : other.startDate;
        LocalDate newEnd = endDate.isBefore(other.endDate) ? endDate : other.endDate;
        return new DateRange(newStart, newEnd);
    }

    /**
     * Returns a string representation suitable for display.
     *
     * @return a formatted string representation
     */
    @Override
    public String toString() {
        if (isSingleDay()) {
            return String.format("DateRange[%s]", startDate);
        }
        return String.format("DateRange[%s to %s (%d days)]",
                startDate, endDate, lengthInDays());
    }
}
