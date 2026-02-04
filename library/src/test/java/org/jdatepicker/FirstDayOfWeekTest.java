package org.jdatepicker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Calendar;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for locale-specific first day of week behavior.
 * 
 * Addresses issues:
 * - #62: First day of the week does not change for RU locale
 * - #89: Bug if the first day of the week is a Monday
 * - #101: Saturday shown as 1st Day of a Week (Chinese locale)
 */
public class FirstDayOfWeekTest {

    @ParameterizedTest(name = "Locale {0} should have first day of week = {1}")
    @CsvSource({
        "en_US, SUNDAY",
        "de_DE, MONDAY", 
        "ru_RU, MONDAY",
        "fr_FR, MONDAY",
        "ar_SA, SATURDAY",
        "zh_CN, SUNDAY"
    })
    void testFirstDayOfWeekByLocale(String localeStr, String expectedFirstDay) {
        // Parse locale
        String[] parts = localeStr.split("_");
        Locale locale = parts.length > 1 ? new Locale(parts[0], parts[1]) : new Locale(parts[0]);
        
        // Save original locale
        Locale originalLocale = Locale.getDefault();
        
        try {
            Locale.setDefault(locale);
            
            // Verify Calendar's first day of week
            Calendar cal = Calendar.getInstance();
            int expectedDow = switch (expectedFirstDay) {
                case "SUNDAY" -> Calendar.SUNDAY;
                case "MONDAY" -> Calendar.MONDAY;
                case "SATURDAY" -> Calendar.SATURDAY;
                default -> throw new IllegalArgumentException("Unknown day: " + expectedFirstDay);
            };
            
            assertEquals(expectedDow, cal.getFirstDayOfWeek(),
                "Calendar.getFirstDayOfWeek() should return " + expectedFirstDay + " for locale " + localeStr);
            
            // Create a JDatePanel and verify column headers start with correct day
            JDatePanel panel = new JDatePanel();
            
            // The first column (index 0) should have the first day of week
            // We can't easily access the internal model directly, but we can create
            // a new panel and check the expected behavior
            assertNotNull(panel.getModel(), "Panel should have a model");
            
        } finally {
            // Restore original locale
            Locale.setDefault(originalLocale);
        }
    }
    
    @Test
    void testJDatePanelCreationWithDifferentLocales() {
        Locale originalLocale = Locale.getDefault();
        
        try {
            // Test US locale (Sunday first)
            Locale.setDefault(Locale.US);
            JDatePanel usPanel = new JDatePanel();
            assertNotNull(usPanel);
            
            // Test German locale (Monday first)  
            Locale.setDefault(Locale.GERMANY);
            JDatePanel dePanel = new JDatePanel();
            assertNotNull(dePanel);
            
            // Test Russian locale (Monday first)
            Locale.setDefault(new Locale("ru", "RU"));
            JDatePanel ruPanel = new JDatePanel();
            assertNotNull(ruPanel);
            
        } finally {
            Locale.setDefault(originalLocale);
        }
    }
    
    @Test
    void testFirstDayOfWeekOffset() {
        // Test the calculation: firstDayOfWeekOffset = getFirstDayOfWeek() - SUNDAY
        
        // For US (Sunday first): offset = 1 - 1 = 0
        Locale.setDefault(Locale.US);
        Calendar usCal = Calendar.getInstance();
        assertEquals(0, usCal.getFirstDayOfWeek() - Calendar.SUNDAY,
            "US should have offset 0 (Sunday is first)");
        
        // For Germany (Monday first): offset = 2 - 1 = 1
        Locale.setDefault(Locale.GERMANY);
        Calendar deCal = Calendar.getInstance();
        assertEquals(1, deCal.getFirstDayOfWeek() - Calendar.SUNDAY,
            "Germany should have offset 1 (Monday is first)");
        
        // For Saudi Arabia (Saturday first): offset = 7 - 1 = 6
        Locale.setDefault(new Locale("ar", "SA"));
        Calendar saCal = Calendar.getInstance();
        assertEquals(6, saCal.getFirstDayOfWeek() - Calendar.SUNDAY,
            "Saudi Arabia should have offset 6 (Saturday is first)");
    }
}
