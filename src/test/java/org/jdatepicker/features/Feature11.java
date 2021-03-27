package org.jdatepicker.features;


import javax.swing.*;
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

        // Create a first JDatePanel without a border that is marking today.
        // As the border is a static feature (just like a colored today number)
        // a border is still displayed because it is subsequently set. 
        final JDatePanel datePanel = new JDatePanel();
        
        // Create a second JDatePanel with border marking today
        final JDatePanel datePanelWithBorder = new JDatePanel();
        //Set a border marking today in DatePanel
        datePanelWithBorder.setStaticTodayBorder(true);
        
        // Create a third JDatePanel with border marking today but disabled
        final JDatePanel datePanelWithBorderDis = new JDatePanel();
        //Statically setting a border in DatePanel is redundant:
        datePanelWithBorderDis.setStaticTodayBorder(true);
        datePanelWithBorderDis.setEnabled(false);
        
        // add the DatePanel to the layout panel of the frame
        jPanel.add(datePanel);
        jPanel.add(datePanelWithBorder);
        jPanel.add(datePanelWithBorderDis);

        // Make the frame visible
        frame.setVisible(true);

    }

}
