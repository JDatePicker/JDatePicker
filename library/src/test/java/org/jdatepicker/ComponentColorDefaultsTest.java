package org.jdatepicker;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ComponentColorDefaults.
 *
 * Addresses issue #116: The month label is unreadable when the system gives a
 * foreground and background with low contrast (black text on navy on KDE).
 */
public class ComponentColorDefaultsTest {

    private static final Color NAVY = new Color(0, 0, 128);

    @Test
    void testBlackOnNavyBecomesWhite() {
        // The issue case: activeCaptionText is black, activeCaption is navy
        Color result = ComponentColorDefaults.readableForeground(Color.BLACK, NAVY);

        assertEquals(Color.WHITE, result,
            "Black text on navy is unreadable, white should be used");
    }

    @Test
    void testWhiteOnNavyIsUnchanged() {
        Color white = new Color(255, 255, 255);
        Color result = ComponentColorDefaults.readableForeground(white, NAVY);

        assertSame(white, result,
            "White text on navy is readable and should not be changed");
    }

    @Test
    void testReadablePairIsUnchanged() {
        Color result = ComponentColorDefaults.readableForeground(Color.BLACK, Color.LIGHT_GRAY);

        assertSame(Color.BLACK, result,
            "Black text on light gray is readable and should not be changed");
    }

    @Test
    void testWhiteOnLightBackgroundBecomesBlack() {
        Color result = ComponentColorDefaults.readableForeground(Color.WHITE, new Color(240, 240, 240));

        assertEquals(Color.BLACK, result,
            "White text on a light background is unreadable, black should be used");
    }

    @Test
    void testDefaultMonthSelectorColorsAreReadable() {
        ComponentColorDefaults defaults = ComponentColorDefaults.getInstance();
        Color fg = defaults.getColor(ComponentColorDefaults.Key.FG_MONTH_SELECTOR);
        Color bg = defaults.getColor(ComponentColorDefaults.Key.BG_MONTH_SELECTOR);

        double ratio = ComponentColorDefaults.contrastRatio(fg, bg);

        assertTrue(ratio >= 4.5,
            "Default month selector colors should have a contrast ratio of at least 4.5, was " + ratio);
    }
}
