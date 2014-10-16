package org.jdatepicker;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by jheyns on 2014/10/16.
 */
public class ComponentTextDefaults {

    public static String TODAY = "text.today";
    public static String MONTH = "text.month";
    public static String YEAR = "text.year";
    public static String CLEAR = "text.clear";

    private Properties texts;

    public ComponentTextDefaults() {
        texts = toProperties(ResourceBundle.getBundle("org.jdatepicker.i18n.Text", Locale.getDefault()));
    }

    public ComponentTextDefaults(Locale locale) {
        texts = toProperties(ResourceBundle.getBundle("org.jdatepicker.i18n.Text", locale));
    }

    public ComponentTextDefaults(String baseName, Locale locale) {
        texts = toProperties(ResourceBundle.getBundle(baseName, locale));
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

    public String getText(String key) {
        return texts.getProperty(key);
    }

    public void setText(String key, String value) {
        texts.setProperty(key, value);
    }

}
