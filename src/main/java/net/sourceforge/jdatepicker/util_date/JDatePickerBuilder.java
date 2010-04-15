package net.sourceforge.jdatepicker.util_date;

import java.util.Date;

import net.sourceforge.jdatepicker.JDateInstantPanel;
import net.sourceforge.jdatepicker.JDateInstantPicker;

public class JDatePickerBuilder {

	public static JDateInstantPanel<Date> buildJDateInstantPanel() {
		return new JDateInstantPanel_UtilDate();
	}
	
	public static JDateInstantPicker<Date> buildJDateInstantPicker() {
		return new JDateInstantPicker_UtilDate();
	}

}
