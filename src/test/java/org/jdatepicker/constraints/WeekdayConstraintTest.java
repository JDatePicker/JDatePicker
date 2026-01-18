package org.jdatepicker.constraints;

import org.jdatepicker.UtilCalendarModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for WeekdayConstraint using JUnit 5.
 */
@DisplayName("WeekdayConstraint Tests")
class WeekdayConstraintTest {

    private WeekdayConstraint constraint;
    private UtilCalendarModel model;

    @BeforeEach
    void setUp() {
        constraint = new WeekdayConstraint();
        model = new UtilCalendarModel();
        model.setSelected(true);
    }

    @Test
    @DisplayName("Monday is valid (weekday)")
    void testMondayIsValid() {
        // January 1, 2024 is a Monday
        model.setDate(2024, 0, 1);
        assertTrue(constraint.isValidSelection(model));
    }

    @Test
    @DisplayName("Tuesday is valid (weekday)")
    void testTuesdayIsValid() {
        // January 2, 2024 is a Tuesday
        model.setDate(2024, 0, 2);
        assertTrue(constraint.isValidSelection(model));
    }

    @Test
    @DisplayName("Wednesday is valid (weekday)")
    void testWednesdayIsValid() {
        // January 3, 2024 is a Wednesday
        model.setDate(2024, 0, 3);
        assertTrue(constraint.isValidSelection(model));
    }

    @Test
    @DisplayName("Thursday is valid (weekday)")
    void testThursdayIsValid() {
        // January 4, 2024 is a Thursday
        model.setDate(2024, 0, 4);
        assertTrue(constraint.isValidSelection(model));
    }

    @Test
    @DisplayName("Friday is valid (weekday)")
    void testFridayIsValid() {
        // January 5, 2024 is a Friday
        model.setDate(2024, 0, 5);
        assertTrue(constraint.isValidSelection(model));
    }

    @Test
    @DisplayName("Saturday is invalid (weekend)")
    void testSaturdayIsInvalid() {
        // January 6, 2024 is a Saturday
        model.setDate(2024, 0, 6);
        assertFalse(constraint.isValidSelection(model));
    }

    @Test
    @DisplayName("Sunday is invalid (weekend)")
    void testSundayIsInvalid() {
        // January 7, 2024 is a Sunday
        model.setDate(2024, 0, 7);
        assertFalse(constraint.isValidSelection(model));
    }

    @Test
    @DisplayName("Multiple weekdays are all valid")
    void testMultipleWeekdays() {
        for (int day = 1; day <= 5; day++) {
            model.setDate(2024, 0, day); // Jan 1-5, 2024 (Mon-Fri)
            assertTrue(constraint.isValidSelection(model),
                    "Day " + day + " should be valid weekday");
        }
    }

    @Test
    @DisplayName("Multiple weekend days are all invalid")
    void testMultipleWeekendDays() {
        model.setDate(2024, 0, 6); // Saturday
        assertFalse(constraint.isValidSelection(model));

        model.setDate(2024, 0, 7); // Sunday
        assertFalse(constraint.isValidSelection(model));
    }
}
