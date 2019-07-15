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

import java.text.DateFormat;
import java.util.*;

public final class ComponentTextDefaults {

    private static ComponentTextDefaults instance;

    public static ComponentTextDefaults getInstance() {
        if (instance == null) {
            instance = new ComponentTextDefaults();
        }
        return instance;
    }

    public enum Key {
        // General texts
        TODAY("text.today", "general"),
        MONTH("text.month", "general"),
        YEAR("text.year", "general"),
        CLEAR("text.clear", "general"),

        // Months of the year
        JANUARY("text.january", "month", 0),
        FEBRUARY("text.february", "month", 1),
        MARCH("text.march", "month", 2),
        APRIL("text.april", "month", 3),
        MAY("text.may", "month", 4),
        JUNE("text.june", "month", 5),
        JULY("text.july", "month", 6),
        AUGUST("text.august", "month", 7),
        SEPTEMBER("text.september", "month", 8),
        OCTOBER("text.october", "month", 9),
        NOVEMBER("text.november", "month", 10),
        DECEMBER("text.december", "month", 11),

        // Days of the week abbreviated where necessary
        SUN("text.sun", "dow", Calendar.SUNDAY),
        MON("text.mon", "dow", Calendar.MONDAY),
        TUE("text.tue", "dow", Calendar.TUESDAY),
        WED("text.wed", "dow", Calendar.WEDNESDAY),
        THU("text.thu", "dow", Calendar.THURSDAY),
        FRI("text.fri", "dow", Calendar.FRIDAY),
        SAT("text.sat", "dow", Calendar.SATURDAY);

        private String property;
        private String kind;
        private Integer index;

        private Key(String property, String kind) {
            this.property = property;
            this.kind = kind;
        }

        private Key(String property, String kind, Integer index) {
            this.property = property;
            this.kind = kind;
            this.index = index;
        }

        public String getProperty() {
            return property;
        }

        public String getKind() {
            return kind;
        }

        public Integer getIndex() {
            return index;
        }

        public static Key getMonthKey(int index) {
            for (Key key : values()) {
                if ("month".equals(key.getKind()) && index == key.getIndex()) {
                    return key;
                }
            }
            return null;
        }

        public static Key getDowKey(int index) {
            for (Key key : values()) {
                if ("dow".equals(key.getKind()) && index == key.getIndex()) {
                    return key;
                }
            }
            return null;
        }

    }

    private Properties texts;

    /**
     * Instantiated with the values which is default for the current locale.
     */
    private ComponentTextDefaults() {
        texts = toProperties(ResourceBundle.getBundle("org.jdatepicker.i18n.Text", Locale.getDefault()));
    }

    private Properties toProperties(ResourceBundle resource) {
        Properties result = new Properties();
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            result.put(key, resource.getString(key));
        }
        return result;
    }

    /**
     * For general texts retrieve from the resource bundles.
     *
     * For months and day of the week use the SimpleDateFormat symbols. In most cases these are the correct ones, but
     * we may want to override it, so if a text is specified then we will not consider the SimpleDateFormat symbols.
     *
     * @param key
     * @return
     */
    public String getText(Key key) {
        String text = texts.getProperty(key.getProperty());
        if (text == null && "month".equals(key.getKind())) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH, key.getIndex());
            ComponentFormatDefaults defaults = ComponentFormatDefaults.getInstance();
            DateFormat monthFormat = defaults.getFormat(ComponentFormatDefaults.Key.MONTH_SELECTOR);
            text = monthFormat.format(c.getTime());
        }
        if (text == null && "dow".equals(key.getKind())) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, key.getIndex());
            ComponentFormatDefaults defaults = ComponentFormatDefaults.getInstance();
            DateFormat dowFormat = defaults.getFormat(ComponentFormatDefaults.Key.DOW_HEADER);
            text = dowFormat.format(c.getTime());
        }
        return text;
    }

    public void setText(Key key, String value) {
        texts.setProperty(key.getProperty(), value);
    }

}
