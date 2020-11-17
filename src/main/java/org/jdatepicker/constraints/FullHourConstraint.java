package org.jdatepicker.constraints;

import org.jdatepicker.TimeModel;

public class FullHourConstraint implements TimeSelectionConstraint {

    @Override
    public boolean isValidSelection(TimeModel<?> model) {
        return model.getMinute() == 0
                && model.getSecond() == 0
                && model.getNanosecond() == 0;
    }

}
