package org.jdatepicker.features;

import org.jdatepicker.AbstractLocalDateTimeModel;
import org.jdatepicker.DateTimeModel;
import org.jdatepicker.JDateTimePicker;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * JDatePicker that selects only days and months, hiding the year.
 * @author Marc Jakobi
 */
public class Feature13 {

    public static void main(final String[] args) {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JDateTimePicker
        DateTimeModel<LocalDateTime> model = new DemoDateTimeModel(LocalDateTime.of(2001, Month.JANUARY, 1, 0, 0, 0));
        final JDateTimePicker dateTimePicker = new JDateTimePicker(model);
        dateTimePicker.setAllowResetDate(false);
        panel.add(dateTimePicker);

        // Add a Display label that updates with the current value.
        JLabel displayLabel = new JLabel(model.getValue().toString());
        panel.add(displayLabel);
        dateTimePicker.addActionListener(event -> displayLabel.setText(model.getValue() == null ? "" : model.getValue().toString()));

        // Make the frame visible
        frame.setVisible(true);
    }

    private static class DemoDateTimeModel extends AbstractLocalDateTimeModel<LocalDateTime> {


        public DemoDateTimeModel(LocalDateTime initialValue) {
            super(initialValue);
        }

        @Override
        protected LocalDateTime toLocalDateTime(LocalDateTime from) {
            return from;
        }

        @Override
        protected LocalDateTime fromLocalDateTime(LocalDateTime from) {
            return from;
        }
    }
}
