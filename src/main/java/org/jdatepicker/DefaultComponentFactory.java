package org.jdatepicker;

import java.util.Calendar;

public class DefaultComponentFactory extends AbstractComponentFactory<Calendar> {
	
	@Override
	public CalendarModel<Calendar> createModel() {
		return (CalendarModel<Calendar>) new DefaultCalendarModel(Calendar.getInstance());
	}

	@Override
	public CalendarModel<Calendar> createModel(Calendar value) {
		return (CalendarModel<Calendar>) new DefaultCalendarModel(value);
	}

}
