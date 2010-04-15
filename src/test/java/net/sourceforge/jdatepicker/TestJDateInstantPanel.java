package net.sourceforge.jdatepicker;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JFrame;

import net.sourceforge.jdatepicker.util_calendar.JDatePickerBuilder;

public class TestJDateInstantPanel {

	public static void main(String[] args) {
		JFrame testFrame = new JFrame();
		JDateInstantPanel<Calendar> panel = JDatePickerBuilder.buildJDateInstantPanel();
		panel.setShowYearButtons(true);
		testFrame.getContentPane().add(panel);
		panel.setShowYearButtons(false);
		panel.setShowYearButtons(true);

		testFrame.setSize(300,300);
		testFrame.addWindowFocusListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		testFrame.setVisible(true);
	}

}