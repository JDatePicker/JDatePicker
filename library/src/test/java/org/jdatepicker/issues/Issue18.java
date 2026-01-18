package org.jdatepicker.issues;

import org.jdatepicker.JDatePanel;

import javax.swing.*;

public class Issue18 {

    public static void main(final String[] args) {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(200, 200);

        // Create the JDatePanel and add it to the main content pane, resizing the window will resize the panel
        final JDatePanel datePanel = new JDatePanel();
        frame.getContentPane().add(datePanel);

        // Make the frame visible
        frame.setVisible(true);
    }

}
