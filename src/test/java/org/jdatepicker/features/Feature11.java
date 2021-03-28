package org.jdatepicker.features;


import javax.swing.*;
import org.jdatepicker.DatePanel;
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
        
        final JDatePanel datePanelExp = new JDatePanel();
        //instance using a static method is not the right way but would work
        //(anyway the border couldn't be disabled only for the instance, 
        //this will be overriden by the subsequent setting of border):
        datePanelExp.setTodayBorder(false);

        /* As the border is a static feature (just like the colored today number)
         * a border is displayed for all instances in a runtime. 
         */
        DatePanel.setTodayBorder(true);
//        DatePanel.setTodayBorder(false);
        
        // add the DatePanels to the layout panel of the frame
        jPanel.add(datePanel);
        jPanel.add(datePanelExp);
        jPanel.add(datePanelDisabled);

        // Make the frame visible
        frame.setVisible(true);

    }

}
