package net.sourceforge.jdatepicker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.text.ParseException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class JDateInstantPicker<T> extends JPanel implements JDateInstantComponent<T> {

	private static final long serialVersionUID = 2814777654384974503L;
	
	private Popup popup;
	private JTextField dateTextField;
	private JButton editButton;
	
	private JDateInstantPanel<T> dateInstantPanel;
	
	private InternalEventHandler internalEventHandler;

	protected JDateInstantPicker(JDateInstantPanel<T> dateInstantPanel) {
		this.dateInstantPanel = dateInstantPanel;

		//Initialise Variables
		popup = null;
		dateInstantPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		internalEventHandler = new InternalEventHandler();

		//Create Layout
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		//Create and Add Components
		//Add and Configure TextField
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		dateTextField = new JTextField(dateInstantPanel.getStringValue());
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
		int w = (int)dateInstantPanel.getPreferredSize().getWidth();
		editButton.setPreferredSize(new Dimension(h,h));
		dateTextField.setPreferredSize(new Dimension(w-h+1,h));

		//Add event listeners
		editButton.addActionListener(internalEventHandler);
		addHierarchyBoundsListener(internalEventHandler);
		dateInstantPanel.addChangeListener(internalEventHandler);
		dateInstantPanel.addActionListener(internalEventHandler);
	}	
	
	public void addActionListener(ActionListener actionListener) {
		dateInstantPanel.addActionListener(actionListener);
	}

	public void removeActionListener(ActionListener actionListener) {
		dateInstantPanel.removeActionListener(actionListener);
	}

	public void addChangeListener(ChangeListener changeListener) {
		dateInstantPanel.addChangeListener(changeListener);
	}

	public void removeChangeListener(ChangeListener changeListener) {
		dateInstantPanel.removeChangeListener(changeListener);
	}

	public void setI18nStrings(Properties i18nStrings) {
		dateInstantPanel.setI18nStrings(i18nStrings);
	}
	
	public Properties getI18nStrings() {
		return dateInstantPanel.getI18nStrings();
	}

	public void setValue(T value) {
		dateInstantPanel.setValue(value);
	}
	
	public T getValue() {
		return dateInstantPanel.getValue();
	}
	
	public void setStringValue(String value) throws ParseException {
		dateInstantPanel.setStringValue(value);
	}
	
	public String getStringValue() {
		return dateInstantPanel.getStringValue();
	}

	public void setDateFormat(String dateFormat) {
		dateInstantPanel.setDateFormat(dateFormat);
	}

	/**
	 * Called internally to popup the dates.
	 */
	private void showPopup() {
		if (popup == null){
			PopupFactory fac = new PopupFactory();
			Point xy = getLocationOnScreen();
			dateInstantPanel.setVisible(true); 
			popup = fac.getPopup(this, dateInstantPanel, (int) xy.getX(), (int) (xy.getY()+this.getHeight()-1));
			popup.show();
		}
	}
	
	/**
	 * Called internally to hide the popup dates. 
	 */
	private void hidePopup() {
		if (popup != null) {
			dateTextField.setText(dateInstantPanel.getStringValue());
			popup.hide();
			popup = null;		
		}
	}

	/**
	 * This internal class hides the public event methods from the outside 
	 */
	private class InternalEventHandler implements ActionListener, HierarchyBoundsListener, ChangeListener {

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
			else if (arg0.getSource()==dateInstantPanel){
				hidePopup();
			}
		}

		public void stateChanged(ChangeEvent arg0) {
			if (arg0.getSource()==dateInstantPanel)
				dateTextField.setText(dateInstantPanel.getStringValue());
		}
		
	}
	
}
