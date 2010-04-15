package net.sourceforge.jdatepicker.util_calendar;

import java.util.Calendar;

import net.sourceforge.jdatepicker.JDateInstantPanel;
import net.sourceforge.jdatepicker.JDateInstantPicker;

public class JDatePickerBuilder {
	
	public static JDateInstantPanel<Calendar> buildJDateInstantPanel() {
		return new JDateInstantPanel_UtilCalendar();
	}
	
	public static JDateInstantPicker<Calendar> buildJDateInstantPicker() {
		return new JDateInstantPicker_UtilCalendar();
	}

}
