package org.jdatepicker.constraints;

import java.util.Calendar;

public class WeekendConstraint extends WeekdayConstraint {
	public boolean isValidSelection(Calendar value) {
		return !super.isValidSelection(value);
	}
}
