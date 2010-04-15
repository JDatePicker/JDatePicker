package net.sourceforge.jdatepicker.util_calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sourceforge.jdatepicker.JDateInstantPanel;

public class JDateInstantPanel_UtilCalendar extends JDateInstantPanel<Calendar> {

	private static final long serialVersionUID = 4316625319919804165L;
	
	private String dateFormat;

	public JDateInstantPanel_UtilCalendar() {
		super();
		dateFormat = "yyyy-MM-dd";
	}
	
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setValue(Calendar value) {
		internalModel.setCalendar(value);
	}
	
	public Calendar getValue() {
		return internalModel.getCalendar();
	}
	
	public String getStringValue() {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(internalModel.getCalendar().getTime());
	}

	public void setStringValue(String value) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = format.parse(value);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		internalModel.setCalendar(calendar);
	}

}
