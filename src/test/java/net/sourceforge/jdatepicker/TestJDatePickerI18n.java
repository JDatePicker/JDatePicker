package net.sourceforge.jdatepicker;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.Properties;

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
		Properties i18n = new Properties();
		i18n.put("messages.today", "Vandag");
		i18n.put("messages.nextMonth", "Volgende maand");
		i18n.put("messages.previousMonth", "Vorige maand");
		i18n.put("messages.nextYear", "Volgende jaar");
		i18n.put("messages.previousYear", "Vorige jaar");
		i18n.put("messages.clear", "Herstel");
		
		Locale.setDefault(Locale.FRENCH);
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
