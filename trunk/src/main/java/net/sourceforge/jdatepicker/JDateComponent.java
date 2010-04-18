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

import java.awt.event.ActionListener;
import java.util.Properties;

/**
 * This interface is implemented by all components which represent a date by day
 * granularity. T will be one of the following org.joda.time.DateMidnight,
 * java.util.Date or java.util.Calendar.
 * 
 * Since the first version of JDatePicker generics was added to Java and
 * JodaTime emerged as a important date handling library in the Java community.
 * 
 * Created 16 April 2010
 * Updated 18 April 2010
 * 
 * @author Juan Heyns
 */
public interface JDateComponent {
	
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
	public DateModel<?> getModel();

	/**
	 * Adds an ActionListener. The actionListener is notified when a user clicks
	 * on a date. Deliberately selecting a date will trigger this event, not
	 * scrolling which fires a ChangeEvent for ChangeListeners.
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

}
