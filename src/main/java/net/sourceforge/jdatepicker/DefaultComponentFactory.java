package net.sourceforge.jdatepicker;

import java.util.Calendar;

public class DefaultComponentFactory extends AbstractComponentFactory<Calendar> {
	
	@Override
	protected CalendarModel<Calendar> createModel() {
		return (CalendarModel<Calendar>) new DefaultCalendarModel(Calendar.getInstance());
	}

	@Override
	protected CalendarModel<Calendar> createModel(Calendar value) {
		return (CalendarModel<Calendar>) new DefaultCalendarModel(value);
	}

}
