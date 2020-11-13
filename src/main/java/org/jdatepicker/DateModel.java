/**
 Copyright 2004 Juan Heyns. All rights reserved.

 Redistribution and use in source and binary forms, with or without modification, are
 permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this list of
 conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice, this list
 of conditions and the following disclaimer in the documentation and/or other materials
 provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY JUAN HEYNS ``AS IS'' AND ANY EXPRESS OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JUAN HEYNS OR
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those of the
 authors and should not be interpreted as representing official policies, either expressed
 or implied, of Juan Heyns.
 */
package org.jdatepicker;

/**
 * Created 18 April 2010
 * Updated 26 April 2010
 * Updated 12 November 2020
 *
 * @param <T> The type of this model (e.g. java.util.Date, java.util.Calendar)
 * @author Juan Heyns
 */
public interface DateModel<T> extends Model<T> {

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @return year
     */
    int getYear();

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @param year year
     */
    void setYear(int year);

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @return month
     */
    int getMonth();

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @param month month
     */
    void setMonth(int month);

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @return day
     */
    int getDay();

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @param day day
     */
    void setDay(int day);

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @param year  year
     * @param month month
     * @param day   day
     */
    void setDate(int year, int month, int day);

    /**
     * Add or substract number of years.
     *
     * @param add years
     */
    void addYear(int add);

    /**
     * Add or substract number of months.
     *
     * @param add months
     */
    void addMonth(int add);

    /**
     * Add or substract number of day.
     *
     * @param add days
     */
    void addDay(int add);

}
