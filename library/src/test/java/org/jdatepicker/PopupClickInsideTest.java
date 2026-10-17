package org.jdatepicker;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the logic used to decide if a mouse click is inside the date picker popup.
 *
 * Addresses issue:
 * - #41: Double clicking month label hides the popup
 *
 * The month label opens a JPopupMenu that is not a child of the date panel. A click on
 * that menu (or on one of its items) must still count as a click inside the popup.
 * These tests use only lightweight components, so they run headless.
 */
public class PopupClickInsideTest {

    @Test
    void testComponentsInsideRoot() {
        JPanel root = new JPanel();
        JLabel label = new JLabel("January");
        root.add(label);

        assertTrue(JDatePicker.isInside(root, root), "Root should be inside itself");
        assertTrue(JDatePicker.isInside(label, root), "Child label should be inside root");
    }

    @Test
    void testPopupMenuInvokedFromInsideRoot() {
        JPanel root = new JPanel();
        JLabel label = new JLabel("January");
        root.add(label);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("February");
        popupMenu.add(menuItem);
        popupMenu.setInvoker(label);

        assertTrue(JDatePicker.isInside(popupMenu, root), "Popup menu invoked from root should be inside root");
        assertTrue(JDatePicker.isInside(menuItem, root), "Menu item of popup invoked from root should be inside root");
    }

    @Test
    void testComponentsOutsideRoot() {
        JPanel root = new JPanel();
        JLabel label = new JLabel("January");
        root.add(label);
        JPanel unrelated = new JPanel();
        JButton unrelatedButton = new JButton("Other");
        unrelated.add(unrelatedButton);
        JPopupMenu outsidePopupMenu = new JPopupMenu();
        JMenuItem outsideMenuItem = new JMenuItem("Other");
        outsidePopupMenu.add(outsideMenuItem);
        outsidePopupMenu.setInvoker(unrelatedButton);
        JPopupMenu noInvokerPopupMenu = new JPopupMenu();

        assertFalse(JDatePicker.isInside(unrelated, root), "Unrelated panel should not be inside root");
        assertFalse(JDatePicker.isInside(unrelatedButton, root), "Unrelated button should not be inside root");
        assertFalse(JDatePicker.isInside(outsidePopupMenu, root), "Popup menu invoked from outside should not be inside root");
        assertFalse(JDatePicker.isInside(outsideMenuItem, root), "Menu item of popup invoked from outside should not be inside root");
        assertFalse(JDatePicker.isInside(noInvokerPopupMenu, root), "Popup menu without invoker should not be inside root");
        assertFalse(JDatePicker.isInside(null, root), "Null source should not be inside root");
    }

}
