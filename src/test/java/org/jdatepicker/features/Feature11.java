package org.jdatepicker.features;

import java.awt.Color;
import java.awt.Component;
import org.jdatepicker.JDatePicker;

import javax.swing.*;

/**
 * a. Add a red border that is marking today.
 * 
 */
public class Feature11 {

    public static void main(final String[] args) {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        final JPanel jPanel = new JPanel();
        frame.getContentPane().add(jPanel);

        // Create the JDatePicker
        final JDatePicker jDatePicker = new JDatePicker();
        
        //Set a red border marking today for JDatePicker
        jDatePicker.setTodayMarkingBorder(Color.RED);
        
        // add the internal DatePanel to the layout panel of the frame
        jPanel.add((Component)jDatePicker.getJDateInstantPanel());

        // Make the frame visible
        frame.setVisible(true);

    }

}
