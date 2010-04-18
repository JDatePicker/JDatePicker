package net.sourceforge.jdatepicker;

import javax.swing.event.ChangeListener;

/**
 * Created 18 April 2010
 * 
 * @author Juan Heyns
 *
 * @param <T>
 */
public interface JDateModel<T> {

	/**
	 * Adds a ChangeListener. ChangeListeners will be notified when the internal
	 * state of the control changes. This means that as a user scrolls through
	 * dates the internal model changes, which fires a ChangeEvent each time it
	 * changes.
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
	 * Getters and setters which represent a gregorian date.
	 * 
	 * @return
	 */
	public int getYear();
	
	/**
	 * Getters and setters which represent a gregorian date.
	 * 
	 * @return
	 */
	public void setYear(int year);
	
	/**
	 * Getters and setters which represent a gregorian date.
	 * 
	 * @return
	 */
	public int getMonth();
	
	/**
	 * Getters and setters which represent a gregorian date.
	 * 
	 * @return
	 */
	public void setMonth(int month);
	
	/**
	 * Getters and setters which represent a gregorian date.
	 * 
	 * @return
	 */
	public int getDay();
	
	/**
	 * Getters and setters which represent a gregorian date.
	 * 
	 * @return
	 */
	public void setDay(int day);
	
	/**
	 * Getters and setters which represent a gregorian date.
	 * 
	 * @return
	 */
	public void setDate(int year, int month, int day);
	
	/**
	 * Add or substract number of years.
	 * 
	 * @param add
	 */
	public void addYear(int add);
	
	/**
	 * Add or substract number of months.
	 * 
	 * @param add
	 */
	public void addMonth(int add);
	
	/**
	 * Add or substract number of day.
	 * 
	 * @param add
	 */
	public void addDay(int add);

	/**
	 * Get the value this model represents.
	 * 
	 * @return
	 */
	public T getValue();

	/**
	 * Set the value this model represents.
	 * 
	 * @param value
	 */
	public void setValue(T value);
	
}
