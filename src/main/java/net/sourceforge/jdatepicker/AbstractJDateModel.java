package net.sourceforge.jdatepicker;

import java.util.HashSet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created 18 April 2010
 * 
 * @author Juan Heyns
 *
 * @param <T>
 */
public abstract class AbstractJDateModel<T> implements JDateModel<T> {
	
	private HashSet<ChangeListener> changeListeners;

	protected AbstractJDateModel() {
		changeListeners = new HashSet<ChangeListener>();		
	}
	
	public void addChangeListener(ChangeListener changeListener) {
		changeListeners.add(changeListener);
	}

	public void removeChangeListener(ChangeListener changeListener) {
		changeListeners.remove(changeListener);
	}

	/**
	 * Called internally when changeListeners should be notified.
	 */
	protected void fireChangeEvent() {
		for (ChangeListener changeListener : changeListeners) {
			changeListener.stateChanged(new ChangeEvent(this));
		}
	}
	
}
