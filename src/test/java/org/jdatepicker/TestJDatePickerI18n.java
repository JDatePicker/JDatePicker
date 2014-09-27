package org.jdatepicker;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class TestJDatePickerI18n {

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
		
		Locale.setDefault(new Locale("pt"));
		JDatePicker picker = new JDateComponentFactory().createJDatePicker();
		picker.setTextEditable(true);
		picker.setShowYearButtons(true);
//		picker.getModel().setSelected(true);
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
