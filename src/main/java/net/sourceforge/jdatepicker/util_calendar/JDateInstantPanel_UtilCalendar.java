package net.sourceforge.jdatepicker.util_calendar;

import java.util.Calendar;

import net.sourceforge.jdatepicker.JDateInstantPanel;

/**
 * Created 16 April 2010
 * 
 * @author Juan Heyns
 */
public class JDateInstantPanel_UtilCalendar extends JDateInstantPanel<Calendar> {

	private static final long serialVersionUID = 4316625319919804165L;
	
	public JDateInstantPanel_UtilCalendar() {
		super();
	}
	
	public void setValue(Calendar value) {
		internalModel.setCalendar(value);
	}
	
	public Calendar getValue() {
		return internalModel.getCalendar();
	}

}
