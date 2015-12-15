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
package org.jdatepicker;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 25 Mar 2004
 * Refactored 21 Jun 2004
 * Refactored 14 May 2009
 * Refactored 16 April 2010
 * Updated 26 April 2010
 * Updated 10 August 2012
 * Updated 6 Jun 2015
 *
 * @author Juan Heyns
 * @author JC Oosthuizen
 * @author Yue Huang
 */
public class JDatePicker extends JComponent implements DatePicker {

    private static final long serialVersionUID = 2814777654384974503L;

    private Popup popup;
    private JFormattedTextField formattedTextField;
    private JButton button;

    private JDatePanel datePanel;

    /**
     * Create a JDatePicker with a default calendar model.
     */
    public JDatePicker() {
        this(new JDatePanel());
    }

    /**
     * Create a JDatePicker with an initial value, with a UtilCalendarModel.
     *
     * @param value the initial value
     */
    public JDatePicker(Calendar value) {
        this(new JDatePanel(value));
    }

    /**
     * Create a JDatePicker with an initial value, with a UtilDateModel.
     *
     * @param value the initial value
     */
    public JDatePicker(java.util.Date value) {
        this(new JDatePanel(value));
    }

    /**
     * Create a JDatePicker with an initial value, with a SqlDateModel.
     *
     * @param value the initial value
     */
    public JDatePicker(java.sql.Date value) {
        this(new JDatePanel(value));
    }

    /**
     * Create a JDatePicker with a custom date model.
     *
     * @param model a custom date model
     */
    public JDatePicker(DateSelectionModel model) {
        this(new JDatePanel(model));
    }


    /**
     * You are able to set the format of the date being displayed on the label.
     * Formatting is described at:
     *
     * @param datePanel The DatePanel to use
     */
    private JDatePicker(JDatePanel datePanel) {
        this.datePanel = datePanel;

        //Initialise Variables
        popup = null;
        datePanel.setBorder(BorderFactory.createLineBorder(getColors().getColor(ComponentColorDefaults.Key.POPUP_BORDER)));
        InternalEventHandler internalEventHandler = new InternalEventHandler();

        //Create Layout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        //Create and Add Components
        //Add and Configure TextField
        formattedTextField = new JFormattedTextField(new DateComponentFormatter());
        setTextFieldValue(formattedTextField, datePanel.getModel());
        formattedTextField.setEditable(false);
        add(formattedTextField);
        layout.putConstraint(SpringLayout.WEST, formattedTextField, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, this, 0, SpringLayout.SOUTH, formattedTextField);

        //Add and Configure Button
        button = new JButton();
        button.setFocusable(true);
        Icon icon = ComponentIconDefaults.getInstance().getPopupButtonIcon();
        button.setIcon(icon);
        if (icon == null) {
            // reset to caption
            button.setText("...");
        } else {
            // remove text
            button.setText("");
        }
        add(button);
        layout.putConstraint(SpringLayout.WEST, button, 1, SpringLayout.EAST, formattedTextField);
        layout.putConstraint(SpringLayout.EAST, this, 0, SpringLayout.EAST, button);
        layout.putConstraint(SpringLayout.SOUTH, this, 0, SpringLayout.SOUTH, button);

        //Do layout formatting
        int h = (int) button.getPreferredSize().getHeight();
        int w = (int) datePanel.getPreferredSize().getWidth();
        button.setPreferredSize(new Dimension(h, h));
        formattedTextField.setPreferredSize(new Dimension(w - h - 1, h));

        //Add event listeners
        addHierarchyBoundsListener(internalEventHandler);
//TODO        addAncestorListener(listener)
        button.addActionListener(internalEventHandler);
        formattedTextField.addPropertyChangeListener("value", internalEventHandler);
        datePanel.getModel().addDateSelectionModelListener(internalEventHandler);
        long eventMask = MouseEvent.MOUSE_PRESSED;
        Toolkit.getDefaultToolkit().addAWTEventListener(internalEventHandler, eventMask);
    }

    private static ComponentColorDefaults getColors() {
        return ComponentColorDefaults.getInstance();
    }

    public DateSelectionModel getModel() {
        return datePanel.getModel();
    }

    /* (non-Javadoc)
     * @see org.jdatepicker.JDatePicker#setTextEditable(boolean)
     */
    public void setTextEditable(boolean editable) {
        formattedTextField.setEditable(editable);
    }

    /* (non-Javadoc)
     * @see org.jdatepicker.JDatePicker#isTextEditable()
     */
    public boolean isTextEditable() {
        return formattedTextField.isEditable();
    }

    /* (non-Javadoc)
     * @see org.jdatepicker.JDatePicker#setButtonFocusable(boolean)
     */
    public void setButtonFocusable(boolean focusable) {
        button.setFocusable(focusable);
    }

