package org.jdatepicker;

import org.jdatepicker.constraints.DateSelectionConstraint;
import org.jdatepicker.constraints.TimeSelectionConstraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Set;

public class JDateTimePicker extends JComponent implements DateTimePanel {

    private final JDatePicker datePicker;
    private final JTimePicker timePanel;
    private final DateTimeModel<?> model;

    public JDateTimePicker(DateTimeModel<?> model) {
        this.model = model;
        this.datePicker = new JDatePicker(model.getDateModel());
        this.timePanel = new JTimePicker(model.getTimeModel());
        setLayout(new FlowLayout());
        add(datePicker);
        add(timePanel);
    }

    @Override
    public void addActionListener(ActionListener actionListener) {
        datePicker.addActionListener(actionListener);
        timePanel.addActionListener(actionListener);
    }

    @Override
    public void removeActionListener(ActionListener actionListener) {
        datePicker.removeActionListener(actionListener);
        timePanel.removeActionListener(actionListener);
    }

    @Override
    public DateTimeModel<?> getModel() {
        return model;
    }

    @Override
    public void setShowTimePicker(boolean showTimePanel) {
        timePanel.setVisible(showTimePanel);
        revalidate();
        repaint(getVisibleRect());
    }

    @Override
    public boolean isShowTimePicker() {
        return timePanel.isVisible();
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
        timePanel.addTimeSelectionConstraint(constraint);
    }

    @Override
    public void removeTimeSelectionConstraint(TimeSelectionConstraint constraint) {
        timePanel.removeTimeSelectionConstraint(constraint);
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
        timePanel.setEnableMinutes(enableMinutes);
    }

    @Override
    public boolean isMinutesEnabled() {
        return timePanel.isMinutesEnabled();
    }

    @Override
    public void setEnableSeconds(boolean enableSeconds) {
        timePanel.setEnableSeconds(enableSeconds);
    }

    @Override
    public boolean isSecondsEnabled() {
        return timePanel.isSecondsEnabled();
    }

    @Override
    public void setEnableNanoseconds(boolean enableNanoSeconds) {
        timePanel.setEnableNanoseconds(enableNanoSeconds);
    }

    @Override
    public boolean isNanoSecondsEnabled() {
        return timePanel.isNanoSecondsEnabled();
    }

    @Override
    public void setAlwaysShowSpinner(boolean alwaysShowSpinner) {
        timePanel.setAlwaysShowSpinner(alwaysShowSpinner);
    }

    @Override
    public boolean isAlwaysShowSpinner() {
        return timePanel.isAlwaysShowSpinner();
    }

    @Override
    public void setEnabled(boolean enabled) {
        datePicker.setEnabled(enabled);
        timePanel.setEnabled(enabled);
        super.setEnabled(enabled);
    }
}
