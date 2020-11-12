package org.jdatepicker.features;

import org.jdatepicker.AbstractDateModel;
import org.jdatepicker.ComponentFormatDefaults;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * JDatePicker that selects only days and months, hiding the year.
 * @author Marc Jakobi
 */
public class Feature11 {

    public static void main(final String[] args) {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JDatePicker
        final JDatePicker datePicker = new JDatePicker(new DemoFixedYearDateModel(2001));
        ComponentFormatDefaults.getInstance().setFormat(ComponentFormatDefaults.Key.SELECTED_DATE_FIELD, new SimpleDateFormat("LLL dd"));
        datePicker.setShowYearSpinner(false);
        datePicker.setShowTodayLabel(false);
        panel.add(datePicker);

        // Make the frame visible
        frame.setVisible(true);
    }

    private static class DemoFixedYearDateModel extends AbstractDateModel<LocalDateTime> {

        private final int year;

        public DemoFixedYearDateModel(int year) {
            super();
            this.year = year;
            setValue(LocalDateTime.of(year, Month.JANUARY, 1, 0, 0, 0));
        }

        @Override
        protected LocalDateTime fromCalendar(Calendar from) {
            TimeZone zone = from.getTimeZone();
            ZoneId zoneId = null == zone ? ZoneId.systemDefault() : zone.toZoneId();
            return LocalDateTime.ofInstant(from.toInstant(), zoneId);
        }

        @Override
        protected Calendar toCalendar(LocalDateTime from) {
            Calendar c = Calendar.getInstance();
            c.set(from.getYear(),
                    from.getMonthValue() - 1,
                    from.getDayOfMonth(),
                    from.getHour(),
                    from.getMinute(),
                    from.getSecond());
            return c;
        }

        @Override
        public void setYear(int year) {
            super.setYear(this.year);
        }
    }
}
