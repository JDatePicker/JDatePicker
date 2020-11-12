package org.jdatepicker;

/**
 *  Created 12 November 2020
 *  Updated 12 November 2020
 *
 * @param <T> The type of this model (e.g. java.time.LocalDateTime, java.util.Calendar)
 * @author Marc Jakobi
 */
public interface DateTimeModel<T> extends DateModel<T>, TimeModel<T> {

    /**
     * @param hour between 0 and 23
     */
    DateTimeModel<T> setHour(int hour);

    /**
     * @param minute between 0 and 59
     */
    DateTimeModel<T> setMinute(int minute);

    /**
     * @param second between 0 and 59
     */
    DateTimeModel<T> setSecond(int second);

    /**
     * @param nanoSecond the nanosecond
     */
    DateTimeModel<T> setNanoSecond(int nanoSecond);

    /**
     * Adds the given number of hours.
     * @param numberOfHours the number of hours to add
     */
    DateTimeModel<T> addHours(int numberOfHours);

    /**
     * Adds the given number of minutes.
     * @param numberOfMinutes the number of minutes to add
     */
    DateTimeModel<T> addMinutes(int numberOfMinutes);

    /**
     * Adds the given number of seconds.
     * @param numberOfSeconds the number of seconds to add
     */
    DateTimeModel<T> addSeconds(int numberOfSeconds);

    /**
     * Adds the given number of nanoseconds.
     * @param numberOfNanoSeconds the number of nanoseconds to add
     */
    DateTimeModel<T> addNanoSeconds(int numberOfNanoSeconds);

}
