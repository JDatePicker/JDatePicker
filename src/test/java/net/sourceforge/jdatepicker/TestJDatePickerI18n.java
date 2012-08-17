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
		
		
		System.out.println("Fully supported locales:");
		System.out.println(new Locale("nl"));
		System.out.println(new Locale("en"));
		System.out.println(new Locale("de"));
		System.out.println(new Locale("it"));
		System.out.println(new Locale("ja"));
		System.out.println(new Locale("ko"));
		System.out.println(new Locale("pt"));
		System.out.println(new Locale("es"));
		System.out.println(new Locale("sv"));
		System.out.println(new Locale("th"));
		
		System.out.println("Resizing locales (needs work):");
		System.out.println(new Locale("zh"));
		System.out.println(new Locale("fr"));
		
		System.out.println("RTL locales (needs work):");
		System.out.println(new Locale("ar"));
		System.out.println(new Locale("he"));
		
		
		Locale.setDefault(new Locale("th"));
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
