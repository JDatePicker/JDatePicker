package org.jdatepicker;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Layout tests for JDatePicker.
 *
 * Addresses issue #61: Text disappearing while resizing JTextField.
 */
public class JDatePickerLayoutTest {

    @Test
    void testDefaultPreferredHeightMatchesChildren() {
        JDatePicker picker = new JDatePicker();
        JFormattedTextField textField = (JFormattedTextField) picker.getComponent(0);
        JButton button = (JButton) picker.getComponent(1);

        int pickerHeight = picker.getPreferredSize().height;

        // The default look: button and text field share the same height,
        // and the picker is exactly that tall.
        assertEquals(button.getPreferredSize().height, textField.getPreferredSize().height,
            "Text field and button should have the same default height");
        assertEquals(button.getPreferredSize().height, pickerHeight,
            "Default picker height should equal the button height");
    }

    @Test
    void testMaximumHeightEqualsPreferredHeight() {
        JDatePicker picker = new JDatePicker();

        assertEquals(picker.getPreferredSize().height, picker.getMaximumSize().height,
            "Picker maximum height should equal its preferred height");
    }

    @Test
    void testChildrenKeepPreferredHeightWhenPickerIsTaller() {
        JDatePicker picker = new JDatePicker();
        Component textField = picker.getComponent(0);
        Component button = picker.getComponent(1);

        Dimension pref = picker.getPreferredSize();
        picker.setSize(pref.width, 3 * pref.height);
        picker.doLayout();

        assertEquals(textField.getPreferredSize().height, textField.getBounds().height,
            "Text field should keep its preferred height when the picker is taller");
        assertEquals(button.getPreferredSize().height, button.getBounds().height,
            "Button should keep its preferred height when the picker is taller");
    }

    @Test
    void testTallerTextFieldIsNotClipped() {
        JDatePicker picker = new JDatePicker();
        Component textField = picker.getComponent(0);
        Component button = picker.getComponent(1);

        textField.setPreferredSize(new Dimension(100, 100));

        assertTrue(picker.getPreferredSize().height >= 100,
            "Picker preferred height should fit the text field, was " + picker.getPreferredSize().height);

        picker.setSize(picker.getPreferredSize());
        picker.doLayout();

        Rectangle bounds = textField.getBounds();
        assertTrue(bounds.y >= 0, "Text field top should be inside the picker, was " + bounds.y);
        assertTrue(bounds.y + bounds.height <= picker.getHeight(),
            "Text field bottom (" + (bounds.y + bounds.height) + ") should be inside the picker height ("
                + picker.getHeight() + ")");
        assertEquals(button.getPreferredSize().height, button.getBounds().height,
            "Button should keep its preferred height next to a taller text field");
    }
}
