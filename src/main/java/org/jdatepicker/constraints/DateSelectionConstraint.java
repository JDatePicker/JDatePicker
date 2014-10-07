package org.jdatepicker.constraints;

import java.util.Calendar;

public interface DateSelectionConstraint {
	boolean isValidSelection(Calendar value);
}
