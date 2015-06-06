package org.jdatepicker;

import java.util.Calendar;

public class DefaultComponentFactory extends JDateComponentFactory<Calendar> {
    
    @Override
    public DateModel<Calendar> createModel() {
        return new UtilCalendarModel();
    }

    @Override
    public DateModel<Calendar> createModel(Calendar value) {
        return new UtilCalendarModel(value);
    }

    public static DateModel<?> createModelFromValue(Object value) {
        if (value instanceof java.util.Calendar) {
            return new UtilCalendarModel((Calendar)value);
        }
        if (value instanceof java.util.Date) {
            return new UtilDateModel((java.util.Date)value);
        }
        if (value instanceof java.sql.Date) {
            return new SqlDateModel((java.sql.Date)value);
        }
        throw new IllegalArgumentException("No model could be constructed from the initial value object.");
    }


}
