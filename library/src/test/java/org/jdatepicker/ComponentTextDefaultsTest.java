package org.jdatepicker;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ComponentTextDefaults.
 * 
 * Addresses issue #100: The text for the wrong month can be returned in certain cases.
 */
public class ComponentTextDefaultsTest {

    @Test
    void testMonthTextRolloverBug() {
        // This test reproduces the bug where asking for a month's text on the 31st
        // of another month can return the wrong month name.
        
        // Simulate the bug: Create a calendar on Jan 31
        Calendar testCal = Calendar.getInstance();
        testCal.set(2024, Calendar.JANUARY, 31);
        
        // Now set the month to February WITHOUT setting the day
        // This is what the buggy code does
        testCal.set(Calendar.MONTH, Calendar.FEBRUARY);
        
        // The calendar will roll over to March because Feb 31 doesn't exist
        // So we expect this to be March (demonstrating the bug exists)
        int actualMonth = testCal.get(Calendar.MONTH);
        
        // This assertion shows the bug - we set FEBRUARY but got MARCH
        assertEquals(Calendar.MARCH, actualMonth, 
            "Bug demonstration: Setting month to Feb on Jan 31 rolls over to March");
    }
    
    @Test
    void testMonthTextRolloverFix() {
        // This test shows the fix: set DAY_OF_MONTH to 1 before setting the month
        
        Calendar testCal = Calendar.getInstance();
        testCal.set(2024, Calendar.JANUARY, 31);
        
        // The fix: set day to 1 first
        testCal.set(Calendar.DAY_OF_MONTH, 1);
        testCal.set(Calendar.MONTH, Calendar.FEBRUARY);
        
        int actualMonth = testCal.get(Calendar.MONTH);
        
        // Now it works correctly
        assertEquals(Calendar.FEBRUARY, actualMonth,
            "Fix: Setting day to 1 before setting month prevents rollover");
    }
    
    @Test
    void testGetMonthTextForAllMonths() {
        // Test that we can get text for all 12 months without errors
        // This would fail on the 31st of any month for months with < 31 days
        
        ComponentTextDefaults defaults = ComponentTextDefaults.getInstance(Locale.US);
        
        for (int month = 0; month < 12; month++) {
            ComponentTextDefaults.Key key = ComponentTextDefaults.Key.getMonthKey(month);
            String monthText = defaults.getText(key);
            
            assertNotNull(monthText, 
                "Should get text for month " + month);
            assertFalse(monthText.isEmpty(), 
                "Month text should not be empty for month " + month);
        }
    }
    
    @Test
    void testMonthTextConsistency() {
        // This test verifies that the month text returned is actually for the correct month
        // by checking it matches what we'd expect from a properly constructed Calendar
        
        ComponentTextDefaults defaults = ComponentTextDefaults.getInstance(Locale.US);
        
        // Test a few specific months that are prone to the rollover bug
        int[] testMonths = {
            Calendar.FEBRUARY,   // 28/29 days
            Calendar.APRIL,      // 30 days  
            Calendar.JUNE,       // 30 days
            Calendar.SEPTEMBER,  // 30 days
            Calendar.NOVEMBER    // 30 days
        };
        
        for (int month : testMonths) {
            ComponentTextDefaults.Key key = ComponentTextDefaults.Key.getMonthKey(month);
            String actualText = defaults.getText(key);
            
            // Create a proper calendar for comparison (with day=1 to avoid rollover)
            Calendar expected = Calendar.getInstance();
            expected.set(Calendar.DAY_OF_MONTH, 1);
            expected.set(Calendar.MONTH, month);
            
            // Get the expected month name
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM", Locale.US);
            String expectedText = sdf.format(expected.getTime());
            
            assertEquals(expectedText, actualText,
                "Month text should match for month " + month);
        }
    }
}
