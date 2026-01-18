package org.jdatepicker;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Immutable value object representing a date selection.
 *
 * <p>This record encapsulates a date value along with its selection state, providing a
 * type-safe and immutable representation of what a user has selected in a date picker.
 *
 * <p><b>Immutability:</b> As a record, DateSelection is inherently immutable. Once created,
 * its values cannot be changed. This makes it thread-safe and suitable for use as a key
 * in collections.
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * // Create a selection
 * LocalDate date = LocalDate.of(2024, 1, 15);
 * DateSelection selection = new DateSelection(date, true);
 *
 * // Use in streams/collections
 * List&lt;DateSelection&gt; selections = List.of(
 *     new DateSelection(LocalDate.now(), true),
 *     new DateSelection(LocalDate.now().plusDays(1), false)
 * );
 *
 * selections.stream()
 *     .filter(DateSelection::selected)
 *     .map(DateSelection::date)
 *     .forEach(System.out::println);
 * </pre>
 *
 * @param date     the selected date, may be null if no date is selected
 * @param selected whether the date is actively selected
 * @author Claude
 * @since 2.0
 */
public record DateSelection(LocalDate date, boolean selected) {

    /**
     * Compact constructor with validation.
     *
     * @throws IllegalArgumentException if selected is true but date is null
     */
    public DateSelection {
        if (selected && date == null) {
            throw new IllegalArgumentException("Cannot have a selected state with null date");
        }
    }

    /**
     * Creates an unselected DateSelection with a null date.
     *
     * @return an unselected DateSelection
     */
    public static DateSelection unselected() {
        return new DateSelection(null, false);
    }

    /**
     * Creates a selected DateSelection with the specified date.
     *
     * @param date the date to select, must not be null
     * @return a selected DateSelection
     * @throws IllegalArgumentException if date is null
     */
    public static DateSelection of(LocalDate date) {
        Objects.requireNonNull(date, "date must not be null");
        return new DateSelection(date, true);
    }

    /**
     * Checks if this selection has a date (regardless of selection state).
     *
     * @return true if date is not null, false otherwise
     */
    public boolean hasDate() {
        return date != null;
    }

    /**
     * Returns a new DateSelection with the selection state toggled.
     *
     * @return a new DateSelection with the same date but opposite selection state
     * @throws IllegalStateException if trying to select when date is null
     */
    public DateSelection toggleSelection() {
        if (date == null && !selected) {
            throw new IllegalStateException("Cannot select when date is null");
        }
        return new DateSelection(date, !selected);
    }

    /**
     * Returns a new DateSelection with a different date.
     *
     * @param newDate the new date
     * @return a new DateSelection with the new date and same selection state
     */
    public DateSelection withDate(LocalDate newDate) {
        return new DateSelection(newDate, selected && newDate != null);
    }

    /**
     * Returns a string representation suitable for display.
     *
     * @return a formatted string representation
     */
    @Override
    public String toString() {
        if (date == null) {
            return "DateSelection[none]";
        }
        return String.format("DateSelection[%s, %s]",
                date,
                selected ? "selected" : "not selected");
    }
}
