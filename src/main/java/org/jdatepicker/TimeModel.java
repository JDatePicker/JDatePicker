package org.jdatepicker;

/**
 *  Created 12 November 2020
 *  Updated 12 November 2020
 *
 * @param <T> The type of this model (e.g. java.time.LocalTime, java.util.Calendar)
 * @author Marc Jakobi
 */
public interface TimeModel<T> extends Model<T> {

    /**
     * @return the hour (between 0 and 23)
     */
    int getHour();

    /**
     * @param hour between 0 and 23
     */
    TimeModel<T> setHour(int hour);

    /**
     * @return the minute (between 0 and 59)
     */
    int getMinute();

    /**
     * @param minute between 0 and 59
     */
    TimeModel<T> setMinute(int minute);

    /**
     * @return the second (between 0 and 59)
     */
    int getSecond();

    /**
     * @param second between 0 and 59
     */
    TimeModel<T> setSecond(int second);

    /**
     * @return the nanosecond
     */
    int getNanoSecond();

    /**
     * @param nanoSecond the nanosecond
     */
    TimeModel<T> setNanoSecond(int nanoSecond);

    /**
     * Adds the given number of hours.
     * @param numberOfHours the number of hours to add
     */
    TimeModel<T> addHours(int numberOfHours);

    /**
     * Adds the given number of minutes.
     * @param numberOfMinutes the number of minutes to add
     */
    TimeModel<T> addMinutes(int numberOfMinutes);

    /**
     * Adds the given number of seconds.
     * @param numberOfSeconds the number of seconds to add
     */
    TimeModel<T> addSeconds(int numberOfSeconds);

    /**
     * Adds the given number of nanoseconds.
     * @param numberOfNanoSeconds the number of nanoseconds to add
     */
    TimeModel<T> addNanoSeconds(int numberOfNanoSeconds);
}
