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
    private Icon nextMonthIconD;
    private Icon nextMonthIconE;
    private Icon nextYearIconD;
    private Icon nextYearIconE;
    private Icon previousMonthIconD;
    private Icon previousMonthIconE;
    private Icon previousYearIconD;
    private Icon previousYearIconE;
    private Icon popupButtonIcon;

    public ComponentIconDefaults() {
        try {
            //TODO consider making all the icons vector images which will scale
            clearIcon = loadIcon(CLEAR);
            nextMonthIconE = new JNextIcon(4, 7, false, true);
            nextYearIconE = new JNextIcon(8, 7, true, true);
            previousMonthIconE = new JPreviousIcon(4, 7, false, true);
            previousYearIconE = new JPreviousIcon(8, 7, true, true);
            nextMonthIconD = new JNextIcon(4, 7, false, false);
            nextYearIconD = new JNextIcon(8, 7, true, false);
            previousMonthIconD = new JPreviousIcon(4, 7, false, false);
            previousYearIconD = new JPreviousIcon(8, 7, true, false);
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

	public Icon getNextMonthIconE() {
		return nextMonthIconE;
	}

	public void setNextMonthIconE(Icon nextMonthIconE) {
		this.nextMonthIconE = nextMonthIconE;
	}

	public Icon getNextMonthIconD() {
		return nextMonthIconD;
	}

	public void setNextMonthIconD(Icon nextMonthIconD) {
		this.nextMonthIconD = nextMonthIconD;
	}

	public Icon getNextYearIconE() {
		return nextYearIconE;
	}

	public void setNextYearIconE(Icon nextYearIconE) {
		this.nextYearIconE = nextYearIconE;
	}

	public Icon getNextYearIconD() {
		return nextYearIconD;
	}

	public void setNextYearIconD(Icon nextYearIconD) {
		this.nextYearIconD = nextYearIconD;
	}

	public Icon getPreviousMonthIconE() {
		return previousMonthIconE;
	}

	public void setPreviousMonthIconE(Icon previousMonthIconE) {
		this.previousMonthIconE = previousMonthIconE;
	}

	public Icon getPreviousMonthIconD() {
		return previousMonthIconD;
	}

	public void setPreviousMonthIconD(Icon previousMonthIconD) {
		this.previousMonthIconD = previousMonthIconD;
	}

	public Icon getPreviousYearIconE() {
		return previousYearIconE;
	}

	public void setPreviousYearIconE(Icon previousYearIconE) {
		this.previousYearIconE = previousYearIconE;
	}

	public Icon getPreviousYearIconD() {
		return previousYearIconD;
	}

	public void setPreviousYearIconD(Icon previousYearIconD) {
		this.previousYearIconD = previousYearIconD;
	}

	public Icon getPopupButtonIcon() {
		return popupButtonIcon;
	}

	public void setPopupButtonIcon(Icon popupButtonIcon) {
		this.popupButtonIcon = popupButtonIcon;
	}
}
