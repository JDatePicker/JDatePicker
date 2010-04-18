package net.sourceforge.jdatepicker;

import javax.swing.JFormattedTextField;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

/**
 * Created 18 April 2010
 * 
 * @author Juan Heyns
 */
public class JDateComponentFactory {
	
	/**
	 * Create with default model and formatter.
	 * 
	 * @return
	 */
	public static JDatePicker createJDatePicker() {
		return new JDatePickerImpl(new JDatePanelImpl(null), null);
	}
	
	/**
	 * Create with default formatter.
	 * 
	 * @return
	 */
	public static JDatePicker createJDatePicker(JDateModel<?> model) {
		return new JDatePickerImpl(new JDatePanelImpl(model), null);
	}
	
	/**
	 * Create with default model.
	 * 
	 * @return
	 */
	public static JDatePicker createJDatePicker(JFormattedTextField.AbstractFormatter dateFormatter) {
		return new JDatePickerImpl(new JDatePanelImpl(null), dateFormatter);
	}
	
	/**
	 * Create specifying model and formatter.
	 * 
	 * @return
	 */
	public static JDatePicker createJDatePicker(JDateModel<?> model, JFormattedTextField.AbstractFormatter dateFormatter) {
		return new JDatePickerImpl(new JDatePanelImpl(model), dateFormatter);
	}
	
	/**
	 * Create with default model.
	 * 
	 * @return
	 */
	public static JDatePanel createJDatePanel() {
		return new JDatePanelImpl(null);
	}
	
	/**
	 * Create specifying model.
	 * 
	 * @return
	 */
	public static JDatePanel createJDatePanel(JDateModel<?> model) {
		return new JDatePanelImpl(model);
	}
	
}
