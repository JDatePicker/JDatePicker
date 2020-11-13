package org.jdatepicker.features;

import org.jdatepicker.*;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * JDatePicker that selects only days and months, hiding the year.
 * @author Marc Jakobi
 */
public class Feature12 {

    public static void main(final String[] args) {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JTimePanels
        final JTimePanel timePanel = new JTimePanel(new DemoLocalTimeModel("00:00"));
        final JTimePanel timePanelWithMinutes = new JTimePanel(new DemoLocalTimeModel("00:00"));
        timePanelWithMinutes.setEnableMinutes(true);
        panel.add(timePanel);
        panel.add(timePanelWithMinutes);

        // Make the frame visible
        frame.setVisible(true);
    }

    private static class DemoLocalTimeModel extends AbstractLocalTimeModel<String> {


        public DemoLocalTimeModel(String initialValue) {
            super(initialValue);
        }

        @Override
        protected LocalTime toLocalTime(String from) {
            return LocalTime.parse(from);
        }

        @Override
        protected String fromLocalTime(LocalTime from) {
            return from.toString();
        }
    }
}
