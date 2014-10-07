package org.jdatepicker.constraints;

import java.util.Calendar;

public class WeekdayConstraint implements DateSelectionConstraint {
	public boolean isValidSelection(Calendar value) {
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
}
