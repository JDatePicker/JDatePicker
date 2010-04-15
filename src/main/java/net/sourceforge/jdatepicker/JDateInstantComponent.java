package net.sourceforge.jdatepicker;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Properties;

import javax.swing.event.ChangeListener;

/**
 * This interface is implemented by all components which represent a date by day
 * granularity. T will be one of the following org.joda.time.DateMidnight,
 * java.util.Date or java.util.Calendar.
 * 
 * Since the first version of JDatePicker generics was added to Java and
 * JodaTime emerged as a important date handling library in the Java community.
 * 
 * @author Juan Heyns
 */
public interface JDateInstantComponent<T> {
	
	/**
	 * Returns the value of the currently represented date in the component.
	 * Depending on the version of the library used this type will one of the
	 * following:
	 * - java.util.Calendar
	 * - org.joda.time.DateMidnight
	 * - java.util.Date
	 * 
	 * @return
	 */
	public T getValue();

	/**
	 * Sets the value of the date on the component. Depending on the version of
	 * the library used this type will one of the following: 
	 * - java.util.Calendar 
	 * - org.joda.time.DateMidnight 
	 * - java.util.Date 
	 * 
	 * @param value
	 */
	public void setValue(T value);

	/**
	 * Get a string represetation of the value, using the formatting string set.
	 * 
	 * @return
	 */
	public String getStringValue();
	
	/**
	 * Set the value based on a string value.
	 * 
	 * @param value
	 * @throws ParseException 
	 */
	public void setStringValue(String value) throws ParseException;
	
	/**
	 * Adds an ActionListener. The actionListener is notified when a user clicks
	 * on a date.
	 * 
	 * @param actionListener
	 */
	public void addActionListener(ActionListener actionListener);

	/**
	 * Removes the ActionListener. The actionListener is notified when a user
	 * clicks on a date.
	 * 
	 * @param arg
	 */
	public void removeActionListener(ActionListener actionListener);

	/**
	 * Adds a ChangeListener. ChangeListeners will be notified when the selected
	 * date is changed.
	 * 
	 * @param changeListener
	 */
	public void addChangeListener(ChangeListener changeListener);

	/**
	 * Removes the specified ChangeListener. ChangeListeners will be notified
	 * when the selected date is changed.
	 * 
	 * @param arg
	 */
	public void removeChangeListener(ChangeListener changeListener);

	/**
	 * Gets the currently set internationalised strings of the component.
	 * 
	 * @return
	 */
	public Properties getI18nStrings();
	
	/**
	 * Sets internationalised the strings of the component.
	 * 
	 * @param strings
	 */
	public void setI18nStrings(Properties i18nStrings);

	/**
	 * You are able to set the format of the date being displayed on the label.
	 * Formatting is described at:
	 * 
	 * http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html
	 * 
	 * @param dateFormat
	 */
	public void setDateFormat(String dateFormat);
	
}
