package org.jdatepicker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Regression tests for issue #124: setValue must update the selected state.
 */
@DisplayName("Issue #124: setValue updates selected state")
class SetValueSelectedTest {

    private static Calendar calendar(int year, int month, int day) {
        return new GregorianCalendar(year, month, day);
    }

    private static List<PropertyChangeEvent> events(List<PropertyChangeEvent> all, String name) {
        return all.stream().filter(e -> name.equals(e.getPropertyName())).collect(Collectors.toList());
    }

    private static <T> void assertSetValueSelects(AbstractDateModel<T> model, T value) {
        assertFalse(model.isSelected());
        model.setValue(value);
        assertTrue(model.isSelected());
        assertNotNull(model.getValue());
        assertEquals(2024, model.getYear());
        assertEquals(Calendar.MARCH, model.getMonth());
        assertEquals(15, model.getDay());
    }

    private static <T> void assertSetNullDeselects(AbstractDateModel<T> model) {
        assertTrue(model.isSelected());
        model.setValue(null);
        assertFalse(model.isSelected());
        assertNull(model.getValue());
    }

    @Test
    @DisplayName("Issue #124: UtilDateModel.setValue(date) selects an unselected model")
    void utilDateModelSetValueSelects() {
        assertSetValueSelects(new UtilDateModel(), calendar(2024, Calendar.MARCH, 15).getTime());
    }

    @Test
    @DisplayName("Issue #124: UtilDateModel.setValue(null) clears a selected model")
    void utilDateModelSetNullDeselects() {
        assertSetNullDeselects(new UtilDateModel(calendar(2024, Calendar.MARCH, 15).getTime(), true));
    }

    @Test
    @DisplayName("Issue #124: UtilCalendarModel.setValue(calendar) selects an unselected model")
    void utilCalendarModelSetValueSelects() {
        assertSetValueSelects(new UtilCalendarModel(), calendar(2024, Calendar.MARCH, 15));
    }

    @Test
    @DisplayName("Issue #124: UtilCalendarModel.setValue(null) clears a selected model")
    void utilCalendarModelSetNullDeselects() {
        assertSetNullDeselects(new UtilCalendarModel(calendar(2024, Calendar.MARCH, 15), true));
    }

    @Test
    @DisplayName("Issue #124: LocalDateModel.setValue(date) selects an unselected model")
    void localDateModelSetValueSelects() {
        assertSetValueSelects(new LocalDateModel(), LocalDate.of(2024, 3, 15));
    }

    @Test
    @DisplayName("Issue #124: LocalDateModel.setValue(null) clears a selected model")
    void localDateModelSetNullDeselects() {
        assertSetNullDeselects(new LocalDateModel(LocalDate.of(2024, 3, 15), true));
    }

    @Test
    @DisplayName("Issue #124: SqlDateModel.setValue(date) selects an unselected model")
    void sqlDateModelSetValueSelects() {
        assertSetValueSelects(new SqlDateModel(), java.sql.Date.valueOf("2024-03-15"));
    }

    @Test
    @DisplayName("Issue #124: SqlDateModel.setValue(null) clears a selected model")
    void sqlDateModelSetNullDeselects() {
        assertSetNullDeselects(new SqlDateModel(java.sql.Date.valueOf("2024-03-15"), true));
    }

    @Test
    @DisplayName("Issue #124: setValue fires 'selected' and 'value' property change events")
    void setValueFiresSelectedAndValueEvents() {
        LocalDateModel model = new LocalDateModel();
        List<PropertyChangeEvent> fired = new ArrayList<>();
        model.addPropertyChangeListener(fired::add);
        AtomicInteger changes = new AtomicInteger();
        model.addChangeListener(e -> changes.incrementAndGet());
        LocalDate date = LocalDate.of(2024, 3, 15);

        model.setValue(date);

        assertEquals(1, changes.get());

        List<PropertyChangeEvent> selected = events(fired, AbstractDateModel.PROPERTY_SELECTED);
        assertEquals(1, selected.size());
        assertEquals(false, selected.get(0).getOldValue());
        assertEquals(true, selected.get(0).getNewValue());

        fired.clear();
        changes.set(0);
        model.setValue(null);

        assertEquals(1, changes.get());

        selected = events(fired, AbstractDateModel.PROPERTY_SELECTED);
        assertEquals(1, selected.size());
        assertEquals(true, selected.get(0).getOldValue());
        assertEquals(false, selected.get(0).getNewValue());

        List<PropertyChangeEvent> value = events(fired, AbstractDateModel.PROPERTY_VALUE);
        assertEquals(1, value.size());
        assertEquals(date, value.get(0).getOldValue());
        assertNull(value.get(0).getNewValue());
    }
}
