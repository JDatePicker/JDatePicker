package org.jdatepicker;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SwingPropertyChangeSupport {

    private final Object source;
    private final Set<ChangeListener> changeListeners = new HashSet<>();
    private final Set<PropertyChangeListener> propertyChangeListeners = new HashSet<>();

    public SwingPropertyChangeSupport(Object sourceBean) {
        this.source = sourceBean;
    }

    public synchronized void addChangeListener(ChangeListener changeListener) {
        changeListeners.add(changeListener);
    }

    public synchronized void removeChangeListener(ChangeListener changeListener) {
        changeListeners.remove(changeListener);
    }

    public synchronized void fireChangeEvent() {
        for (ChangeListener changeListener : changeListeners) {
            changeListener.stateChanged(new ChangeEvent(this));
        }
    }

    public synchronized void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (Objects.equals(oldValue, newValue)) {
            return;
        }

        for (PropertyChangeListener listener : propertyChangeListeners) {
            listener.propertyChange(new PropertyChangeEvent(source, propertyName, oldValue, newValue));
        }
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeListeners.add(listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeListeners.remove(listener);
    }
}
