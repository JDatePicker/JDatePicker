package org.jdatepicker;

import java.util.Calendar;

import org.jdatepicker.impl.UtilCalendarModel;

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
