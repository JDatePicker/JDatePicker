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

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField;

import net.sourceforge.jdatepicker.graphics.ColorTheme;
import net.sourceforge.jdatepicker.impl.DateComponentFormatter;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

/**
 * Created 18 April 2010
 * Updated 10 August 2012
 * 
 * @author Juan Heyns
 */
public class JDateComponentFactory {
	
	private Class<? extends DateModel<?>> defaultModelClass;
	private JFormattedTextField.AbstractFormatter defaultFormatter;
	private Properties defaultTexts;
	private ColorTheme defaultColors;
	
	private Class<? extends DateModel<?>> modelClass;
	private JFormattedTextField.AbstractFormatter formatter;
	private Properties texts;
	private ColorTheme colors;
	
	/**
	 * Create a factory which will construct widgets with the default date model
	 * (UtilCalendarModel), date formatter (DateComponentFormatter) and
	 * i18nStrings (English) and colors.
	 */
	public JDateComponentFactory() {
		this(null, null, null, null);
	}
	
	/**
	 * Create a factory which will construct widgets with the provided date
	 * model, date formatter, i18nStrings or colors.
	 * 
	 * @param modelClass
	 * @param formatter
	 * @param locale
	 * @param colors
	 */
	public JDateComponentFactory(Class<? extends DateModel<?>> modelClass, JFormattedTextField.AbstractFormatter formatter, Locale locale, ColorTheme colors) {
		this.defaultModelClass = createDefaultModelClass();
		this.defaultFormatter = createDefaultFormatter();
		this.defaultTexts = createTexts(Locale.getDefault());
		this.defaultColors = createDefaultColors();
		this.modelClass = modelClass == null ? this.defaultModelClass : modelClass;
		this.formatter = formatter == null ? this.defaultFormatter : formatter;
		this.texts = locale == null ? this.defaultTexts : createTexts(locale);
		this.colors = colors == null ? this.defaultColors : colors;
	}
	
	/**
	 * Get the default class for the date model.
	 * 
	 * @return
	 */
	protected Class<? extends DateModel<?>> createDefaultModelClass() {
		return UtilCalendarModel.class;
	}
	
	/**
	 * Get the default date formatter.
	 * 
	 * @return
	 */
	protected JFormattedTextField.AbstractFormatter createDefaultFormatter() {
		return new DateComponentFormatter();
	}

	/**
	 * Get the translations for the specified locale.
	 * 
	 * @return
	 */
	protected Properties createTexts(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("net.sourceforge.jdatepicker.i18n.Text", locale);
		return toProperties(resourceBundle);
	}

	/**
	 * Convert to the properties object which is used internally by the widgets.
	 * 
	 * @param resource
	 * @return
	 */
	private Properties toProperties(ResourceBundle resource) {
		Properties result = new Properties();
		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			result.put(key, resource.getString(key));
		}
		return result;
	}	
	
	/**
	 * Create a default color scheme.
	 * 
	 * @return
	 */
	protected ColorTheme createDefaultColors() {
		return new ColorTheme() {};
	}
	
	/**
	 * Create a DateModel initialised to today, based on the clazz type.
	 * 
	 * @param clazz
	 * @return
	 */
	private DateModel<?> createModel(Class<? extends DateModel<?>> clazz) {
		DateModel<?> result = null;
		if (clazz.equals(net.sourceforge.jdatepicker.impl.UtilCalendarModel.class)) {
			result = new UtilCalendarModel(Calendar.getInstance());
		}
		if (clazz.equals(net.sourceforge.jdatepicker.impl.UtilDateModel.class)) {
			result = new UtilDateModel(new java.util.Date());
		}
		if (clazz.equals(net.sourceforge.jdatepicker.impl.SqlDateModel.class)) {
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
	private DateModel<?> createModel(Object value) {
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
	 * Create with factory dateModel, i18nStrings and dateFormatter.
	 * 
	 * @return
	 */
	public JDatePicker createJDatePicker() {
		DateModel<?> model = createModel(modelClass);
		return new JDatePickerImpl(new JDatePanelImpl(model, texts, colors), formatter);
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
		DateModel<?> model = createModel(value);
		return new JDatePickerImpl(new JDatePanelImpl(model, texts, colors), formatter);
	}
	
	/**
	 * Create with factory dateModel, i18nStrings and dateFormatter.
	 * 
	 * @return
	 */
	public JDatePanel createJDatePanel() {
		DateModel<?> model = createModel(modelClass);
		return new JDatePanelImpl(model, texts, colors);
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
		DateModel<?> model = createModel(value);
		return new JDatePanelImpl(model, texts, colors);
	}
	
}
