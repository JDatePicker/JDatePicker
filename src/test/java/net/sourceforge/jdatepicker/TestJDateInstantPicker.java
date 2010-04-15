package net.sourceforge.jdatepicker;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.sourceforge.jdatepicker.util_calendar.JDatePickerBuilder;

public class TestJDateInstantPicker {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
		JFrame testFrame = new JFrame();
		testFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		testFrame.setSize(500, 500);
		JPanel jPanel = new JPanel();
		JDateInstantPicker<Calendar> picker = JDatePickerBuilder.buildJDateInstantPicker();
		jPanel.add(picker);
		JPanel DatePanel = new JPanel();
		DatePanel.setLayout(new BorderLayout());
		DatePanel.add(jPanel, BorderLayout.WEST);
		BorderLayout fb = new BorderLayout();
		testFrame.setLayout(fb);
		testFrame.getContentPane().add(DatePanel, BorderLayout.WEST);
		testFrame.setVisible(true);
	}

}
