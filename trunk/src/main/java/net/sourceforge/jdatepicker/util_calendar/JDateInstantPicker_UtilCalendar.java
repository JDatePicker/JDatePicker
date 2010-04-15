package net.sourceforge.jdatepicker.util_calendar;

import java.util.Calendar;

import net.sourceforge.jdatepicker.JDateInstantPicker;

public class JDateInstantPicker_UtilCalendar extends JDateInstantPicker<Calendar> {

	private static final long serialVersionUID = 4502592170240575115L;

	protected JDateInstantPicker_UtilCalendar() {
		super(new JDateInstantPanel_UtilCalendar());
	}

}
