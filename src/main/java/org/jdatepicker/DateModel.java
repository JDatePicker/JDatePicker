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

import java.beans.PropertyChangeListener;

import javax.swing.event.ChangeListener;

/**
 * Created 18 April 2010
 * Updated 26 April 2010
 * 
 * @author Juan Heyns
 *
 * @param <T>
 */
public interface DateModel<T> {

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
	
	/**
	 * Is the value selected or is it not.
	 * 
	 * @return
	 */
	public boolean isSelected();
	
	/**
	 * Set the value as selected.
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected);
	
    /**
     * Adds a PropertyChangeListener to the list of bean listeners.
     * The listener is registered for all bound properties of the target bean.
     *
     * @param listener      the PropertyChangeListener to be added
     *
     * @see #removePropertyChangeListener(PropertyChangeListener)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);


    /**
     * Removes a PropertyChangeListener from the list of bean listeners.
     * This method should be used to remove PropertyChangeListeners that
     * were registered for all bound properties of the target bean.
     * 
     * @param listener      the PropertyChangeListener to be removed
     *
     * @see #addPropertyChangeListener(PropertyChangeListener)
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);

}
