package org.jdatepicker;

import org.jdatepicker.constraints.TimeSelectionConstraint;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

public class JTimePanel extends JComponent implements TimePanel {

    private boolean isMinutesEnabled;
    private boolean isSecondsEnabled;
    private boolean isNanoSecondsEnabled;
    private boolean isAlwaysShowSpinner;

    private final InternalController internalController;
    private final InternalModel<?> internalModel;

    public JTimePanel(TimeModel<?> model) {
        internalModel = new InternalModel<>(model);
        InternalView<?> internalView = new InternalView<>(internalModel);
        internalController = new InternalController(internalModel, internalView);
        setLayout(new GridLayout(1, 1));
        add(internalView);
    }

    @Override
    public TimePanel setEnableMinutes(boolean enableMinutes) {
        this.isMinutesEnabled = enableMinutes;
        if (enableMinutes) {
            internalController.enableMinutes();
        } else {
            internalController.disableMinutes(!isAlwaysShowSpinner());
            this.isSecondsEnabled = false;
            this.isNanoSecondsEnabled = false;
        }
        return this;
    }

    @Override
    public boolean isMinutesEnabled() {
        return isMinutesEnabled;
    }

    @Override
    public TimePanel setEnableSeconds(boolean enableSeconds) {
        this.isSecondsEnabled = enableSeconds;
        if (enableSeconds) {
            internalController.enableSeconds();
        } else {
            internalController.disableSeconds();
            this.isNanoSecondsEnabled = false;
        }
        return this;
    }

    @Override
    public boolean isSecondsEnabled() {
        return isSecondsEnabled;
    }

    @Override
    public TimePanel setEnableNanoseconds(boolean enableNanoseconds) {
        this.isNanoSecondsEnabled = enableNanoseconds;
        if (enableNanoseconds) {
            internalController.enableNanoseconds();
        } else {
            internalController.disableNanoseconds();
        }
        return this;
    }

    @Override
    public boolean isNanoSecondsEnabled() {
        return isNanoSecondsEnabled;
    }

    @Override
    public TimePanel setAlwaysShowSpinner(boolean alwaysShowSpinner) {
        this.isAlwaysShowSpinner = alwaysShowSpinner;
        return this;
    }

    @Override
    public boolean isAlwaysShowSpinner() {
        return isAlwaysShowSpinner;
    }

    @Override
    public void addActionListener(ActionListener actionListener) {
        internalModel.addActionListener(actionListener);
    }

    @Override
    public void removeActionListener(ActionListener actionListener) {
        internalModel.removeActionListener(actionListener);
    }

    @Override
    public TimeModel<?> getModel() {
        return null;
    }

    @Override
    public void addTimeSelectionConstraint(TimeSelectionConstraint constraint) {
        internalModel.addTimeSelectionConstraint(constraint);
    }

    @Override
    public void removeTimeSelectionConstraint(TimeSelectionConstraint constraint) {
        internalModel.removeTimeSelectionConstraint(constraint);
    }

    @SuppressWarnings("unchecked")
    private static class InternalModel<T> implements SpinnerModel, ComboBoxModel<T>, ChangeListener {

        private final Set<ActionListener> actionListeners = new HashSet<>();
        private final Set<ChangeListener> spinnerChangeListeners = new HashSet<>();
        private final Set<TimeSelectionConstraint> timeSelectionConstraints = new HashSet<>();
        private final TimeModel<T> timeModel;
        private final Vector<T> fullHours;
        private ModelResolution resolution = ModelResolution.Hour;

        private InternalModel(TimeModel<T> timeModel) {
            this.timeModel = timeModel;
            this.fullHours = timeModel.getFullHours().stream().map(it -> (T) it).collect(Collectors.toCollection(Vector::new));
            this.timeModel.addChangeListener(this);
            setValue(timeModel.getValue());
        }

        public void setSpinnerModelResolution(ModelResolution resolution) {
            this.resolution = resolution;
        }

        public ModelResolution getSpinnerModelResolution() {
            return resolution;
        }

        @Override
        public void setSelectedItem(Object o) {
            setValue(o);
        }

        @Override
        public Object getSelectedItem() {
            return timeModel.getValue();
        }

        @Override
        public Object getValue() {
            return timeModel.getValue();
        }

        @Override
        public void setValue(Object o) {
            if (o == null) {
                return;
            }
            T oldValue = timeModel.getValue();
            if (Objects.equals(oldValue, o)) {
                return;
            }
            if (o instanceof String) {
                String value = (String) o;
                if (value.isEmpty()) {
                    return;
                }
                timeModel.setValueFromString(value);
            } else {
                T value = (T) o;
                timeModel.setValue(value);
            }
            if (!isValidSelection(timeModel)) {
                timeModel.setValue(oldValue);
            } else {
                timeModel.setSelected(true);
            }
            fireActionPerformed();
        }

