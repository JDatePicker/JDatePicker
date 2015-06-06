package org.jdatepicker;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.jdatepicker.graphics.JNextIcon;
import org.jdatepicker.graphics.JPreviousIcon;

/**
 * Created by jheyns on 2014/10/16.
 */
public class ComponentIconDefaults {

    private static String CLEAR = "/org/jdatepicker/icons/clear.png";

    private Icon clearIcon;
    private Icon nextMonthIconEnabled;
    private Icon nextYearIconEnabled;
    private Icon previousMonthIconEnabled;
    private Icon previousYearIconEnabled;
    private Icon nextMonthIconDisabled;
    private Icon nextYearIconDisabled;
    private Icon previousMonthIconDisabled;
    private Icon previousYearIconDisabled;
    private Icon popupButtonIcon;

    public ComponentIconDefaults() {
        try {
            // TODO consider making all the icons vector images which will scale
            clearIcon = loadIcon(CLEAR);
            nextMonthIconEnabled = new JNextIcon(4, 7, false, true);
            nextYearIconEnabled = new JNextIcon(8, 7, true, true);
            previousMonthIconEnabled = new JPreviousIcon(4, 7, false, true);
            previousYearIconEnabled = new JPreviousIcon(8, 7, true, true);
            nextMonthIconDisabled = new JNextIcon(4, 7, false, false);
            nextYearIconDisabled = new JNextIcon(8, 7, true, false);
            previousMonthIconDisabled = new JPreviousIcon(4, 7, false, false);
            previousYearIconDisabled = new JPreviousIcon(8, 7, true, false);
            popupButtonIcon = null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Icon loadIcon(String path) throws IOException {
        InputStream stream = ComponentIconDefaults.class.getResourceAsStream(path);
        try {
            BufferedImage image = ImageIO.read(stream);
            return new ImageIcon(image);
        }
        finally {
            stream.close();
        }
    }

    public Icon getClearIcon() {
        return clearIcon;
    }

    public void setClearIcon(Icon clearIcon) {
        this.clearIcon = clearIcon;
    }

    public Icon getNextMonthIconEnabled() {
        return nextMonthIconEnabled;
    }

    public void setNextMonthIconEnabled(Icon nextMonthIconEnabled) {
        this.nextMonthIconEnabled = nextMonthIconEnabled;
    }

    public Icon getNextMonthIconDisabled() {
        return nextMonthIconDisabled;
    }

    public void setNextMonthIconDisabled(Icon nextMonthIconDisabled) {
        this.nextMonthIconDisabled = nextMonthIconDisabled;
    }

    public Icon getNextYearIconEnabled() {
        return nextYearIconEnabled;
    }

    public void setNextYearIconEnabled(Icon nextYearIconEnabled) {
        this.nextYearIconEnabled = nextYearIconEnabled;
    }

    public Icon getNextYearIconDisabled() {
        return nextYearIconDisabled;
    }

    public void setNextYearIconDisabled(Icon nextYearIconDisabled) {
        this.nextYearIconDisabled = nextYearIconDisabled;
    }

    public Icon getPreviousMonthIconEnabled() {
        return previousMonthIconEnabled;
    }

    public void setPreviousMonthIconEnabled(Icon previousMonthIconEnabled) {
        this.previousMonthIconEnabled = previousMonthIconEnabled;
    }

    public Icon getPreviousMonthIconDisabled() {
        return previousMonthIconDisabled;
    }

    public void setPreviousMonthIconDisabled(Icon previousMonthIconDisabled) {
        this.previousMonthIconDisabled = previousMonthIconDisabled;
    }

    public Icon getPreviousYearIconEnabled() {
        return previousYearIconEnabled;
    }

    public void setPreviousYearIconEnabled(Icon previousYearIconEnabled) {
        this.previousYearIconEnabled = previousYearIconEnabled;
    }

    public Icon getPreviousYearIconDisabled() {
        return previousYearIconDisabled;
    }

    public void setPreviousYearIconDisabled(Icon previousYearIconDisabled) {
        this.previousYearIconDisabled = previousYearIconDisabled;
    }

    public Icon getPopupButtonIcon() {
        return popupButtonIcon;
    }

    public void setPopupButtonIcon(Icon popupButtonIcon) {
        this.popupButtonIcon = popupButtonIcon;
    }
}
