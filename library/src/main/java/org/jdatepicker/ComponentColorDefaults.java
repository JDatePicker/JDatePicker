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

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class ComponentColorDefaults {

    private static volatile ComponentColorDefaults instance;

    public static ComponentColorDefaults getInstance() {
        if (instance == null) {
            synchronized (ComponentColorDefaults.class) {
                if (instance == null) {
                    instance = new ComponentColorDefaults();
                }
            }
        }
        return instance;
    }

    public enum Key {
        FG_MONTH_SELECTOR,
        BG_MONTH_SELECTOR,
        FG_GRID_HEADER,
        BG_GRID_HEADER,
        FG_GRID_THIS_MONTH,
        FG_GRID_OTHER_MONTH,
        FG_GRID_TODAY,
        BG_GRID,
        BG_GRID_NOT_SELECTABLE,
        FG_GRID_SELECTED,
        BG_GRID_SELECTED,
        FG_GRID_TODAY_SELECTED,
        BG_GRID_TODAY_SELECTED,
        FG_TODAY_SELECTOR_ENABLED,
        FG_TODAY_SELECTOR_DISABLED,
        BG_TODAY_SELECTOR,
        POPUP_BORDER;
    }

    private Map<Key, Color> colors;

    private ComponentColorDefaults() {
        colors = new HashMap<Key, Color>();

        colors.put(Key.FG_MONTH_SELECTOR, readableForeground(SystemColor.activeCaptionText, SystemColor.activeCaption));
        colors.put(Key.BG_MONTH_SELECTOR, SystemColor.activeCaption);

        colors.put(Key.FG_GRID_HEADER, new Color(10, 36, 106));
        colors.put(Key.BG_GRID_HEADER, Color.LIGHT_GRAY);

        colors.put(Key.FG_GRID_THIS_MONTH, Color.BLACK);
        colors.put(Key.FG_GRID_OTHER_MONTH, Color.LIGHT_GRAY);
        colors.put(Key.FG_GRID_TODAY, Color.RED);
        colors.put(Key.BG_GRID, Color.WHITE);
        colors.put(Key.BG_GRID_NOT_SELECTABLE, new Color(240, 240, 240));

        colors.put(Key.FG_GRID_SELECTED, Color.WHITE);
        colors.put(Key.BG_GRID_SELECTED, new Color(10, 36, 106));

        colors.put(Key.FG_GRID_TODAY_SELECTED, Color.RED);
        colors.put(Key.BG_GRID_TODAY_SELECTED, new Color(10, 36, 106));

        colors.put(Key.FG_TODAY_SELECTOR_ENABLED, Color.BLACK);
        colors.put(Key.FG_TODAY_SELECTOR_DISABLED, Color.LIGHT_GRAY);
        colors.put(Key.BG_TODAY_SELECTOR, Color.WHITE);

        colors.put(Key.POPUP_BORDER, Color.BLACK);
    }

    public Color getColor(Key key) {
        return colors.get(key);
    }

    public void setColor(Key key, Color color) {
        colors.put(key, color);
    }

    /**
     * Returns the foreground if it is readable on the background (WCAG contrast
     * ratio of at least 4.5:1), otherwise black or white, whichever has the higher
     * contrast against the background.
     */
    static Color readableForeground(Color foreground, Color background) {
        if (foreground == null || background == null) {
            return foreground;
        }
        if (contrastRatio(foreground, background) >= 4.5) {
            return foreground;
        }
        return contrastRatio(Color.BLACK, background) >= contrastRatio(Color.WHITE, background)
                ? Color.BLACK
                : Color.WHITE;
    }

    /**
     * WCAG 2.x contrast ratio between two colors, from 1.0 to 21.0.
     */
    static double contrastRatio(Color a, Color b) {
        double la = relativeLuminance(a);
        double lb = relativeLuminance(b);
        return (Math.max(la, lb) + 0.05) / (Math.min(la, lb) + 0.05);
    }

    private static double relativeLuminance(Color color) {
        return 0.2126 * linearise(color.getRed())
                + 0.7152 * linearise(color.getGreen())
                + 0.0722 * linearise(color.getBlue());
    }

    private static double linearise(int component) {
        double c = component / 255.0;
        return c <= 0.03928 ? c / 12.92 : Math.pow((c + 0.055) / 1.055, 2.4);
    }

}
