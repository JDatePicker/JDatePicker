package net.sourceforge.jdatepicker;

public interface JDatePanel<T> extends JDateComponent<T> {

	/**
	 * Sets the visibilty of the Year navigation buttons. Defaults to false.
	 * 
	 * @param showYearButtons
	 */
	public abstract void setShowYearButtons(boolean showYearButtons);

	/**
	 * Is the year navigation buttons active.
	 * 
	 * @return visiblity of the year
	 */
	public abstract boolean isShowYearButtons();

	/**
	 * This changes the behaviour of the control to require a double click on
	 * actionable clicks. If this is set the ActionEvent will only be thrown
	 * when double clicked on a date. Defaults to true.
	 * 
	 * @param doubleClickAction
	 */
	public abstract void setDoubleClickAction(boolean doubleClickAction);

	/**
	 * Is a double click required to throw a ActionEvent.
	 * 
	 * @return
	 */
	public abstract boolean isDoubleClickAction();

}