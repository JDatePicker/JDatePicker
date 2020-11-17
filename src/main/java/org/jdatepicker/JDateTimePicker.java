package org.jdatepicker;

import org.jdatepicker.constraints.DateSelectionConstraint;
import org.jdatepicker.constraints.TimeSelectionConstraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Set;

public class JDateTimePicker extends JComponent implements DateTimePanel {

    private final JDatePicker datePicker;
    private final JTimePicker timePicker;
    private final DateTimeModel<?> model;

    public JDateTimePicker(DateTimeModel<?> model) {
        this.model = model;
        this.datePicker = new JDatePicker(model.getDateModel());
        this.timePicker = new JTimePicker(model.getTimeModel());
        setLayout(new BorderLayout());
        add(datePicker, BorderLayout.CENTER);
        add(timePicker, BorderLayout.EAST);
    }

    @Override
    public void addActionListener(ActionListener actionListener) {
        datePicker.addActionListener(actionListener);
        timePicker.addActionListener(actionListener);
    }

    @Override
    public void removeActionListener(ActionListener actionListener) {
        datePicker.removeActionListener(actionListener);
        timePicker.removeActionListener(actionListener);
    }

    @Override
    public DateTimeModel<?> getModel() {
        return model;
    }

    @Override
    public void setShowTimePicker(boolean showTimePanel) {
        timePicker.setVisible(showTimePanel);
        revalidate();
        repaint(getVisibleRect());
    }

    @Override
    public boolean isShowTimePicker() {
        return timePicker.isVisible();
    }

    @Override
    public void setShowDatePicker(boolean showDatePicker) {
        datePicker.setVisible(showDatePicker);
    }

    @Override
    public boolean isShowDatePicker() {
        return datePicker.isVisible();
    }

    @Override
    public void addTimeSelectionConstraint(TimeSelectionConstraint constraint) {
        timePicker.addTimeSelectionConstraint(constraint);
    }

    @Override
    public void removeTimeSelectionConstraint(TimeSelectionConstraint constraint) {
        timePicker.removeTimeSelectionConstraint(constraint);
    }

    @Override
    public void addDateSelectionConstraint(DateSelectionConstraint constraint) {
        datePicker.addDateSelectionConstraint(constraint);
    }

    @Override
    public void removeDateSelectionConstraint(DateSelectionConstraint constraint) {
        datePicker.removeDateSelectionConstraint(constraint);
    }

    @Override
    public void removeAllDateSelectionConstraints() {
        datePicker.removeAllDateSelectionConstraints();
    }

    @Override
    public Set<DateSelectionConstraint> getDateSelectionConstraints() {
        return datePicker.getDateSelectionConstraints();
    }

    @Override
    public void setShowYearButtons(boolean showYearButtons) {
        datePicker.setShowYearButtons(showYearButtons);
    }

    @Override
    public boolean isShowYearButtons() {
        return datePicker.isShowYearButtons();
    }

    @Override
    public void setShowYearSpinner(boolean showYearSpinner) {
        datePicker.setShowYearSpinner(showYearSpinner);
    }

    @Override
    public boolean isShowYearSpinner() {
        return datePicker.isShowYearSpinner();
    }

    @Override
    public void setDoubleClickAction(boolean doubleClickAction) {
        datePicker.setDoubleClickAction(doubleClickAction);
    }

    @Override
    public boolean isDoubleClickAction() {
        return datePicker.isDoubleClickAction();
    }

    @Override
    public void setShowTodayLabel(boolean showTodayLabel) {
        datePicker.setShowTodayLabel(showTodayLabel);
    }

    @Override
    public boolean isShowTodayLabel() {
        return datePicker.isShowTodayLabel();
    }

    @Override
    public void setAllowResetDate(boolean allowResetDate) {
        datePicker.setAllowResetDate(allowResetDate);
    }

    @Override
    public boolean isAllowResetDateAllowed() {
        return datePicker.isAllowResetDateAllowed();
    }

    @Override
    public void setEnableMinutes(boolean enableMinutes) {
        timePicker.setEnableMinutes(enableMinutes);
    }

    @Override
    public boolean isMinutesEnabled() {
        return timePicker.isMinutesEnabled();
    }

    @Override
    public void setEnableSeconds(boolean enableSeconds) {
        timePicker.setEnableSeconds(enableSeconds);
    }

    @Override
    public boolean isSecondsEnabled() {
        return timePicker.isSecondsEnabled();
    }

    @Override
    public void setEnableNanoseconds(boolean enableNanoSeconds) {
        timePicker.setEnableNanoseconds(enableNanoSeconds);
    }

    @Override
    public boolean isNanoSecondsEnabled() {
        return timePicker.isNanoSecondsEnabled();
    }

    @Override
    public void setAlwaysShowSpinner(boolean alwaysShowSpinner) {
        timePicker.setAlwaysShowSpinner(alwaysShowSpinner);
    }

    @Override
    public boolean isAlwaysShowSpinner() {
        return timePicker.isAlwaysShowSpinner();
    }

    @Override
    public void setEnabled(boolean enabled) {
        datePicker.setEnabled(enabled);
        timePicker.setEnabled(enabled);
        super.setEnabled(enabled);
    }
}