    /* (non-Javadoc)
     * @see org.jdatepicker.JDatePicker#getButtonFocusable()
     */
    public boolean getButtonFocusable() {
        return button.isFocusable();
    }

    /* (non-Javadoc)
     * @see org.jdatepicker.JDatePicker#getJDateInstantPanel()
     */
    public DatePanel getJDateInstantPanel() {
        return datePanel;
    }

    /**
     * Called internally to popup the dates.
     */
    private void showPopup() {
        if (popup == null) {
            PopupFactory fac = new PopupFactory();
            Point xy = getLocationOnScreen();
            datePanel.setVisible(true);
            popup = fac.getPopup(this, datePanel, (int) xy.getX(), (int) (xy.getY() + this.getHeight()));
            popup.show();
        }
    }

    /**
     * Called internally to hide the popup dates.
     */
    private void hidePopup() {
        if (popup != null) {
            popup.hide();
            popup = null;
        }
    }

    private Set<Component> getAllComponents(Component component) {
        Set<Component> children = new HashSet<Component>();
        children.add(component);
        if (component instanceof Container) {
            Container container = (Container) component;
            Component[] components = container.getComponents();
            for (int i = 0; i < components.length; i++) {
                children.addAll(getAllComponents(components[i]));
            }
        }
        return children;
    }

    public boolean isDoubleClickAction() {
        return datePanel.isDoubleClickAction();
    }

    public boolean isShowYearButtons() {
        return datePanel.isShowYearButtons();
    }

    public void setDoubleClickAction(boolean doubleClickAction) {
        datePanel.setDoubleClickAction(doubleClickAction);
    }

    public void setShowYearButtons(boolean showYearButtons) {
        datePanel.setShowYearButtons(showYearButtons);
    }

    private void setTextFieldValue(JFormattedTextField textField, DateSelectionModel model) {
        if (model.getSelectionCount() == 0) {
            textField.setValue(null);
        } else {
            textField.setValue(model.getValue());
        }
    }

    public int getTextfieldColumns() {
        return formattedTextField.getColumns();
    }

    public void setTextfieldColumns(int columns) {
        formattedTextField.setColumns(columns);
    }

    @Override
    public void setVisible(boolean aFlag) {
        if (!aFlag) {
            hidePopup();
        }
        super.setVisible(aFlag);
    }

    @Override
    public void setEnabled(boolean enabled) {
        button.setEnabled(enabled);
        datePanel.setEnabled(enabled);
        formattedTextField.setEnabled(enabled);

        super.setEnabled(enabled);
    }

    /**
     * This internal class hides the public event methods from the outside
     */
    private class InternalEventHandler implements ActionListener, HierarchyBoundsListener, ChangeListener, PropertyChangeListener, AWTEventListener, DateSelectionModelListener {

        public void ancestorMoved(HierarchyEvent event) {
            hidePopup();
        }

        public void ancestorResized(HierarchyEvent event) {
            hidePopup();
        }

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == button) {
                if (popup == null) {
                    showPopup();
                } else {
                    hidePopup();
                }
            }
        }

        public void selectionChanged(DateSelectionModelEvent event) {
            if (event.getSource() == datePanel) {
                hidePopup();
            }
        }

        public void stateChanged(ChangeEvent event) {
            if (event.getSource() == datePanel.getModel()) {
                setTextFieldValue(formattedTextField, datePanel.getModel());
            }
        }

        public void propertyChange(PropertyChangeEvent event) {
            // Short circuit if the following cases are found
            if (event.getOldValue() == null && event.getNewValue() == null) {
                return;
            }
            if (event.getOldValue() != null && event.getOldValue().equals(event.getNewValue())) {
                return;
            }
            if (!formattedTextField.isEditable()) {
                return;
            }

            // If the field is editable and we need to parse the date entered
            if (event.getNewValue() != null) {
                Calendar newValue = (Calendar) event.getNewValue();
                // check constraints
                if (!datePanel.getModel().isDateSelectable(newValue)) {
                    // rollback
                    // TODO I think this needs to be replaced by an exception, otherwise it will trigger another event.
                    formattedTextField.setValue(event.getOldValue());
                    return;
                }
                datePanel.getModel().replaceSelectedDates(Arrays.asList(newValue));
            }

            // Clearing textfield will also fire change event
            if (event.getNewValue() == null) {
                // Set model value unselected, this will fire an event
                getModel().clearSelectedDates();
            }
        }

        public void eventDispatched(AWTEvent event) {
            if (MouseEvent.MOUSE_CLICKED == event.getID() && event.getSource() != button) {
                Set<Component> components = getAllComponents(datePanel);
                boolean clickInPopup = false;
                for (Component component : components) {
                    if (event.getSource() == component) {
                        clickInPopup = true;
                    }
                }
                if (!clickInPopup) {
                    hidePopup();
                }
            }
        }

    }

}