        @Override
        public Object getNextValue() {
            return getValuePlus(1);
        }

        @Override
        public Object getPreviousValue() {
            return getValuePlus(-1);
        }

        @Override
        public void addChangeListener(ChangeListener changeListener) {
            spinnerChangeListeners.add(changeListener);
        }

        @Override
        public void removeChangeListener(ChangeListener changeListener) {
            spinnerChangeListeners.remove(changeListener);
        }

        public void addActionListener(ActionListener actionListener) {
            actionListeners.add(actionListener);
        }

        public void removeActionListener(ActionListener actionListener) {
            actionListeners.remove(actionListener);
        }

        public void addTimeSelectionConstraint(TimeSelectionConstraint constraint) {
            timeSelectionConstraints.add(constraint);
        }

        public void removeTimeSelectionConstraint(TimeSelectionConstraint constraint) {
            timeSelectionConstraints.remove(constraint);
        }

        private Object getValuePlus(int add) {
            switch (resolution) {
                case Hour:
                    return timeModel.getValuePlusHours(add);
                case Minute:
                    return timeModel.getValuePlusMinutes(add);
                case Second:
                    return timeModel.getValuePlusSeconds(add);
                case Nano:
                    return timeModel.getValuePlusNanoseconds(add);
            }
            throw new RuntimeException("Resolution not implemented.");
        }

        private boolean isValidSelection(TimeModel<T> model) {
            return timeSelectionConstraints.stream()
                    .allMatch(it -> it.isValidSelection(model));
        }

        private void fireActionPerformed() {
            actionListeners.forEach(it -> it.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Tine selected")));
        }

        @Override
        public int getSize() {
            return fullHours.size();
        }

        @Override
        public T getElementAt(int i) {
            return fullHours.get(i);
        }

        @Override
        public void addListDataListener(ListDataListener listDataListener) {
        }

        @Override
        public void removeListDataListener(ListDataListener listDataListener) {
        }

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            spinnerChangeListeners.forEach(it -> it.stateChanged(changeEvent));
        }

        public enum ModelResolution {
            Hour,
            Minute,
            Second,
            Nano
        }
    }

    private static class InternalView<T> extends JPanel {

        private static final String COMBOBOX_SELECTOR_KEY = "combobox-selector";
        private static final String SPINNER_SELECTOR_KEY = "spinner-selector";

        public InternalView(InternalModel<T> model) {
            JSpinner timeSpinner = new JSpinner();
            timeSpinner.setModel(model);
            JComboBox<T> timeComboBox = new JComboBox<>();
            timeComboBox.setModel(model);
            setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            setLayout(new CardLayout());
            add(timeComboBox, COMBOBOX_SELECTOR_KEY);
            add(timeSpinner, SPINNER_SELECTOR_KEY);
        }

        public void showComboBoxSelector() {
            show(COMBOBOX_SELECTOR_KEY);
        }

        public void showSpinnerSelector() {
            show(SPINNER_SELECTOR_KEY);
        }

        private void show(String key) {
            ((CardLayout) getLayout()).show(this, key);
        }
    }

    private static class InternalController {

        private final InternalModel<?> model;
        private final InternalView<?> view;

        private InternalController(InternalModel<?> model, InternalView<?> internalView) {
            this.model = model;
            this.view = internalView;
        }

        public void enableMinutes() {
            model.setSpinnerModelResolution(InternalModel.ModelResolution.Minute);
            view.showSpinnerSelector();
        }

        public void disableMinutes(boolean isComboBoxEnabled) {
            disableSeconds();
            if (InternalModel.ModelResolution.Minute.equals(model.getSpinnerModelResolution())) {
                model.setSpinnerModelResolution(InternalModel.ModelResolution.Hour);
            }
            if (isComboBoxEnabled) {
                view.showComboBoxSelector();
            }
        }

        public void enableSeconds() {
            model.setSpinnerModelResolution(InternalModel.ModelResolution.Second);
            view.showSpinnerSelector();
        }

        public void disableSeconds() {
            disableNanoseconds();
            if (InternalModel.ModelResolution.Second.equals(model.getSpinnerModelResolution())) {
                model.setSpinnerModelResolution(InternalModel.ModelResolution.Minute);
            }
        }

        public void enableNanoseconds() {
            model.setSpinnerModelResolution(InternalModel.ModelResolution.Nano);
            view.showSpinnerSelector();
        }

        public void disableNanoseconds() {
            if (InternalModel.ModelResolution.Nano.equals(model.getSpinnerModelResolution())) {
                model.setSpinnerModelResolution(InternalModel.ModelResolution.Second);
            }
        }
    }
}
