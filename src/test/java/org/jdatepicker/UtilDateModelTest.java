package org.jdatepicker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UtilDateModel using JUnit 5.
 */
@DisplayName("UtilDateModel Tests")
class UtilDateModelTest {

    private UtilDateModel model;

    @BeforeEach
    void setUp() {
        model = new UtilDateModel();
    }

    @Test
    @DisplayName("Constructor with null creates unselected model")
    void testConstructorWithNull() {
        UtilDateModel nullModel = new UtilDateModel(null);
        assertNull(nullModel.getValue());
        assertFalse(nullModel.isSelected());
    }

    @Test
    @DisplayName("Constructor with date sets value correctly")
    void testConstructorWithDate() {
        Date date = new Date();
        UtilDateModel dateModel = new UtilDateModel(date);

        assertNotNull(dateModel.getValue());
        assertEquals(date, dateModel.getValue());
    }

    @Test
    @DisplayName("setValue updates the date value")
    void testSetValue() {
        Date date = new Date();
        model.setValue(date);

        assertEquals(date, model.getValue());
    }

    @Test
    @DisplayName("setYear updates year correctly")
    void testSetYear() {
        model.setValue(new Date());
        model.setYear(2024);

        assertEquals(2024, model.getYear());
    }

    @Test
    @DisplayName("setMonth updates month correctly")
    void testSetMonth() {
        model.setValue(new Date());
        model.setMonth(5); // June (0-indexed)

        assertEquals(5, model.getMonth());
    }

    @Test
    @DisplayName("setDay updates day correctly")
    void testSetDay() {
        model.setValue(new Date());
        model.setDay(15);

        assertEquals(15, model.getDay());
    }

    @Test
    @DisplayName("setDate updates all components")
    void testSetDate() {
        model.setDate(2024, 5, 15);

        assertEquals(2024, model.getYear());
        assertEquals(5, model.getMonth());
        assertEquals(15, model.getDay());
    }

    @Test
    @DisplayName("addYear increments year")
    void testAddYear() {
        model.setValue(new Date());
        int originalYear = model.getYear();
        model.addYear(1);

        assertEquals(originalYear + 1, model.getYear());
    }

    @Test
    @DisplayName("addYear decrements year with negative value")
    void testAddYearNegative() {
        model.setValue(new Date());
        int originalYear = model.getYear();
        model.addYear(-1);

        assertEquals(originalYear - 1, model.getYear());
    }

    @Test
    @DisplayName("addMonth increments month")
    void testAddMonth() {
        model.setDate(2024, 5, 15);
        model.addMonth(1);

        assertEquals(6, model.getMonth());
    }

    @Test
    @DisplayName("addMonth wraps to next year")
    void testAddMonthWrapsYear() {
        model.setDate(2024, 11, 15); // December
        model.addMonth(1);

        assertEquals(0, model.getMonth()); // January
        assertEquals(2025, model.getYear());
    }

    @Test
    @DisplayName("addDay increments day")
    void testAddDay() {
        model.setDate(2024, 5, 15);
        model.addDay(1);

        assertEquals(16, model.getDay());
    }

    @Test
    @DisplayName("setSelected updates selected state")
    void testSetSelected() {
        model.setValue(new Date());

        assertFalse(model.isSelected());
        model.setSelected(true);
        assertTrue(model.isSelected());
        model.setSelected(false);
        assertFalse(model.isSelected());
    }

    @Test
    @DisplayName("ChangeListener is notified on year change")
    void testChangeListenerOnYearChange() {
        model.setValue(new Date());
        AtomicBoolean notified = new AtomicBoolean(false);

        model.addChangeListener(e -> notified.set(true));
        model.setYear(2025);

        assertTrue(notified.get());
    }

    @Test
    @DisplayName("ChangeListener is notified on month change")
    void testChangeListenerOnMonthChange() {
        model.setValue(new Date());
        AtomicBoolean notified = new AtomicBoolean(false);

        model.addChangeListener(e -> notified.set(true));
        model.setMonth(6);

        assertTrue(notified.get());
    }

    @Test
    @DisplayName("ChangeListener is notified on day change")
    void testChangeListenerOnDayChange() {
        model.setValue(new Date());
        AtomicBoolean notified = new AtomicBoolean(false);

        model.addChangeListener(e -> notified.set(true));
        model.setDay(20);

        assertTrue(notified.get());
    }

    @Test
    @DisplayName("PropertyChangeListener is notified on value change")
    void testPropertyChangeListenerOnValueChange() {
        AtomicReference<PropertyChangeEvent> event = new AtomicReference<>();

        model.addPropertyChangeListener(e -> event.set(e));
        model.setValue(new Date());

        assertNotNull(event.get());
        assertEquals(AbstractDateModel.PROPERTY_VALUE, event.get().getPropertyName());
    }

    @Test
    @DisplayName("removeChangeListener stops notifications")
    void testRemoveChangeListener() {
        model.setValue(new Date());
        AtomicBoolean notified = new AtomicBoolean(false);
        ChangeListener listener = e -> notified.set(true);

        model.addChangeListener(listener);
        model.removeChangeListener(listener);
        model.setYear(2025);

        assertFalse(notified.get());
    }

    @Test
    @DisplayName("equals returns true for models with same value")
    void testEquals() {
        Date date = new Date();
        UtilDateModel model1 = new UtilDateModel(date);
        UtilDateModel model2 = new UtilDateModel(date);

        assertEquals(model1, model2);
    }

    @Test
    @DisplayName("equals returns false for models with different values")
    void testNotEquals() {
        UtilDateModel model1 = new UtilDateModel(new Date(1000));
        UtilDateModel model2 = new UtilDateModel(new Date(2000));

        assertNotEquals(model1, model2);
    }

    @Test
    @DisplayName("equals considers selected state")
    void testEqualsConsidersSelectedState() {
        Date date = new Date();
        UtilDateModel model1 = new UtilDateModel(date);
        UtilDateModel model2 = new UtilDateModel(date);

        model1.setSelected(true);
        model2.setSelected(false);

        assertNotEquals(model1, model2);
    }

    @Test
    @DisplayName("hashCode is consistent with equals")
    void testHashCode() {
        Date date = new Date();
        UtilDateModel model1 = new UtilDateModel(date);
        UtilDateModel model2 = new UtilDateModel(date);

        assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    @DisplayName("equals handles null comparison")
    void testEqualsWithNull() {
        assertNotEquals(null, model);
    }

    @Test
    @DisplayName("equals is reflexive")
    void testEqualsReflexive() {
        assertEquals(model, model);
    }

    @Test
    @DisplayName("Models with null values are equal")
    void testEqualsWithNullValues() {
        UtilDateModel model1 = new UtilDateModel();
        UtilDateModel model2 = new UtilDateModel();

        assertEquals(model1, model2);
    }
}
