package org.jdatepicker;

import org.jdatepicker.impl.UtilCalendarModel;

import java.util.Calendar;

public class DefaultComponentFactory extends JDateComponentFactory<Calendar> {
	
	@Override
	public DateModel<Calendar> createModel() {
		return (DateModel<Calendar>) new UtilCalendarModel(Calendar.getInstance());
	}

	@Override
	public DateModel<Calendar> createModel(Calendar value) {
		return (DateModel<Calendar>) new UtilCalendarModel(value);
	}

}
