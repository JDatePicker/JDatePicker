package org.jdatepicker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UtilCalendarModel using JUnit 5.
 */
@DisplayName("UtilCalendarModel Tests")
class UtilCalendarModelTest {

    private UtilCalendarModel model;

    @BeforeEach
    void setUp() {
        model = new UtilCalendarModel();
    }

    @Test
    @DisplayName("Constructor with null creates unselected model")
    void testConstructorWithNull() {
        UtilCalendarModel nullModel = new UtilCalendarModel(null);
        assertNull(nullModel.getValue());
        assertFalse(nullModel.isSelected());
    }

    @Test
    @DisplayName("Constructor with calendar sets value correctly, and discards time portion")
    void testConstructorWithCalendar() {
        Calendar calendar = Calendar.getInstance();
        UtilCalendarModel calModel = new UtilCalendarModel(calendar);

        assertNotNull(calModel.getValue());
        assertEquals(calendar.get(Calendar.YEAR), calModel.getYear(), "Cloned calendar year should equal original");
        assertEquals(calendar.get(Calendar.MONTH), calModel.getMonth(), "Cloned calendar month should equal original");
        assertEquals(calendar.get(Calendar.DATE), calModel.getDay(), "Cloned calendar date should equal original");
    }

    @Test
    @DisplayName("setValue clones the calendar, and discards the time portion")
    void testSetValueClonesCalendar() {
        Calendar original = Calendar.getInstance();
        model.setValue(original);

        Calendar retrieved = model.getValue();
        assertNotSame(original, retrieved, "Calendar should be cloned, not the same instance");
        assertEquals(original.get(Calendar.YEAR), retrieved.get(Calendar.YEAR), "Cloned calendar year should equal original");
        assertEquals(original.get(Calendar.MONTH), retrieved.get(Calendar.MONTH), "Cloned calendar month should equal original");
        assertEquals(original.get(Calendar.DATE), retrieved.get(Calendar.DATE), "Cloned calendar date should equal original");
    }

    @Test
    @DisplayName("Modifying original calendar doesn't affect model")
    void testOriginalCalendarIsolation() {
        Calendar original = Calendar.getInstance();
        original.set(Calendar.YEAR, 2024);
        model.setValue(original);

        // Modify original
        original.set(Calendar.YEAR, 2025);

        // Model should still have 2024
        assertEquals(2024, model.getYear());
    }

    @Test
    @DisplayName("getValue returns clone not original")
    void testGetValueReturnsClone() {
        Calendar original = Calendar.getInstance();
        model.setValue(original);

        Calendar value1 = model.getValue();
        Calendar value2 = model.getValue();

        assertNotSame(value1, value2, "Each getValue() should return a new clone");
        assertEquals(value1, value2, "Clones should be equal");
    }

    @Test
    @DisplayName("equals returns true for models with same calendar")
    void testEquals() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, 5, 15, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);

        UtilCalendarModel model1 = new UtilCalendarModel(cal);
        UtilCalendarModel model2 = new UtilCalendarModel((Calendar) cal.clone());

        assertEquals(model1, model2);
    }

    @Test
    @DisplayName("equals returns false for models with different calendars")
    void testNotEquals() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2024, 5, 15);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(2024, 5, 16);

        UtilCalendarModel model1 = new UtilCalendarModel(cal1);
        UtilCalendarModel model2 = new UtilCalendarModel(cal2);

        assertNotEquals(model1, model2);
    }

    @Test
    @DisplayName("hashCode is consistent with equals")
    void testHashCode() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, 5, 15, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);

        UtilCalendarModel model1 = new UtilCalendarModel(cal);
        UtilCalendarModel model2 = new UtilCalendarModel((Calendar) cal.clone());

        assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    @DisplayName("setDate updates all calendar fields")
    void testSetDate() {
        model.setDate(2024, 11, 25);

        assertEquals(2024, model.getYear());
        assertEquals(11, model.getMonth());
        assertEquals(25, model.getDay());
    }

    @Test
    @DisplayName("addMonth wraps correctly at year boundary")
    void testAddMonthYearWrap() {
        model.setDate(2024, 11, 15); // December
        model.addMonth(2);

        assertEquals(2025, model.getYear());
        assertEquals(1, model.getMonth()); // February
    }

    @Test
    @DisplayName("addDay handles month transitions")
    void testAddDayMonthTransition() {
        model.setDate(2024, 5, 30); // June 30
        model.addDay(1);

        assertEquals(2024, model.getYear());
        assertEquals(6, model.getMonth()); // July
        assertEquals(1, model.getDay());
    }
}
