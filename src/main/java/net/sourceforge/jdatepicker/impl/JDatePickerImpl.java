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
package net.sourceforge.jdatepicker.impl;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sourceforge.jdatepicker.JDateModel;
import net.sourceforge.jdatepicker.JDatePanel;
import net.sourceforge.jdatepicker.JDatePicker;


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
public class JDatePickerImpl extends JPanel implements JDatePicker {

	private static final long serialVersionUID = 2814777654384974503L;
	
	private Popup popup;
	private JFormattedTextField dateTextField;
	private JButton editButton;
	
	private JDatePanelImpl dateInstantPanel;
	private InternalEventHandler internalEventHandler;

	public JDatePickerImpl(JDatePanelImpl dateInstantPanel) {
		this(dateInstantPanel, null);
	}
	
	/**
	 * You are able to set the format of the date being displayed on the label.
	 * Formatting is described at:
	 * 
	 * http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html
	 * 
	 * @param dateInstantPanel
	 * @param dateFormat
	 */
	public JDatePickerImpl(JDatePanelImpl dateInstantPanel, JFormattedTextField.AbstractFormatter dateFormat) {
		this.dateInstantPanel = dateInstantPanel;

		//Initialise Variables
		popup = null;
		dateInstantPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		internalEventHandler = new InternalEventHandler();

		//Create Layout
		SpringLayout layout = new SpringLayout();
        setLayout(layout);

        //Create and Add Components
		//Add and Configure TextField
//		dateTextField = new JFormattedTextField((dateFormat!=null) ? dateFormat : createDefaultFormatter());
        dateTextField = new JFormattedTextField();
		dateTextField.setValue(dateInstantPanel.getModel());
		dateTextField.setEditable(false);
		add(dateTextField);
        layout.putConstraint(SpringLayout.WEST, dateTextField, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, this, 0, SpringLayout.SOUTH, dateTextField);

		//Add and Configure Button
		editButton = new JButton("...");
		editButton.setFocusable(true);
		add(editButton);
        layout.putConstraint(SpringLayout.WEST, editButton, 1, SpringLayout.EAST, dateTextField);
        layout.putConstraint(SpringLayout.EAST, this, 0, SpringLayout.EAST, editButton);
        layout.putConstraint(SpringLayout.SOUTH, this, 0, SpringLayout.SOUTH, editButton);
		
		//Do layout formatting
		int h = (int)editButton.getPreferredSize().getHeight();
		int w = (int)dateInstantPanel.getPreferredSize().getWidth();
		editButton.setPreferredSize(new Dimension(h, h));
		dateTextField.setPreferredSize(new Dimension(w-h-1, h));

		//Add event listeners
		editButton.addActionListener(internalEventHandler);
		addHierarchyBoundsListener(internalEventHandler);
		dateInstantPanel.getModel().addChangeListener(internalEventHandler);
		dateInstantPanel.addActionListener(internalEventHandler);
	}	

	public void addActionListener(ActionListener actionListener) {
		dateInstantPanel.addActionListener(actionListener);
	}

	public void removeActionListener(ActionListener actionListener) {
		dateInstantPanel.removeActionListener(actionListener);
	}

	public void setI18nStrings(Properties i18nStrings) {
		dateInstantPanel.setI18nStrings(i18nStrings);
	}
	
	public Properties getI18nStrings() {
		return dateInstantPanel.getI18nStrings();
	}

	public JDateModel<?> getModel() {
		return dateInstantPanel.getModel();
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#setTextEditable(boolean)
	 */
	public void setTextEditable(boolean editable) {
		dateTextField.setEditable(editable);
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#isTextEditable()
	 */
	public boolean isTextEditable() {
		return dateTextField.isEditable();
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#setButtonFocusable(boolean)
	 */
	public void setButtonFocusable(boolean focusable) {
		editButton.setFocusable(focusable);
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#getButtonFocusable()
	 */
	public boolean getButtonFocusable() {
		return editButton.isFocusable();
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#getJDateInstantPanel()
	 */
	public JDatePanel getJDateInstantPanel() {
		return dateInstantPanel;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#getJFormattedTextField()
	 */
	public JFormattedTextField getJFormattedTextField() {
		return dateTextField;
	}

	/**
	 * Called internally to popup the dates.
	 */
	private void showPopup() {
		if (popup == null){
//TODO			if (dateTextField.isEditable() && dateTextField.getValue() != null) {
//				dateInstantPanel.getModel() setModel(dateTextField.getValue());
//			}
			PopupFactory fac = new PopupFactory();
			Point xy = getLocationOnScreen();
			dateInstantPanel.setVisible(true); 
			popup = fac.getPopup(this, dateInstantPanel, (int) xy.getX(), (int) (xy.getY()+this.getHeight()));
			popup.show();
		}
	}
	
	/**
	 * Called internally to hide the popup dates. 
	 */
	private void hidePopup() {
		if (popup != null) {
			dateTextField.setValue(dateInstantPanel.getModel());
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
				dateTextField.setValue(dateInstantPanel.getModel());
			}
		}
		
	}

	public boolean isDoubleClickAction() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isShowYearButtons() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDoubleClickAction(boolean doubleClickAction) {
		// TODO Auto-generated method stub
		
	}

	public void setShowYearButtons(boolean showYearButtons) {
		// TODO Auto-generated method stub
		
	}
	
}
