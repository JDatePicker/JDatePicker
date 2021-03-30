package org.jdatepicker.features;


import javax.swing.*;
import org.jdatepicker.TodayMark;
import org.jdatepicker.JDatePanel;

/**
 * a. Add a red border that is marking today.
 * 
 */
public class Feature11 {

    public static void main(final String[] args) {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(680, 300);

        // Create a flow layout panel
        final JPanel jPanel = new JPanel();
        frame.getContentPane().add(jPanel);

        // Create a JDatePanel
        final JDatePanel datePanel = new JDatePanel();
        
        // Create a disabled JDatePanel
        final JDatePanel datePanelDisabled = new JDatePanel();
        datePanelDisabled.setEnabled(false);
        
        //Set a border marking today in DatePanels
        TodayMark.setShowTodayBorder(true);
        //Set color of today's number like every other day of this month
        TodayMark.setRedColorForTodayNumber(false);
        
        // add the DatePanel to the layout panel of the frame
        jPanel.add(datePanel);
        jPanel.add(datePanelDisabled);
        
//        change color schema for today's number'
    

        // Make the frame visible
        frame.setVisible(true);

    }

}
