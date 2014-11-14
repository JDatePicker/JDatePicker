package org.jdatepicker;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePickerImpl;

public class TestIssue19 extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        new TestIssue19().setVisible(true);
    }

    public TestIssue19() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new FlowLayout());

        // create components
        final Container cont = getContentPane();
        final JDatePickerImpl picker = (JDatePickerImpl) new DefaultComponentFactory()
                .createJDatePicker();
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
