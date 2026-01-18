package org.jdatepicker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LocalDateModel using JUnit 5.
 */
@DisplayName("LocalDateModel Tests")
class LocalDateModelTest {

    private LocalDateModel model;

    @BeforeEach
    void setUp() {
        model = new LocalDateModel();
    }

    @Test
    @DisplayName("Constructor with null creates unselected model")
    void testConstructorWithNull() {
        LocalDateModel nullModel = new LocalDateModel(null);
        assertNull(nullModel.getValue());
        assertFalse(nullModel.isSelected());
    }

    @Test
    @DisplayName("Constructor with LocalDate sets value correctly, and selected is true")
    void testConstructorWithLocalDate() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        LocalDateModel dateModel = new LocalDateModel(date);

        assertNotNull(dateModel.getValue());
        assertEquals(date, dateModel.getValue());
        assertTrue(dateModel.isSelected());
    }

    @Test
    @DisplayName("setValue updates the date value")
    void testSetValue() {
        LocalDate date = LocalDate.of(2024, 6, 15);
        model.setValue(date);

        assertEquals(date, model.getValue());
    }

    @Test
    @DisplayName("LocalDate is immutable - original unchanged")
    void testLocalDateImmutability() {
        LocalDate original = LocalDate.of(2024, 1, 15);
        model.setValue(original);

        LocalDate retrieved = model.getValue();
        assertNotSame(original, retrieved, "LocalDate should not be the same instance");
        assertEquals(original, retrieved, "LocalDate values should be equal");
    }

    @Test
    @DisplayName("setYear updates year correctly")
    void testSetYear() {
        model.setValue(LocalDate.of(2023, 6, 15)); // June 15, 2023
        model.setYear(2024);

        assertEquals(2024, model.getYear());
        assertEquals(5, model.getMonth()); // June is month index 5 (0-indexed)
        assertEquals(15, model.getDay());
    }

    @Test
    @DisplayName("setMonth updates month correctly with 0-indexed value")
    void testSetMonth() {
        model.setValue(LocalDate.of(2024, 1, 15));
        model.setMonth(5); // June (0-indexed)

        assertEquals(2024, model.getYear());
        assertEquals(5, model.getMonth()); // Returns 0-indexed
        assertEquals(15, model.getDay());

        // Verify the LocalDate has the correct month (1-indexed)
        assertEquals(6, model.getValue().getMonthValue());
    }

    @Test
    @DisplayName("setDay updates day correctly")
    void testSetDay() {
        model.setValue(LocalDate.of(2024, 1, 10));
        model.setDay(20);

        assertEquals(20, model.getDay());
        assertEquals(LocalDate.of(2024, 1, 20), model.getValue());
    }

    @Test
    @DisplayName("setDate updates all components")
    void testSetDate() {
        model.setDate(2024, 5, 15); // June 15, 2024 (0-indexed month)

        assertEquals(2024, model.getYear());
        assertEquals(5, model.getMonth());
        assertEquals(15, model.getDay());
        assertEquals(LocalDate.of(2024, 6, 15), model.getValue());
    }

    @Test
    @DisplayName("addYear increments year")
    void testAddYear() {
        model.setValue(LocalDate.of(2024, 6, 15)); // June 15, 2024
        model.addYear(1);

        assertEquals(2025, model.getYear());
        assertEquals(5, model.getMonth()); // June is month index 5
        assertEquals(LocalDate.of(2025, 6, 15), model.getValue());
    }

    @Test
    @DisplayName("addYear with negative value decrements year")
    void testAddYearNegative() {
        model.setValue(LocalDate.of(2024, 6, 15)); // June 15, 2024
        model.addYear(-1);

        assertEquals(2023, model.getYear());
        assertEquals(5, model.getMonth()); // June is month index 5
        assertEquals(LocalDate.of(2023, 6, 15), model.getValue());
    }

    @Test
    @DisplayName("addMonth increments month")
    void testAddMonth() {
        model.setDate(2024, 5, 15); // June 15
        model.addMonth(1);

        assertEquals(6, model.getMonth()); // July (0-indexed)
        assertEquals(LocalDate.of(2024, 7, 15), model.getValue());
    }

    @Test
    @DisplayName("addMonth wraps to next year")
    void testAddMonthWrapsYear() {
        model.setDate(2024, 11, 15); // December 15
        model.addMonth(1);

        assertEquals(0, model.getMonth()); // January (0-indexed)
        assertEquals(2025, model.getYear());
        assertEquals(LocalDate.of(2025, 1, 15), model.getValue());
    }

    @Test
    @DisplayName("addMonth with negative value wraps to previous year")
    void testAddMonthNegativeWrapsYear() {
        model.setDate(2024, 0, 15); // January 15
        model.addMonth(-1);

        assertEquals(11, model.getMonth()); // December (0-indexed)
        assertEquals(2023, model.getYear());
        assertEquals(LocalDate.of(2023, 12, 15), model.getValue());
    }

    @Test
    @DisplayName("addDay increments day")
    void testAddDay() {
        model.setDate(2024, 5, 15);
        model.addDay(1);

        assertEquals(16, model.getDay());
        assertEquals(LocalDate.of(2024, 6, 16), model.getValue());
    }

    @Test
    @DisplayName("addDay wraps to next month")
    void testAddDayWrapsMonth() {
        model.setDate(2024, 5, 30); // June 30
        model.addDay(1);

        assertEquals(1, model.getDay());
        assertEquals(6, model.getMonth()); // July (0-indexed)
        assertEquals(LocalDate.of(2024, 7, 1), model.getValue());
    }

    @Test
    @DisplayName("setSelected updates selected state")
    void testSetSelected() {
        model.setValue(LocalDate.of(2024, 1, 15));

        assertFalse(model.isSelected());
        model.setSelected(true);
        assertTrue(model.isSelected());
        model.setSelected(false);
        assertFalse(model.isSelected());
    }

    @Test
    @DisplayName("ChangeListener is notified on date change")
    void testChangeListenerNotification() {
        model.setValue(LocalDate.of(2024, 1, 15));
        AtomicBoolean notified = new AtomicBoolean(false);

        model.addChangeListener(e -> notified.set(true));
        model.setYear(2025);

        assertTrue(notified.get());
    }

    @Test
    @DisplayName("equals returns true for models with same LocalDate")
    void testEquals() {
        LocalDate date = LocalDate.of(2024, 6, 15);
        LocalDateModel model1 = new LocalDateModel(date);
        LocalDateModel model2 = new LocalDateModel(date);

        assertEquals(model1, model2);
    }

    @Test
    @DisplayName("equals returns false for models with different LocalDates")
    void testNotEquals() {
        LocalDateModel model1 = new LocalDateModel(LocalDate.of(2024, 1, 15));
        LocalDateModel model2 = new LocalDateModel(LocalDate.of(2024, 1, 16));

        assertNotEquals(model1, model2);
    }

    @Test
    @DisplayName("equals considers selected state")
    void testEqualsConsidersSelectedState() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        LocalDateModel model1 = new LocalDateModel(date);
        LocalDateModel model2 = new LocalDateModel(date);

        model1.setSelected(true);
        model2.setSelected(false);

        assertNotEquals(model1, model2);
    }

    @Test
    @DisplayName("hashCode is consistent with equals")
    void testHashCode() {
        LocalDate date = LocalDate.of(2024, 6, 15);
        LocalDateModel model1 = new LocalDateModel(date);
        LocalDateModel model2 = new LocalDateModel(date);

        assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    @DisplayName("toString provides meaningful output")
    void testToString() {
        LocalDate date = LocalDate.of(2024, 6, 15);
        LocalDateModel model = new LocalDateModel(date);
        model.setSelected(true);

        String result = model.toString();

        assertTrue(result.contains("2024-06-15"));
        assertTrue(result.contains("selected=true"));
    }

    @Test
    @DisplayName("Month indexing matches Calendar convention")
    void testMonthIndexing() {
        // January should be 0, December should be 11
        model.setDate(2024, 0, 15); // January
        assertEquals(1, model.getValue().getMonthValue()); // LocalDate uses 1-indexed

        model.setDate(2024, 11, 15); // December
        assertEquals(12, model.getValue().getMonthValue());
    }

    @Test
    @DisplayName("Handles leap year correctly")
    void testLeapYear() {
        model.setDate(2024, 1, 29); // Feb 29, 2024 (leap year)

        assertEquals(29, model.getDay());
        assertEquals(LocalDate.of(2024, 2, 29), model.getValue());
    }
}
