package org.jdatepicker;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Created by jheyns on 2014/10/07.
 */
public class TestIssueXX {

    public static void main(String[] args) {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        testFrame.setSize(500, 500);
        JPanel jPanel = new JPanel();

        JDatePicker picker = new DefaultComponentFactory().createJDatePicker();
        picker.setTextEditable(true);
        picker.setShowYearButtons(true);
        jPanel.add((JComponent) picker);

        picker.getModel().setYear(2010);
        picker.getModel().setMonth(1);
        //picker.getModel().setMonth(1);
        picker.getModel().setDay(15);
        picker.getModel().setSelected(true);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BorderLayout());
        datePanel.add(jPanel, BorderLayout.WEST);
        BorderLayout fb = new BorderLayout();
        testFrame.setLayout(fb);
        testFrame.getContentPane().add(datePanel, BorderLayout.WEST);
        testFrame.setVisible(true);
    }

}
