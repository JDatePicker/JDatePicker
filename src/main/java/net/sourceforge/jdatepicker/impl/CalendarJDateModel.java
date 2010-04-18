package net.sourceforge.jdatepicker.impl;

import java.util.Calendar;

import net.sourceforge.jdatepicker.AbstractJDateModel;

public class CalendarJDateModel extends AbstractJDateModel<java.util.Calendar> {
	
	private Calendar value;
	
	public CalendarJDateModel() {
		this(null);
	}
	
	public CalendarJDateModel(Calendar value) {
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
	
	public Calendar getValue() {
		return (Calendar) value.clone();
	}
	
	public void addDay(int add) {
		value.add(Calendar.DATE, add);
		fireChangeEvent();
	}

	public void addMonth(int add) {
		value.add(Calendar.MONTH, add);
		fireChangeEvent();
	}

	public void addYear(int add) {
		value.add(Calendar.YEAR, add);
		fireChangeEvent();
	}

	public void setDate(int year, int month, int day) {
		value.set(year, month, day);
		fireChangeEvent();
	}

	public void setDay(int day) {
		value.set(Calendar.DATE, day);
		fireChangeEvent();
	}

	public void setMonth(int month) {
		value.set(Calendar.MONTH, month);
		fireChangeEvent();
	}

	public void setYear(int year) {
		value.set(Calendar.YEAR, year);
		setToMidnight();
		fireChangeEvent();
	}

	public void setValue(Calendar value) {
		if (value == null) {
			value = Calendar.getInstance();
		}
		this.value = value;
		setToMidnight();
		fireChangeEvent();
	}

	private void setToMidnight() {
		value.set(Calendar.HOUR, 0);
		value.set(Calendar.MINUTE, 0);
		value.set(Calendar.SECOND, 0);
		value.set(Calendar.MILLISECOND, 0);
	}

}
