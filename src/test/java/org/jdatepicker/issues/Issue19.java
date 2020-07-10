package org.jdatepicker.issues;

import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Issue19 extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        new Issue19().setVisible(true);
    }

    public Issue19() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new FlowLayout());

        // create components
        final Container cont = getContentPane();
        final JDatePicker picker = new JDatePicker();
        final JButton right = new JButton("show / hide");

        // add button action
        right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                picker.setVisible(!picker.isVisible());
            }
        });

        // set size for elements
        picker.setPreferredSize(new Dimension(350, 30));
        right.setPreferredSize(new Dimension(100, 30));

        // add all elements to frame
        cont.add(picker);
        cont.add(right);
    }
}
