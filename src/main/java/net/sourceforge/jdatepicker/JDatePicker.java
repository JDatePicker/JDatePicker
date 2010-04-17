package net.sourceforge.jdatepicker;

import javax.swing.JFormattedTextField;

public interface JDatePicker<T> extends JDateComponent<T> {

	/**
	 * Is the text component editable or not. Defaults to false.
	 * 
	 * @param editable
	 */
	public abstract void setTextEditable(boolean editable);

	/**
	 * Is the text component editable or not.
	 * 
	 * @return
	 */
	public abstract boolean isTextEditable();

	/**
	 * Sets the button to be focusable. Defaults to true.
	 * 
	 * @param focusable
	 */
	public abstract void setButtonFocusable(boolean focusable);

	/**
	 * Is the button focusable.
	 * 
	 * @return
	 */
	public abstract boolean getButtonFocusable();

	/**
	 * Get the panel which draws the calendar, you can set additional settings
	 * here.
	 * 
	 * @return
	 */
	public abstract JDatePanel<T> getJDateInstantPanel();

	/**
	 * Get the formatted text field which is used internally to display the date
	 * on the form.
	 * 
	 * @return
	 */
	public abstract JFormattedTextField getJFormattedTextField();

}