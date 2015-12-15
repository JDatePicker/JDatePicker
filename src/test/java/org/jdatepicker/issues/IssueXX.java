package org.jdatepicker.issues;

import org.jdatepicker.DatePicker;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jheyns on 2014/10/07.
 */
public class IssueXX {

    public static void main(String[] args) {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        testFrame.setSize(500, 500);
        JPanel jPanel = new JPanel();

        DatePicker picker = new JDatePicker();
        picker.setTextEditable(true);
        picker.setShowYearButtons(true);
        jPanel.add((JComponent) picker);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BorderLayout());
        datePanel.add(jPanel, BorderLayout.WEST);
        BorderLayout fb = new BorderLayout();
        testFrame.setLayout(fb);
        testFrame.getContentPane().add(datePanel, BorderLayout.WEST);
        testFrame.setVisible(true);
    }

}
