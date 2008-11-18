package net.sourceforge.jdatepicker;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * @author Juan Heyns
 *
 * Created on 25-Mar-2004
 * Refactored 21-Jun-2004
 * 
 */
public class JDatePicker extends JPanel {
	
	/**
	 * Tests the JDatePicker 
	 * @param args
	 */
	public static void main (String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
		JFrame testFrame = new JFrame();
		testFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		testFrame.setSize(600,300);
		JPanel jPanel = new JPanel();
		jPanel.add(new JDatePicker());
		testFrame.getContentPane().add(jPanel);
		testFrame.setVisible(true);
	}

	private EventHandler eventHandler;
	private Component ownerComponent;
	private Popup popup;
	private JTextField dateTextField;
	private JButton editButton;
	private JDatePanel datePanel;

	/**
	 * Constructs a JDatePicker
	 *
	 */
	public JDatePicker(){
		//Call Super Constructor
		super();
		
		//Initialise Variables
		popup = null;
		datePanel = new JDatePanel();
		datePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		eventHandler = new EventHandler();

		//Create Layout
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		//Create and Add Components
		//Add and Configure TextField
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		dateTextField = new JTextField(datePanel.toString());
		gridbag.setConstraints(dateTextField, c);
		dateTextField.setEditable(false);
		dateTextField.setHorizontalAlignment(JTextField.CENTER);
		add(dateTextField);

		//Add and Configure Button
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		editButton = new JButton("...");
		gridbag.setConstraints(dateTextField, c);
		add(editButton);
		
		//Do layout formatting
		setLayout(gridbag);
		int h = (int)editButton.getPreferredSize().getHeight();
		int w = (int)datePanel.getPreferredSize().getWidth();
		editButton.setPreferredSize(new Dimension(h,h));
		dateTextField.setPreferredSize(new Dimension(w-h+1,h));

		//Add event listeners
		editButton.addActionListener(eventHandler);
		addHierarchyBoundsListener(eventHandler);
		datePanel.addChangeListener(eventHandler);
		datePanel.addActionListener(eventHandler);
	}
	
	/**
	 * Prints out the date in the format of the datePanel.toString() 
	 */
	public String toString(){
		return datePanel.toString();
	}
	
	/**
	 * Gets the clone Calendar object with the selected date   
	 * @return Calendar
	 */
	public Calendar getCalendarClone(){
		return datePanel.getCalendarClone();
	}
	
	/**
	 * Called internally to popup the dates.
	 *
	 */
	private void showPopup() {
		if (popup == null){
			PopupFactory fac = new PopupFactory();
			Point xy = getLocationOnScreen();
			datePanel.setVisible(true); 
			popup = fac.getPopup(this, datePanel, (int) xy.getX(), (int) (xy.getY()+this.getHeight()-1));
			popup.show();
		}
	}
	
	/**
	 * Called internally to hide the popup dates. 
	 *
	 */
	private void hidePopup() {
		if (popup != null) {
			dateTextField.setText(datePanel.toString());
			popup.hide();
			popup = null;		
		}
	}

	/**
	 * This internal class hides the public event methods from the outside 
	 */
	private class EventHandler implements ActionListener, HierarchyBoundsListener, ChangeListener {

		public void ancestorMoved(HierarchyEvent arg0) {
			hidePopup();
		}

		public void ancestorResized(HierarchyEvent arg0) {
			hidePopup();
		}

		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource()==editButton){
				if (popup == null) {
					showPopup();
				}
				else {
					hidePopup();
				}
			} 
			else if (arg0.getSource()==datePanel){
				hidePopup();
			}
		}

		public void stateChanged(ChangeEvent arg0) {
			if (arg0.getSource()==datePanel)
				dateTextField.setText(datePanel.toString());
		}
	}

}
