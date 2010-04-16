/**
Copyright 2004 Juan Heyns. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY JUAN HEYNS ``AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JUAN HEYNS OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Juan Heyns.
*/
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
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Created on 25 Mar 2004
 * Refactored 21 Jun 2004
 * Refactored 14 May 2009
 * Refactored 16 April 2010
 * 
 * @author Juan Heyns
 * @author JC Oosthuizen
 * @author Yue Huang
 * @param <T>
 */
public abstract class JDatePicker<T> extends JPanel implements JDateComponent<T> {

	private static final long serialVersionUID = 2814777654384974503L;
	
	private Popup popup;
	private JFormattedTextField dateTextField;
	private JButton editButton;
	
	private JDatePanel<T> dateInstantPanel;
	private InternalEventHandler internalEventHandler;

	private JFormattedTextField.AbstractFormatter dateFormat;

	protected JDatePicker(JDatePanel<T> dateInstantPanel) {
		this.dateInstantPanel = dateInstantPanel;

		//Initialise Variables
		popup = null;
		dateInstantPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		internalEventHandler = new InternalEventHandler();
		dateFormat = createDefaultFormatter();

		//Create Layout
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		//Create and Add Components
		//Add and Configure TextField
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		dateTextField = new JFormattedTextField(dateFormat);
		dateTextField.setValue(dateInstantPanel.getValue());
		gridbag.setConstraints(dateTextField, c);
		dateTextField.setEditable(false);
		dateTextField.setHorizontalAlignment(JTextField.CENTER);
		add(dateTextField);

		//Add and Configure Button
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		editButton = new JButton("...");
		editButton.setFocusable(false);
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
	
	/**
	 * You are able to set the format of the date being displayed on the label.
	 * Formatting is described at:
	 * 
	 * http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html
	 * 
	 * @param dateFormat
	 */
	public void setDateFormat(JFormattedTextField.AbstractFormatter dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	/**
	 * Get the set date format.
	 * 
	 * @return
	 */
	public JFormattedTextField.AbstractFormatter getDateFormat() {
		return dateFormat;
	}
	
	/**
	 * Needs to be implemented for each type of date object.
	 * 
	 * @return
	 */
	protected abstract JFormattedTextField.AbstractFormatter createDefaultFormatter();

	/**
	 * Is the text component editable or not. Defaults to false.
	 * 
	 * @param editable
	 */
	public void setTextEditable(boolean editable) {
		dateTextField.setEditable(editable);
	}
	
	/**
	 * Is the text component editable or not.
	 * 
	 * @return
	 */
	public boolean isTextEditable() {
		return dateTextField.isEditable();
	}
	
	/**
	 * Sets the button to be focusable. Defaults to false.
	 * 
	 * @param focusable
	 */
	public void setButtonFocusable(boolean focusable) {
		editButton.setFocusable(focusable);
	}
	
	/**
	 * Is the button focusable.
	 * 
	 * @return
	 */
	public boolean getButtonFocusable() {
		return editButton.isFocusable();
	}

	/**
	 * Get the panel which draws the calendar, you can set additional settings
	 * here.
	 * 
	 * @return
	 */
	public JDatePanel<T> getJDateInstantPanel() {
		return dateInstantPanel;
	}

	/**
	 * Get the formatted text field which is used internally to display the date
	 * on the form.
	 * 
	 * @return
	 */
	public JFormattedTextField getJFormattedTextField() {
		return dateTextField;
	}

	/**
	 * Called internally to popup the dates.
	 */
	@SuppressWarnings("unchecked")
	private void showPopup() {
		if (popup == null){
			if (dateTextField.isEditable() && dateTextField.getValue() != null) {
				dateInstantPanel.setValue((T)dateTextField.getValue());
			}
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
			dateTextField.setValue(dateInstantPanel.getValue());
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
			if (arg0.getSource() == editButton){
				if (popup == null) {
					showPopup();
				}
				else {
					hidePopup();
				}
			} 
			else if (arg0.getSource() == dateInstantPanel){
				hidePopup();
			}
		}

		public void stateChanged(ChangeEvent arg0) {
			if (arg0.getSource() == dateInstantPanel) {
				dateTextField.setValue(dateInstantPanel.getValue());
			}
		}
		
	}
	
}
