package org.jdatepicker.features;

import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Opening and closing the popup of JDatePicker.
 *
 * a. Open the popup when clicking on the button.
 * b. Close the popup when clicking on the button.
 * c. Close the popup when resizing the parent.
 * d. Close the popup when moving the parent.
 * e. Do not close the popup when minimising the parent.
 * f. Close the popup when clicking anywhere outside of the popup in the same application.
 * g. Do not close the popup when switching focus to another application.
 * h. Close the popup when the component is setVisible(false).
 * i. Close the popup if a value is selected (clicking on a specific date).
 * j. Do not close the popup if browsing the dates (changing displayed months, years, clicking on today label).
 * k. Close the popup if click on the clear date button.
 */
public class Feature10 {

    public static void main(final String[] args) {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JDatePicker
        final JDatePicker datePicker = new JDatePicker();
//        datePicker.setDoubleClickAction(true);
        panel.add(datePicker);

        // Add a button to print out date value
        final JButton button1 = new JButton("Read Value");
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Calendar c2 = (Calendar) datePicker.getModel().getValue();
                System.out.println("JDatePicker: " + (c2 != null ? sdf.format(c2.getTime()) : null));
            }
        });
        panel.add(button1);

        final JButton button2 = new JButton("Hide in 10 seconds");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        for (int t = 1; t <= 10; t++) {
                            try {
                                Thread.sleep(1000);
                                System.out.print(t + " ");
                            } catch (InterruptedException e1) {}
                        }
                        datePicker.setVisible(false);
                    }
                });
                thread.start();
            }
        });
        panel.add(button2);

        // Make the frame visible
        frame.setVisible(true);
    }

}
