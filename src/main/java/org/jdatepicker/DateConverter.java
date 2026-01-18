package org.jdatepicker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class for converting between legacy date/time types and modern java.time API.
 *
 * <p>This class provides bidirectional conversion methods for migrating between:
 * <ul>
 *   <li>{@link java.util.Date} ↔ {@link LocalDate}</li>
 *   <li>{@link java.util.Calendar} ↔ {@link LocalDate}</li>
 *   <li>{@link java.sql.Date} ↔ {@link LocalDate}</li>
 * </ul>
 *
 * <p><b>Time Zone Handling:</b> Conversions use the system default time zone. When converting
 * from legacy types that include time information, the time component is discarded to produce
 * a LocalDate (date-only).
 *
 * <p><b>Thread Safety:</b> This class is thread-safe. All methods are static and operate on
 * immutable or isolated objects.
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * // Legacy to modern
 * Date utilDate = new Date();
 * LocalDate localDate = DateConverter.toLocalDate(utilDate);
 *
 * Calendar calendar = Calendar.getInstance();
 * LocalDate fromCalendar = DateConverter.toLocalDate(calendar);
 *
 * // Modern to legacy
 * LocalDate today = LocalDate.now();
 * Date backToDate = DateConverter.toUtilDate(today);
 * Calendar backToCalendar = DateConverter.toCalendar(today);
 * </pre>
 *
 * @author Claude
 * @since 2.0
 */
public final class DateConverter {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private DateConverter() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== TO LocalDate ====================

    /**
     * Converts a {@link java.util.Date} to a {@link LocalDate}.
     * Time information is discarded.
     *
     * @param date the Date to convert, or null
     * @return the corresponding LocalDate, or null if input is null
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * Converts a {@link java.util.Calendar} to a {@link LocalDate}.
     * Time information is discarded.
     *
     * @param calendar the Calendar to convert, or null
     * @return the corresponding LocalDate, or null if input is null
     */
    public static LocalDate toLocalDate(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return LocalDate.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, // Calendar is 0-indexed, LocalDate is 1-indexed
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    /**
     * Converts a {@link java.sql.Date} to a {@link LocalDate}.
     *
     * @param sqlDate the SQL Date to convert, or null
     * @return the corresponding LocalDate, or null if input is null
     */
    public static LocalDate toLocalDate(java.sql.Date sqlDate) {
        if (sqlDate == null) {
            return null;
        }
        return sqlDate.toLocalDate();
    }

    // ==================== FROM LocalDate ====================

    /**
     * Converts a {@link LocalDate} to a {@link java.util.Date}.
     * The time is set to midnight in the system default time zone.
     *
     * @param localDate the LocalDate to convert, or null
     * @return the corresponding Date at midnight, or null if input is null
     */
    public static Date toUtilDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Converts a {@link LocalDate} to a {@link java.util.Calendar}.
     * The time is set to midnight (00:00:00.000).
     *
     * @param localDate the LocalDate to convert, or null
     * @return the corresponding Calendar at midnight, or null if input is null
     */
    public static Calendar toCalendar(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Converts a {@link LocalDate} to a {@link java.sql.Date}.
     *
     * @param localDate the LocalDate to convert, or null
     * @return the corresponding SQL Date, or null if input is null
     */
    public static java.sql.Date toSqlDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return java.sql.Date.valueOf(localDate);
    }

    // ==================== LocalDateTime Support ====================

    /**
     * Converts a {@link java.util.Date} to a {@link LocalDateTime}.
     * Preserves time information.
     *
     * @param date the Date to convert, or null
     * @return the corresponding LocalDateTime, or null if input is null
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Converts a {@link java.util.Calendar} to a {@link LocalDateTime}.
     * Preserves time information.
     *
     * @param calendar the Calendar to convert, or null
     * @return the corresponding LocalDateTime, or null if input is null
     */
    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Converts a {@link LocalDateTime} to a {@link java.util.Date}.
     * Preserves time information.
     *
     * @param localDateTime the LocalDateTime to convert, or null
     * @return the corresponding Date, or null if input is null
     */
    public static Date toUtilDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Converts a {@link LocalDateTime} to a {@link java.util.Calendar}.
     * Preserves time information.
     *
     * @param localDateTime the LocalDateTime to convert, or null
     * @return the corresponding Calendar, or null if input is null
     */
    public static Calendar toCalendar(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toUtilDate(localDateTime));
        return calendar;
    }
}
