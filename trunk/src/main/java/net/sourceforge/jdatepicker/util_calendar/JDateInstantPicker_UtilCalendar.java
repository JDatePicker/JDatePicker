package net.sourceforge.jdatepicker.util_calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField;

import net.sourceforge.jdatepicker.JDateInstantPicker;

/**
 * Created 16 April 2010
 * 
 * @author Juan Heyns
 */
public class JDateInstantPicker_UtilCalendar extends JDateInstantPicker<Calendar> {

	private static final long serialVersionUID = 4502592170240575115L;

	protected JDateInstantPicker_UtilCalendar() {
		super(new JDateInstantPanel_UtilCalendar());
	}

	@Override
	protected JFormattedTextField.AbstractFormatter createDefaultFormatter() {
		return new JFormattedTextField.AbstractFormatter() {
			
			private static final long serialVersionUID = 4784639521455547590L;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			@Override
			public String valueToString(Object value) throws ParseException {
				Calendar cal = (Calendar)value;
				if (cal == null) {
					return "";
				}
				return format.format(cal.getTime());
			}
			
			@Override
			public Object stringToValue(String text) throws ParseException {
				if (text == null || text.equals("")) {
					return null;
				}
				Date date = format.parse(text);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				return calendar;
			}
		};
	}

}
