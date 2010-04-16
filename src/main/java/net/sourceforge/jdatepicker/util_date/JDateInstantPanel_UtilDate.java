package net.sourceforge.jdatepicker.util_date;

import java.util.Calendar;
import java.util.Date;

import net.sourceforge.jdatepicker.JDateInstantPanel;

public class JDateInstantPanel_UtilDate extends JDateInstantPanel<Date> {

	private static final long serialVersionUID = 6667146004567102532L;
	
	public JDateInstantPanel_UtilDate() {
		super();
	}

	public void setValue(Date value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		internalModel.setCalendar(calendar);
	}
	
	public Date getValue() {
		Calendar calendar = internalModel.getCalendar();
		return calendar.getTime();
	}

}
