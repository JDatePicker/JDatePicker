package net.sourceforge.jdatepicker;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField;

import net.sourceforge.jdatepicker.impl.JDateComponentFactory_Calendar;
import net.sourceforge.jdatepicker.impl.JDateComponentFactory_Date;

public abstract class JDateComponentFactory<T> {
	
	@SuppressWarnings("unchecked")
	public static JDateComponentFactory getInstance(Class<?> clazz) throws IllegalArgumentException {
		JDateComponentFactory<?> result = null;
		if (clazz.equals(Calendar.class)) {
			result = new JDateComponentFactory_Calendar();
		} 
		else if (clazz.equals(Date.class)) {
			result = new JDateComponentFactory_Date();
		}
		else {
			throw new IllegalArgumentException(clazz.getSimpleName() + " is not supported.");
		}
		return result;
	}
	
	protected JDateComponentFactory() { }
	
	public abstract JDatePicker<T> createJDatePicker();
	
	public abstract JDatePicker<T> createJDatePicker(JFormattedTextField.AbstractFormatter dateFormatter);
	
	public abstract JDatePanel<T> createJDatePanel();
	
}
