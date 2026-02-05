package org.jdatepicker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for java.time and java.util.Date convenience methods in JDatePicker.
 */
public class JDatePickerJavaTimeTest {

    private JDatePicker datePicker;

    @BeforeEach
    void setUp() {
        datePicker = new JDatePicker();
    }

    @Test
    void testSetAndGetDate() {
        // Create a date: January 15, 2024
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 15, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date testDate = cal.getTime();

        // Set the date
        datePicker.setDate(testDate);

        // Verify it was set
        assertTrue(datePicker.getModel().isSelected());
        assertEquals(2024, datePicker.getModel().getYear());
        assertEquals(Calendar.JANUARY, datePicker.getModel().getMonth());
        assertEquals(15, datePicker.getModel().getDay());

        // Get the date back
        Date retrievedDate = datePicker.getDate();
        assertNotNull(retrievedDate);
        assertEquals(testDate, retrievedDate);
    }

    @Test
    void testSetDateNull() {
        // Set a date first
        datePicker.setDate(new Date());
        assertTrue(datePicker.getModel().isSelected());

        // Set to null
        datePicker.setDate(null);
        assertFalse(datePicker.getModel().isSelected());
        assertNull(datePicker.getDate());
    }

    @Test
    void testGetDateWhenNotSelected() {
        assertFalse(datePicker.getModel().isSelected());
        assertNull(datePicker.getDate());
    }

    @Test
    void testSetAndGetLocalDate() {
        // Create a LocalDate: February 28, 2024
        LocalDate testDate = LocalDate.of(2024, 2, 28);

        // Set the date
        datePicker.setLocalDate(testDate);

        // Verify it was set
        assertTrue(datePicker.getModel().isSelected());
        assertEquals(2024, datePicker.getModel().getYear());
        assertEquals(Calendar.FEBRUARY, datePicker.getModel().getMonth()); // Calendar uses 0-11
        assertEquals(28, datePicker.getModel().getDay());

        // Get the date back
        LocalDate retrievedDate = datePicker.getLocalDate();
        assertNotNull(retrievedDate);
        assertEquals(testDate, retrievedDate);
    }

    @Test
    void testSetLocalDateNull() {
        // Set a date first
        datePicker.setLocalDate(LocalDate.now());
        assertTrue(datePicker.getModel().isSelected());

        // Set to null
        datePicker.setLocalDate(null);
        assertFalse(datePicker.getModel().isSelected());
        assertNull(datePicker.getLocalDate());
    }

    @Test
    void testGetLocalDateWhenNotSelected() {
        assertFalse(datePicker.getModel().isSelected());
        assertNull(datePicker.getLocalDate());
    }

    @Test
    void testLocalDateMonthConversion() {
        // Test that month conversion between LocalDate (1-12) and Calendar (0-11) works correctly
        for (int month = 1; month <= 12; month++) {
            LocalDate testDate = LocalDate.of(2024, month, 15);
            datePicker.setLocalDate(testDate);
            
            assertEquals(month - 1, datePicker.getModel().getMonth(), 
                "Calendar month should be " + (month - 1) + " for LocalDate month " + month);
            
            LocalDate retrieved = datePicker.getLocalDate();
            assertEquals(testDate, retrieved, 
                "Retrieved LocalDate should match original for month " + month);
        }
    }

    @Test
    void testSetAndGetInstant() {
        // Create an Instant
        Instant testInstant = Instant.parse("2024-03-15T12:30:00Z");

        // Set the instant
        datePicker.setInstant(testInstant);

        // Verify the date was set (time component should be stripped)
        assertTrue(datePicker.getModel().isSelected());
        
        // Get as LocalDate to verify the date part
        LocalDate expectedDate = testInstant.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate actualDate = datePicker.getLocalDate();
        assertEquals(expectedDate, actualDate);

        // Get the instant back
        Instant retrievedInstant = datePicker.getInstant();
        assertNotNull(retrievedInstant);
        
        // The time should be set to midnight in system timezone
        assertEquals(expectedDate, retrievedInstant.atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @Test
    void testSetInstantNull() {
        // Set an instant first
        datePicker.setInstant(Instant.now());
        assertTrue(datePicker.getModel().isSelected());

        // Set to null
        datePicker.setInstant(null);
        assertFalse(datePicker.getModel().isSelected());
        assertNull(datePicker.getInstant());
    }

    @Test
    void testGetInstantWhenNotSelected() {
        assertFalse(datePicker.getModel().isSelected());
        assertNull(datePicker.getInstant());
    }

    @Test
    void testConversionBetweenDateAndLocalDate() {
        // Set using Date
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 20, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        
        datePicker.setDate(date);
        
        // Get as LocalDate
        LocalDate localDate = datePicker.getLocalDate();
        assertEquals(2024, localDate.getYear());
        assertEquals(3, localDate.getMonthValue());
        assertEquals(20, localDate.getDayOfMonth());
        
        // Now set using LocalDate
        LocalDate newDate = LocalDate.of(2024, 4, 25);
        datePicker.setLocalDate(newDate);
        
        // Get as Date
        Date retrievedDate = datePicker.getDate();
        Calendar retrievedCal = Calendar.getInstance();
        retrievedCal.setTime(retrievedDate);
        assertEquals(2024, retrievedCal.get(Calendar.YEAR));
        assertEquals(Calendar.APRIL, retrievedCal.get(Calendar.MONTH));
        assertEquals(25, retrievedCal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    void testLeapYearDate() {
        // Test February 29, 2024 (leap year)
        LocalDate leapDay = LocalDate.of(2024, 2, 29);
        datePicker.setLocalDate(leapDay);
        
        assertEquals(leapDay, datePicker.getLocalDate());
        assertEquals(29, datePicker.getModel().getDay());
    }

    @Test
    void testDateRangeBoundaries() {
        // Test minimum and maximum reasonable dates
        LocalDate minDate = LocalDate.of(1900, 1, 1);
        datePicker.setLocalDate(minDate);
        assertEquals(minDate, datePicker.getLocalDate());
        
        LocalDate maxDate = LocalDate.of(2100, 12, 31);
        datePicker.setLocalDate(maxDate);
        assertEquals(maxDate, datePicker.getLocalDate());
    }
}
