package org.jdatepicker;

import org.jdatepicker.graphics.JNextIcon;
import org.jdatepicker.graphics.JPreviousIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jheyns on 2014/10/16.
 */
public class ComponentIconDefaults {

    private static String CLEAR = "/org/jdatepicker/icons/clear.png";

    private Icon clearIcon;
    private Icon nextMonthIcon;
    private Icon nextYearIcon;
    private Icon previousMonthIcon;
    private Icon previousYearIcon;
    private Icon popupButtonIcon;

    public ComponentIconDefaults() {
        try {
            //TODO consider making all the icons vector images which will scale
            clearIcon = loadIcon(CLEAR);
            nextMonthIcon = new JNextIcon(4, 7, false);
            nextYearIcon = new JNextIcon(8, 7, true);
            previousMonthIcon = new JPreviousIcon(4, 7, false);
            previousYearIcon = new JPreviousIcon(8, 7, true);
            popupButtonIcon = null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Icon loadIcon(String path) throws IOException {
        InputStream stream = ComponentIconDefaults.class.getResourceAsStream(path);
        BufferedImage image = ImageIO.read(stream);
        return new ImageIcon(image);
    }

    public Icon getClearIcon() {
        return clearIcon;
    }

    public void setClearIcon(Icon clearIcon) {
        this.clearIcon = clearIcon;
    }

    public Icon getNextMonthIcon() {
        return nextMonthIcon;
    }

    public void setNextMonthIcon(Icon nextMonthIcon) {
        this.nextMonthIcon = nextMonthIcon;
    }

    public Icon getNextYearIcon() {
        return nextYearIcon;
    }

    public void setNextYearIcon(Icon nextYearIcon) {
        this.nextYearIcon = nextYearIcon;
    }

    public Icon getPreviousMonthIcon() {
        return previousMonthIcon;
    }

    public void setPreviousMonthIcon(Icon previousMonthIcon) {
        this.previousMonthIcon = previousMonthIcon;
    }

    public Icon getPreviousYearIcon() {
        return previousYearIcon;
    }

    public void setPreviousYearIcon(Icon previousYearIcon) {
        this.previousYearIcon = previousYearIcon;
    }

    public Icon getPopupButtonIcon() {
        return popupButtonIcon;
    }

    public void setPopupButtonIcon(Icon popupButtonIcon) {
        this.popupButtonIcon = popupButtonIcon;
    }

}
