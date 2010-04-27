/**
Copyright 2004 Juan Heyns. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY JUAN HEYNS ``AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JUAN HEYNS OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Juan Heyns.
*/
package net.sourceforge.jdatepicker;

import javax.swing.JFormattedTextField;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

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
	public static JDatePicker createJDatePicker(DateModel<?> model) {
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
	public static JDatePicker createJDatePicker(DateModel<?> model, JFormattedTextField.AbstractFormatter dateFormatter) {
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
	public static JDatePanel createJDatePanel(DateModel<?> model) {
		return new JDatePanelImpl(model);
	}
	
	/**
	 * Create a DateModel based on the type of the value.
	 * 
	 * @param value
	 * @return
	 */
	public static DateModel<?> createDateModel(Object value) {
		Class<?> clazz = value.getClass();
		
		DateModel<?> result = null;
		if (clazz.equals(java.util.Calendar.class)) {
			result = new UtilCalendarModel((java.util.Calendar)value);
		}
		if (clazz.equals(java.util.Date.class)) {
			result = new UtilDateModel((java.util.Date)value);
		}
		if (clazz.equals(java.sql.Date.class)) {
			result = new SqlDateModel((java.sql.Date)value);
		}
		
		return result;
	}
	
	/**
	 * Create a DateModel initialised to today, based on the clazz type.
	 * 
	 * @param clazz
	 * @return
	 */
	public static DateModel<?> createDateModel(Class<?> clazz) {
		DateModel<?> result = null;
		if (clazz.equals(java.util.Calendar.class)) {
			result = new UtilCalendarModel();
		}
		if (clazz.equals(java.util.Date.class)) {
			result = new UtilDateModel();
		}
		if (clazz.equals(java.sql.Date.class)) {
			result = new SqlDateModel();
		}
		
		return result;
	}
	
}
