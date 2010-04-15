package net.sourceforge.jdatepicker.util_date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sourceforge.jdatepicker.JDateInstantPanel;

public class JDateInstantPanel_UtilDate extends JDateInstantPanel<Date> {

	private static final long serialVersionUID = 6667146006951102532L;
	
	private String dateFormat;
	
	public JDateInstantPanel_UtilDate() {
		super();
		dateFormat = "yyyy-MM-dd";
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setValue(Date value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		calendarModel.setCalendar(calendar);
	}
	
	public Date getValue() {
		Calendar calendar = calendarModel.getCalendar();
		return calendar.getTime();
	}
	
	public String getStringValue() {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(calendarModel.getCalendar().getTime());
	}

	public void setStringValue(String value) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = format.parse(value);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendarModel.setCalendar(calendar);
	}

}
