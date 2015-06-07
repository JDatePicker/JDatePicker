package org.jdatepicker;

import java.text.SimpleDateFormat;
import java.util.*;

public class ComponentTextDefaults {

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
        NOVEMBER("text.november",  "month",10),
        DECEMBER("text.december",  "month",11),

        // Days of the week abbreviated where necessary
        SUN("text.sun", "dow", 0),
        MON("text.mon", "dow", 1),
        TUE("text.tue", "dow", 2),
        WED("text.wed", "dow", 3),
        THU("text.thu", "dow", 4),
        FRI("text.fri", "dow", 5),
        SAT("text.sat", "dow", 6);

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
            for (Key key: values()) {
                if ("month".equals(key.getKind()) && index == key.getIndex()) {
                    return key;
                }
            }
            return null;
        }

        public static Key getDowKey(int index) {
            for (Key key: values()) {
                if ("dow".equals(key.getKind()) && index == key.getIndex()) {
                    return key;
                }
            }
            return null;
        }

    }

    private static SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM");
    private static SimpleDateFormat sdfDow = new SimpleDateFormat("EEE");

    private Properties texts;

    /**
     * Instantiated with the values which is default for the current locale.
     */
    public ComponentTextDefaults() {
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
            text = sdfMonth.format(c.getTime());
        }
        if (text == null && "dow".equals(key.getKind())) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, key.getIndex());
            text = sdfDow.format(c.getTime());
        }
        return text;
    }

    public void setText(Key key, String value) {
        texts.setProperty(key.getProperty(), value);
    }

}
