package org.jdatepicker;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

/**
 * Modern date model implementation using {@link java.time.LocalDate} from the java.time API.
 *
 * <p>This model represents a date using the modern, immutable {@code LocalDate} class introduced
 * in Java 8. It provides better API design, thread-safety, and clarity compared to the legacy
 * {@link java.util.Calendar} and {@link java.util.Date} classes.
 *
 * <p><b>Advantages over legacy models:</b>
 * <ul>
 *   <li>Immutable - LocalDate objects cannot be modified</li>
 *   <li>Thread-safe - No shared mutable state</li>
 *   <li>Clear API - More intuitive method names and behavior</li>
 *   <li>No time components - Represents dates only (no hours/minutes/seconds confusion)</li>
 *   <li>Better month representation - Uses Month enum instead of 0-indexed integers</li>
 * </ul>
 *
 * <p><b>Month Handling:</b> This class maintains backward compatibility with the DateModel interface,
 * which uses 0-indexed months (0=January, 11=December) following Calendar convention. Internally,
 * LocalDate uses 1-indexed months, but this is handled transparently.
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * LocalDateModel model = new LocalDateModel();
 * model.setDate(2024, 0, 15); // January 15, 2024 (0-indexed month)
 * LocalDate date = model.getValue(); // Returns LocalDate(2024-01-15)
 *
 * // Or construct with an existing LocalDate
 * LocalDate now = LocalDate.now();
 * LocalDateModel model2 = new LocalDateModel(now);
 * </pre>
 *
 * <p><b>Thread Safety:</b> LocalDate is immutable, making this model inherently thread-safe
 * for the stored value. Listener management is thread-safe via CopyOnWriteArraySet.
 *
 * @author Claude
 * @since 2.0
 * @see LocalDate
 * @see DateModel
 */
public class LocalDateModel extends AbstractDateModel<LocalDate> {

    /**
     * Creates a new LocalDateModel with no initial value.
     * The model will be in an unselected state with a null value.
     */
    public LocalDateModel() {
        this(null);
    }

    /**
     * Creates a new LocalDateModel with the specified initial value.
     *
     * @param value the initial LocalDate value, or null for no date
     */
    public LocalDateModel(LocalDate value) {
        super();
        setValue(value);
    }

    /**
     * Converts a LocalDate to a Calendar for internal use by AbstractDateModel.
     * The time is set to midnight (00:00:00.000).
     *
     * @param from the LocalDate to convert
     * @return a Calendar representing the same date at midnight, never null
     */
    @Override
    protected Calendar toCalendar(LocalDate from) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(from.getYear(), from.getMonthValue() - 1, from.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Converts a Calendar to a LocalDate, discarding any time information.
     *
     * @param from the Calendar to convert
     * @return a LocalDate representing the date portion of the Calendar, never null
     */
    @Override
    protected LocalDate fromCalendar(Calendar from) {
        return LocalDate.of(
                from.get(Calendar.YEAR),
                from.get(Calendar.MONTH) + 1, // LocalDate uses 1-indexed months
                from.get(Calendar.DAY_OF_MONTH)
        );
    }

    /**
     * Compares this model to another object for equality.
     * Two LocalDateModel instances are equal if they have the same LocalDate value
     * and the same selection state.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocalDateModel other)) {
            return false;
        }
        return Objects.equals(getValue(), other.getValue()) &&
               isSelected() == other.isSelected();
    }

    /**
     * Returns a hash code value for this model.
     * The hash code is based on the LocalDate value and selection state.
     *
     * @return a hash code value for this model
     */
    @Override
    public int hashCode() {
        return Objects.hash(getValue(), isSelected());
    }

    /**
     * Returns a string representation of this model for debugging.
     *
     * @return a string representation including the date and selection state
     */
    @Override
    public String toString() {
        return "LocalDateModel{" +
                "value=" + getValue() +
                ", selected=" + isSelected() +
                '}';
    }
}
