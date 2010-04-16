package net.sourceforge.jdatepicker.util_date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;

import net.sourceforge.jdatepicker.JDateInstantPicker;

public class JDateInstantPicker_UtilDate extends JDateInstantPicker<Date> {

	private static final long serialVersionUID = 7627038762758043844L;

	public JDateInstantPicker_UtilDate() {
		super(new JDateInstantPanel_UtilDate());
	}

	@Override
	protected JFormattedTextField.AbstractFormatter createDefaultFormatter() {
		return new JFormattedTextField.AbstractFormatter() {
			
			private static final long serialVersionUID = 4784119521455547555L;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			@Override
			public String valueToString(Object value) throws ParseException {
				Date date = (Date)value;
				if (date == null) {
					return "";
				}
				return format.format(date);
			}
			
			@Override
			public Object stringToValue(String text) throws ParseException {
				if (text == null || text.equals("")) {
					return null;
				}
				Date date = format.parse(text);
				return date;
			}
		};
	}

}
