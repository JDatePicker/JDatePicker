package org.jdatepicker;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShowNoneButtonTest {

    @Test
    void togglesNoneButtonAndCentersTodayLabelWhenHidden() {
        JDatePanel panel = new JDatePanel();
        JPanel footer = getFooter(panel);
        BorderLayout footerLayout = (BorderLayout) footer.getLayout();

        assertTrue(panel.isShowNoneButton());
        assertEquals(2, footer.getComponentCount());

        panel.setShowNoneButton(false);

        assertFalse(panel.isShowNoneButton());
        assertEquals(1, footer.getComponentCount());
        assertEquals(BorderLayout.CENTER, footerLayout.getConstraints(footer.getComponent(0)));

        panel.setShowNoneButton(true);

        assertTrue(panel.isShowNoneButton());
        assertEquals(2, footer.getComponentCount());
    }

    @Test
    void pickerForwardsNoneButtonVisibility() {
        JDatePicker picker = new JDatePicker();

        assertTrue(picker.isShowNoneButton());
        picker.setShowNoneButton(false);
        assertFalse(picker.isShowNoneButton());
    }

    private JPanel getFooter(JDatePanel panel) {
        JPanel internalView = (JPanel) panel.getComponent(0);
        BorderLayout viewLayout = (BorderLayout) internalView.getLayout();
        return (JPanel) viewLayout.getLayoutComponent(BorderLayout.SOUTH);
    }
}