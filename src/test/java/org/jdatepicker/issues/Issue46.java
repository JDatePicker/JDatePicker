package org.jdatepicker.issues;

import org.jdatepicker.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Created by jheyns on 2014/10/07.
 */
public class Issue46 {

    public static void main(String[] args) {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        testFrame.setSize(500, 500);

        final DatePicker picker = new JDatePicker();
        picker.setTextEditable(true);
        picker.setShowYearButtons(true);

        final DatePanel panel = new JDatePanel();
        panel.setShowYearButtons(true);

        picker.getModel().setYear(2010);
        picker.getModel().setMonth(1);
        picker.getModel().setDay(15);
        picker.getModel().setSelected(true);

        JButton button = new JButton("Enable / disable");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDatePicker pickerImpl = (JDatePicker) picker;
                JDatePanel panelImpl = (JDatePanel) panel;

                pickerImpl.setEnabled(!pickerImpl.isEnabled());
                panelImpl.setEnabled(!panelImpl.isEnabled());
            }
        });
        testFrame.setLayout(new BorderLayout());
        testFrame.getContentPane().add((JComponent) picker, BorderLayout.NORTH);
        testFrame.getContentPane().add((JComponent) panel, BorderLayout.CENTER);
        testFrame.getContentPane().add(button, BorderLayout.SOUTH);
        testFrame.setVisible(true);
    }

}
