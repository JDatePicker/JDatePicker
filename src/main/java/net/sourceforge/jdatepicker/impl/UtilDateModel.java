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
package net.sourceforge.jdatepicker.impl;

import java.util.Calendar;
import java.util.Date;

import net.sourceforge.jdatepicker.AbstractDateModel;

public class UtilDateModel extends AbstractDateModel<java.util.Date> {

	private Calendar value;
	
	public UtilDateModel() {
		this(null);
	}
	
	public UtilDateModel(Date value) {
		setValue(value);
		setToMidnight();
	}

	public int getDay() {
		return value.get(Calendar.DATE);
	}
	
	public int getMonth() {
		return value.get(Calendar.MONTH);
	}
	
	public int getYear() {
		return value.get(Calendar.YEAR);
	}
	
	public Date getValue() {
		return (Date)value.getTime().clone();
	}
	
	public void setDay(int day) {
		int oldDayValue = this.value.get(Calendar.DATE);
		Date oldValue = getValue();
		value.set(Calendar.DATE, day);
		fireChangeEvent();
		firePropertyChange("day", oldDayValue, this.value.get(Calendar.DATE));
		firePropertyChange("value", oldValue, getValue());
	}

	public void addDay(int add) {
		int oldDayValue = this.value.get(Calendar.DATE);
		Date oldValue = getValue();
		value.add(Calendar.DATE, add);
		fireChangeEvent();
		firePropertyChange("day", oldDayValue, this.value.get(Calendar.DATE));
		firePropertyChange("value", oldValue, getValue());
	}
	
	public void setMonth(int month) {
		int oldMonthValue = this.value.get(Calendar.MONTH);
		Date oldValue = getValue();
		value.set(Calendar.MONTH, month);
		fireChangeEvent();
		firePropertyChange("month", oldMonthValue, this.value.get(Calendar.MONTH));
		firePropertyChange("value", oldValue, getValue());
	}

	public void addMonth(int add) {
		int oldMonthValue = this.value.get(Calendar.MONTH);
		Date oldValue = getValue();
		value.add(Calendar.MONTH, add);
		fireChangeEvent();
		firePropertyChange("month", oldMonthValue, this.value.get(Calendar.MONTH));
		firePropertyChange("value", oldValue, getValue());
	}
	
	public void setYear(int year) {
		int oldYearValue = this.value.get(Calendar.YEAR);
		Date oldValue = getValue();
		value.set(Calendar.YEAR, year);
		fireChangeEvent();
		firePropertyChange("year", oldYearValue, this.value.get(Calendar.YEAR));
		firePropertyChange("value", oldValue, getValue());
	}

	public void addYear(int add) {
		int oldYearValue = this.value.get(Calendar.YEAR);
		Date oldValue = getValue();
		value.add(Calendar.YEAR, add);
		fireChangeEvent();
		firePropertyChange("year", oldYearValue, this.value.get(Calendar.YEAR));
		firePropertyChange("value", oldValue, getValue());
	}
	
	public void setValue(Date dateValue) {
		Calendar calValue = Calendar.getInstance();
		if (dateValue != null) {
			calValue.setTime(dateValue);
		}
		else {
			this.value = calValue;
		}
		int oldYearValue = this.value.get(Calendar.YEAR);
		int oldMonthValue = this.value.get(Calendar.MONTH);
		int oldDayValue = this.value.get(Calendar.DATE);
		Date oldValue = getValue();
		this.value = calValue;
		setToMidnight();
		fireChangeEvent();
		firePropertyChange("year", oldYearValue, this.value.get(Calendar.YEAR));
		firePropertyChange("month", oldMonthValue, this.value.get(Calendar.MONTH));
		firePropertyChange("day", oldDayValue, this.value.get(Calendar.DATE));
		firePropertyChange("value", oldValue, getValue());
	}
	
	public void setDate(int year, int month, int day) {
		int oldYearValue = this.value.get(Calendar.YEAR);
		int oldMonthValue = this.value.get(Calendar.MONTH);
		int oldDayValue = this.value.get(Calendar.DATE);
		Date oldValue = getValue();
		value.set(year, month, day);
		fireChangeEvent();
		firePropertyChange("year", oldYearValue, this.value.get(Calendar.YEAR));
		firePropertyChange("month", oldMonthValue, this.value.get(Calendar.MONTH));
		firePropertyChange("day", oldDayValue, this.value.get(Calendar.DATE));
		firePropertyChange("value", oldValue, getValue());
	}
	
	private void setToMidnight() {
		value.set(Calendar.HOUR, 0);
		value.set(Calendar.MINUTE, 0);
		value.set(Calendar.SECOND, 0);
		value.set(Calendar.MILLISECOND, 0);
	}

}
