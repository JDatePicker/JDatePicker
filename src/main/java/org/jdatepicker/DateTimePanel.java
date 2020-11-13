package org.jdatepicker;

public interface DateTimePanel extends DatePanel, TimePanel {

    @Override
    DateTimeModel<?> getModel();

}