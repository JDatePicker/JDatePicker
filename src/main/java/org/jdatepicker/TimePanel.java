package org.jdatepicker;

/**
 * Time panel interface.
 *
 * @author Marc Jakobi
 */
public interface TimePanel extends TimeComponent {

    /**
     * Sets whether to enable minutes (disabled by default). If minutes are disabled, so are seconds and nanoseconds.
     * @param enableMinutes {@code true} to enable minutes.
     */
    TimePanel setEnableMinutes(boolean enableMinutes);

    /**
     * @return Are minutes enabled?
     */
    boolean isMinutesEnabled();

    /**
     * Sets whether to enable seconds (disabled by default). If seconds are disabled, so are nanoseconds.
     * @param enableSeconds {@code true} to enable seconds.
     */
    TimePanel setEnableSeconds(boolean enableSeconds);

    /**
     * @return are seconds enabled?
     */
    boolean isSecondsEnabled();

    /**
     * Sets whether to enable nanoseconds (disabled by default).
     * @param enableNanoSeconds {@code true} to enable nanoseconds.
     */
    TimePanel setEnableNanoseconds(boolean enableNanoSeconds);

    /**
     * @return are nanoseconds enabled?
     */
    boolean isNanoSecondsEnabled();

    /**
     * Sets whether to always show a spinner, or to show a combo box if only hours are enabled (default).
     * @param alwaysShowSpinner {@code true} to always show a spinner
     */
    TimePanel setAlwaysShowSpinner(boolean alwaysShowSpinner);

    /**
     * @return {@code true} if this panel always shows a spinner, even if only hours are enabled
     */
    boolean isAlwaysShowSpinner();
}
