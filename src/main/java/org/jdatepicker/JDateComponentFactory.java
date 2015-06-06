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
package org.jdatepicker;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilCalendarModel;
import org.jdatepicker.impl.UtilDateModel;

/**
 * Created 18 April 2010
 * Updated 10 August 2012
 * 
 * @author Juan Heyns
 */
public class JDateComponentFactory {
	
	private Class<? extends DateModel<?>> dateModelClass;
	private JFormattedTextField.AbstractFormatter dateFormatter;
	private Properties i18nStrings;
	
	/**
	 * Create a factory which will construct widgets with the default date model
	 * (UtilCalendarModel), date formatter (DateComponentFormatter) and
	 * i18nStrings (English).
	 */
	public JDateComponentFactory() {
		this(null, null, null);
	}
	
	/**
	 * Create a factory which will construct widgets with the provided date
	 * model, date formatter and i18nStrings.
	 * 
	 * @param dateModelClass
	 * @param dateFormatter
	 * @param i18nStrings
	 */
	public JDateComponentFactory(Class<? extends DateModel<?>> dateModelClass, JFormattedTextField.AbstractFormatter dateFormatter, Locale locale) {
		if (dateModelClass == null) {
			this.dateModelClass = getDefaultDateModelClass();
		}
		else {
			this.dateModelClass = dateModelClass;
		}
		
		if (dateFormatter == null) {
			this.dateFormatter = getDefaultDateFormatter();
		}
		else {
			this.dateFormatter = dateFormatter;
		}
		
		if (locale == null) {
			this.i18nStrings = getI18nStrings(Locale.getDefault());
		}
		else {
			this.i18nStrings = getI18nStrings(locale);
		}
	}
	
	/**
	 * Get the default class for the date model.
	 * 
	 * @return
	 */
	protected Class<? extends DateModel<?>> getDefaultDateModelClass() {
		return UtilCalendarModel.class;
	}
	
	/**
	 * Get the default date formatter.
	 * 
	 * @return
	 */
	protected JFormattedTextField.AbstractFormatter getDefaultDateFormatter() {
		return new DateComponentFormatter();
	}

	/**
	 * Get the translations for the specified locale.
	 * 
	 * @return
	 */
	protected Properties getI18nStrings(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("org.jdatepicker.i18n.Text", locale);
		return convertToProperties(resourceBundle);
	}

	/**
	 * Convert to the properties object which is used internally by the widgets.
	 * 
	 * @param resource
	 * @return
	 */
	private Properties convertToProperties(ResourceBundle resource) {
		Properties properties = new Properties();
		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			properties.put(key, resource.getString(key));
		}
		return properties;
	}	
	
	/**
	 * Create a DateModel initialised to today, based on the clazz type.
	 * 
	 * @param clazz
	 * @return
	 */
	private DateModel<?> createDateModel(Class<? extends DateModel<?>> clazz) {
		DateModel<?> result = null;
		if (clazz.equals(org.jdatepicker.impl.UtilCalendarModel.class)) {
			result = new UtilCalendarModel(Calendar.getInstance());
		}
		if (clazz.equals(org.jdatepicker.impl.UtilDateModel.class)) {
			result = new UtilDateModel(new java.util.Date());
		}
		if (clazz.equals(org.jdatepicker.impl.SqlDateModel.class)) {
			result = new SqlDateModel(new java.sql.Date(System.currentTimeMillis()));
		}
		
		return result;
	}
	
	/**
	 * Create a DateModel based on the type of the value.
	 * 
	 * @param value
	 * @return
	 */
	private static DateModel<?> createDateModel(Object value) {
		DateModel<?> result = null;
		if (value instanceof java.util.Calendar) {
			result = new UtilCalendarModel((java.util.Calendar)value);
		}
		if (value instanceof java.util.Date) {
			result = new UtilDateModel((java.util.Date)value);
		}
		if (value instanceof java.sql.Date) {
			result = new SqlDateModel((java.sql.Date)value);
		}
		
		return result;
	}
	
	/**
	 * Create with factory dateModel, i18nStrings and dateFormatter.
	 * 
	 * @return
	 */
	public JDatePicker createJDatePicker() {
		DateModel<?> model = createDateModel(dateModelClass);
		return new JDatePickerImpl(new JDatePanelImpl(model, i18nStrings), dateFormatter);
	}
	
	/**
	 * Create by specifying the initial value of the widget.
	 * 
	 * @param model
	 * @param i18n
	 * @param format
	 * @return
	 */
	public JDatePicker createJDatePicker(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Value may not be null.");
		}
		DateModel<?> model = createDateModel(value);
		return new JDatePickerImpl(new JDatePanelImpl(model, i18nStrings), dateFormatter);
	}
	
	/**
	 * Create with factory dateModel, i18nStrings and dateFormatter.
	 * 
	 * @return
	 */
	public JDatePanel createJDatePanel() {
		DateModel<?> model = createDateModel(dateModelClass);
		return new JDatePanelImpl(model, i18nStrings);
	}
	
	/**
	 * Create by specifying the initial value of the widget.
	 * 
	 * @param model
	 * @param i18n
	 * @return
	 */
	public JDatePanel createJDatePanel(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Value may not be null.");
		}
		DateModel<?> model = createDateModel(value);
		return new JDatePanelImpl(model, i18nStrings);
	}
	
}
