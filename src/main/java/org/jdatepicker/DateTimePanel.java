package org.jdatepicker;

public interface DateTimePanel extends DatePanel, TimePanel {

    @Override
    DateTimeModel<?> getModel();

    /**
     * Shows / hides the time picker.
     * @param showTimePanel show the time picker?
     */
    void setShowTimePicker(boolean showTimePanel);

    /**
     * @return is the time picker shown?
     */
    boolean isShowTimePicker();

    /**
     * Shows / hides the date picker.
     * @param showDatePicker show the date picker?
     */
    void setShowDatePicker(boolean showDatePicker);

    /**
     * @return is the date picker shown?
     */
    boolean isShowDatePicker();
}