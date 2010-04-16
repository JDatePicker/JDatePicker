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
package net.sourceforge.jdatepicker.util_calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField;

import net.sourceforge.jdatepicker.JDateInstantPicker;

/**
 * Created 16 April 2010
 * 
 * @author Juan Heyns
 */
public class JDateInstantPicker_UtilCalendar extends JDateInstantPicker<Calendar> {

	private static final long serialVersionUID = 4502592170240575115L;

	protected JDateInstantPicker_UtilCalendar() {
		super(new JDateInstantPanel_UtilCalendar());
	}

	@Override
	protected JFormattedTextField.AbstractFormatter createDefaultFormatter() {
		return new JFormattedTextField.AbstractFormatter() {
			
			private static final long serialVersionUID = 4784639521455547590L;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			@Override
			public String valueToString(Object value) throws ParseException {
				Calendar cal = (Calendar)value;
				if (cal == null) {
					return "";
				}
				return format.format(cal.getTime());
			}
			
			@Override
			public Object stringToValue(String text) throws ParseException {
				if (text == null || text.equals("")) {
					return null;
				}
				Date date = format.parse(text);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				return calendar;
			}
		};
	}

}
