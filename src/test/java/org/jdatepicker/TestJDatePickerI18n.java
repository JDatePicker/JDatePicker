package org.jdatepicker;

import java.awt.BorderLayout;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class TestJDatePickerI18n {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) { }
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        testFrame.setSize(500, 500);
        JPanel jPanel = new JPanel();
        
        Locale.setDefault(new Locale("pt"));
        DatePicker picker = new DefaultComponentFactory().createJDatePicker();
        picker.setTextEditable(true);
        picker.setShowYearButtons(true);
//        picker.getModel().setSelected(true);
        jPanel.add((JComponent)picker);
        JPanel DatePanel = new JPanel();
        DatePanel.setLayout(new BorderLayout());
        DatePanel.add(jPanel, BorderLayout.WEST);
        BorderLayout fb = new BorderLayout();
        testFrame.setLayout(fb);
        testFrame.getContentPane().add(DatePanel, BorderLayout.WEST);
        testFrame.setVisible(true);
    }

}
