package org.jdatepicker.constraints;

import org.jdatepicker.CalendarModel;

import java.util.Calendar;

public class WeekdayConstraint implements DateSelectionConstraint {

	public boolean isValidSelection(CalendarModel model) {
        if (model.isSelected()) {
            Calendar value = Calendar.getInstance();
            value.set(model.getYear(), model.getMonth(), model.getDay());
            value.set(Calendar.HOUR, 0);
            value.set(Calendar.MINUTE, 0);
            value.set(Calendar.SECOND, 0);
            value.set(Calendar.MILLISECOND, 0);

            switch (value.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                case Calendar.TUESDAY:
                case Calendar.WEDNESDAY:
                case Calendar.THURSDAY:
                case Calendar.FRIDAY:
                    return true;
                default:
                    return false;
            }
        }
        else {
            return true;
        }
	}

}
