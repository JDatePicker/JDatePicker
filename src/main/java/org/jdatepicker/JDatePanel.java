/**
 Copyright 2004 Juan Heyns. All rights reserved.

 Redistribution and use in source and binary forms, with or without modification, are
 permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this list of
 conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice, this list
 of conditions and the following disclaimer in the documentation and/or other materials
 provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY JUAN HEYNS ``AS IS'' AND ANY EXPRESS OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JUAN HEYNS OR
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those of the
 authors and should not be interpreted as representing official policies, either expressed
 or implied, of Juan Heyns.
 */
package org.jdatepicker;

import org.jdatepicker.constraints.DateSelectionConstraint;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.util.*;

/**
 * Created on 26 Mar 2004
 * Refactored on 21 Jun 2004
 * Refactored on 8 Jul 2004
 * Refactored 14 May 2009
 * Refactored 16 April 2010
 * Updated 18 April 2010
 * Updated 26 April 2010
 * Updated 15 June 2012
 * Updated 10 August 2012
 * Updated 6 Jun 2015
 *
 * @author Juan Heyns
 * @author JC Oosthuizen
 * @author Yue Huang
 */
public class JDatePanel extends JComponent implements DatePanel {

    private static final long serialVersionUID = -2299249311312882915L;

    private Set<ActionListener> actionListeners;
    private Set<DateSelectionConstraint> dateConstraints;

    private boolean showYearButtons;
    private boolean doubleClickAction;
    private int firstDayOfWeek;

    private InternalCalendarModel internalModel;
    private InternalController internalController;
    private InternalView internalView;

    /**
     * Creates a JDatePanel with a default calendar model.
     */
    public JDatePanel() {
        this(createModel());
    }

    /**
     * Create a JDatePanel with an initial value, with a UtilCalendarModel.
     *
     * @param value the initial value
     */
    public JDatePanel(Calendar value) {
        this(createModelFromValue(value));
    }

    /**
     * Create a JDatePanel with an initial value, with a UtilDateModel.
     *
     * @param value the initial value
     */
    public JDatePanel(java.util.Date value) {
        this(createModelFromValue(value));
    }

    /**
     * Create a JDatePanel with an initial value, with a SqlDateModel.
     *
     * @param value the initial value
     */
    public JDatePanel(java.sql.Date value) {
        this(createModelFromValue(value));
    }

    /**
     * Create a JDatePanel with a custom date model.
     *
     * @param model a custom date model
     */
    public JDatePanel(DateModel<?> model) {
        actionListeners = new HashSet<ActionListener>();
        dateConstraints = new HashSet<DateSelectionConstraint>();

        showYearButtons = false;
        doubleClickAction = false;
        firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek();

        internalModel = new InternalCalendarModel(model);
        internalController = new InternalController();
        internalView = new InternalView();

        setLayout(new GridLayout(1, 1));
        add(internalView);
    }

    public static DateModel<Calendar> createModel() {
        return new UtilCalendarModel();
    }

    private static DateModel<Calendar> createModel(Calendar value) {
        return new UtilCalendarModel(value);
    }

    private static DateModel<?> createModelFromValue(Object value) {
        if (value instanceof java.util.Calendar) {
            return new UtilCalendarModel((Calendar) value);
        }
        if (value instanceof java.util.Date) {
            return new UtilDateModel((java.util.Date) value);
        }
        if (value instanceof java.sql.Date) {
            return new SqlDateModel((java.sql.Date) value);
        }
        throw new IllegalArgumentException("No model could be constructed from the initial value object.");
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        actionListeners.remove(actionListener);
    }

    /**
     * Called internally when actionListeners should be notified.
     */
    private void fireActionPerformed() {
        for (ActionListener actionListener : actionListeners) {
            actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Date selected"));
        }
    }

    /* (non-Javadoc)
     * @see org.jdatepicker.JDatePanel#setShowYearButtons(boolean)
     */
    public void setShowYearButtons(boolean showYearButtons) {
        this.showYearButtons = showYearButtons;
        internalView.updateShowYearButtons();
    }

    /* (non-Javadoc)
     * @see org.jdatepicker.JDatePanel#isShowYearButtons()
     */
    public boolean isShowYearButtons() {
        return this.showYearButtons;
    }

    /* (non-Javadoc)
     * @see org.jdatepicker.JDatePanel#setDoubleClickAction(boolean)
     */
    public void setDoubleClickAction(boolean doubleClickAction) {
        this.doubleClickAction = doubleClickAction;
    }

    /* (non-Javadoc)
     * @see org.jdatepicker.JDatePanel#isDoubleClickAction()
     */
    public boolean isDoubleClickAction() {
        return doubleClickAction;
    }

    /* (non-Javadoc)
     * @see org.jdatepicker.JDateComponent#getModel()
     */
    public DateModel<?> getModel() {
        return internalModel.getModel();
    }

    public void addDateSelectionConstraint(DateSelectionConstraint constraint) {
        dateConstraints.add(constraint);
    }

    public void removeDateSelectionConstraint(DateSelectionConstraint constraint) {
        dateConstraints.remove(constraint);
    }

    public void removeAllDateSelectionConstraints() {
        dateConstraints.clear();
    }

    public Set<DateSelectionConstraint> getDateSelectionConstraints() {
        return Collections.unmodifiableSet(dateConstraints);
    }

    protected boolean checkConstraints(DateModel<?> model) {
        for (DateSelectionConstraint constraint : dateConstraints) {
            if (!constraint.isValidSelection(model)) {
                return false;
            }
        }
        return true;
    }

    private static ComponentTextDefaults getTexts() {
        return ComponentTextDefaults.getInstance();
    }

    private static ComponentIconDefaults getIcons() {
        return ComponentIconDefaults.getInstance();
    }

    private static ComponentColorDefaults getColors() {
        return ComponentColorDefaults.getInstance();
    }

    private static ComponentFormatDefaults getFormats() {
        return ComponentFormatDefaults.getInstance();
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);

        if (aFlag) {
            internalView.updateTodayLabel();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        internalView.setEnabled(enabled);

        super.setEnabled(enabled);
    }

    /**
     * Logically grouping the view controls under this internal class.
     *
     * @author Juan Heyns
     */
    private class InternalView extends JPanel {

        private static final long serialVersionUID = -6844493839307157682L;

        private JPanel centerPanel;
        private JPanel northCenterPanel;
        private JPanel northPanel;
        private JPanel southPanel;
        private JPanel previousButtonPanel;
        private JPanel nextButtonPanel;
        private JTable dayTable;
        private JTableHeader dayTableHeader;
        private InternalTableCellRenderer dayTableCellRenderer;
        private JLabel monthLabel;
        private JLabel todayLabel;
        private JLabel noneLabel;
        private JPopupMenu monthPopupMenu;
        private JMenuItem[] monthPopupMenuItems;
        private JButton nextMonthButton;
        private JButton previousMonthButton;
        private JButton previousYearButton;
        private JButton nextYearButton;
        private JSpinner yearSpinner;

        /**
         * Update the scroll buttons UI.
         */
        private void updateShowYearButtons() {
            if (showYearButtons) {
                getNextButtonPanel().add(getNextYearButton());
                getPreviousButtonPanel().removeAll();
                getPreviousButtonPanel().add(getPreviousYearButton());
                getPreviousButtonPanel().add(getPreviousMonthButton());
            } else {
                getNextButtonPanel().remove(getNextYearButton());
                getPreviousButtonPanel().remove(getPreviousYearButton());
            }
        }

        /**
         * Update the UI of the monthLabel
         */
        private void updateMonthLabel() {
            monthLabel.setText(getTexts().getText(ComponentTextDefaults.Key.getMonthKey(internalModel.getModel().getMonth())));
        }

        public InternalView() {
            initialise();
        }

        /**
         * Initialise the control.
         */
        private void initialise() {
            this.setLayout(new java.awt.BorderLayout());
            this.setSize(200, 180);
            this.setPreferredSize(new java.awt.Dimension(200, 180));
            this.setOpaque(false);
            this.add(getNorthPanel(), java.awt.BorderLayout.NORTH);
            this.add(getSouthPanel(), java.awt.BorderLayout.SOUTH);
            this.add(getCenterPanel(), java.awt.BorderLayout.CENTER);
        }

        /**
         * This method initializes northPanel
         *
         * @return javax.swing.JPanel The north panel
         */
        private JPanel getNorthPanel() {
            if (northPanel == null) {
                northPanel = new javax.swing.JPanel();
                northPanel.setLayout(new java.awt.BorderLayout());
                northPanel.setName("");
                northPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
                northPanel.setBackground(getColors().getColor(ComponentColorDefaults.Key.BG_MONTH_SELECTOR));
                northPanel.add(getPreviousButtonPanel(), java.awt.BorderLayout.WEST);
                northPanel.add(getNextButtonPanel(), java.awt.BorderLayout.EAST);
                northPanel.add(getNorthCenterPanel(), java.awt.BorderLayout.CENTER);
            }
            return northPanel;
        }

        /**
         * This method initializes northCenterPanel
         *
         * @return javax.swing.JPanel
         */
        private JPanel getNorthCenterPanel() {
            if (northCenterPanel == null) {
                northCenterPanel = new javax.swing.JPanel();
                northCenterPanel.setLayout(new java.awt.BorderLayout());
                northCenterPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
                northCenterPanel.setOpaque(false);
                northCenterPanel.add(getMonthLabel(), java.awt.BorderLayout.CENTER);
                northCenterPanel.add(getYearSpinner(), java.awt.BorderLayout.EAST);
            }
            return northCenterPanel;
        }

        /**
         * This method initializes monthLabel
         *
         * @return javax.swing.JLabel
         */
        private JLabel getMonthLabel() {
            if (monthLabel == null) {
                monthLabel = new javax.swing.JLabel();
                monthLabel.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_MONTH_SELECTOR));
                monthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                monthLabel.addMouseListener(internalController);
                updateMonthLabel();
            }
            return monthLabel;
        }

        /**
         * This method initializes yearSpinner
         *
         * @return javax.swing.JSpinner
         */
        private JSpinner getYearSpinner() {
            if (yearSpinner == null) {
                yearSpinner = new javax.swing.JSpinner();
                yearSpinner.setModel(internalModel);
            }
            return yearSpinner;
        }

        /**
         * This method initializes southPanel
         *
         * @return javax.swing.JPanel
         */
        private JPanel getSouthPanel() {
            if (southPanel == null) {
                southPanel = new javax.swing.JPanel();
                southPanel.setLayout(new java.awt.BorderLayout());
                southPanel.setBackground(getColors().getColor(ComponentColorDefaults.Key.BG_TODAY_SELECTOR));
                southPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
                southPanel.add(getTodayLabel(), java.awt.BorderLayout.WEST);
                southPanel.add(getNoneLabel(), java.awt.BorderLayout.EAST);
            }
            return southPanel;
        }

        /**
         * This method initializes todayLabel
         *
         * @return javax.swing.JLabel
         */
        private JLabel getNoneLabel() {
            if (noneLabel == null) {
                noneLabel = new javax.swing.JLabel();
                noneLabel.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_TODAY_SELECTOR_ENABLED));
                noneLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                noneLabel.addMouseListener(internalController);
                //TODO get the translations for each language before adding this in
                //noneLabel.setToolTipText(getText(ComponentTextDefaults.CLEAR));
                noneLabel.setIcon(getIcons().getClearIcon());
            }
            return noneLabel;
        }

        private void updateTodayLabel() {
            Calendar now = Calendar.getInstance();
            DateFormat df = getFormats().getFormat(ComponentFormatDefaults.Key.TODAY_SELECTOR);
            todayLabel.setText(getTexts().getText(
                    ComponentTextDefaults.Key.TODAY)
                    + ": "
                    + df.format(now.getTime()));
        }

        /**
         * This method initializes todayLabel
         *
         * @return javax.swing.JLabel
         */
        private JLabel getTodayLabel() {
            if (todayLabel == null) {
                todayLabel = new javax.swing.JLabel();
                todayLabel.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_TODAY_SELECTOR_ENABLED));
                todayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                todayLabel.addMouseListener(internalController);
                updateTodayLabel();
            }
            return todayLabel;
        }

        /**
         * This method initializes centerPanel
         *
         * @return javax.swing.JPanel
         */
        private JPanel getCenterPanel() {
            if (centerPanel == null) {
                centerPanel = new javax.swing.JPanel();
                centerPanel.setLayout(new java.awt.BorderLayout());
                centerPanel.setOpaque(false);
                centerPanel.add(getDayTableHeader(), java.awt.BorderLayout.NORTH);
                centerPanel.add(getDayTable(), java.awt.BorderLayout.CENTER);
            }
            return centerPanel;
        }

        /**
         * This method initializes dayTable
         *
         * @return javax.swing.JTable
         */
        private JTable getDayTable() {
            if (dayTable == null) {
                dayTable = new javax.swing.JTable();
                dayTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
                dayTable.setModel(internalModel);
                dayTable.setShowGrid(true);
                dayTable.setGridColor(getColors().getColor(ComponentColorDefaults.Key.BG_GRID));
                dayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                dayTable.setCellSelectionEnabled(true);
                dayTable.setRowSelectionAllowed(true);
                dayTable.setFocusable(false);
                dayTable.addMouseListener(internalController);
                for (int i = 0; i < 7; i++) {
                    TableColumn column = dayTable.getColumnModel().getColumn(i);
                    column.setCellRenderer(getDayTableCellRenderer());
                }
                dayTable.addComponentListener(new ComponentListener() {

                    public void componentResized(ComponentEvent e) {
                        // The new size of the table
                        final double w = e.getComponent().getSize().getWidth();
                        final double h = e.getComponent().getSize().getHeight();

                        // Set the size of the font as a fraction of the width or the height, whichever is smallest
                        final float sw = (float) Math.floor(w / 16);
                        final float sh = (float) Math.floor(h / 8);
                        dayTable.setFont(dayTable.getFont().deriveFont(Math.min(sw, sh)));

                        // Set the row height as a fraction of the height
                        final int r = (int) Math.floor(h / 6);
                        dayTable.setRowHeight(r);
                    }

                    public void componentMoved(ComponentEvent e) {
                        // Do nothing
                    }

                    public void componentShown(ComponentEvent e) {
                        // Do nothing
                    }

                    public void componentHidden(ComponentEvent e) {
                        // Do nothing
                    }

                });
            }
            return dayTable;
        }

        private InternalTableCellRenderer getDayTableCellRenderer() {
            if (dayTableCellRenderer == null) {
                dayTableCellRenderer = new InternalTableCellRenderer();
            }
            return dayTableCellRenderer;
        }

        private JTableHeader getDayTableHeader() {
            if (dayTableHeader == null) {
                dayTableHeader = getDayTable().getTableHeader();
                dayTableHeader.setResizingAllowed(false);
                dayTableHeader.setReorderingAllowed(false);
                dayTableHeader.setDefaultRenderer(getDayTableCellRenderer());
            }
            return dayTableHeader;
        }

        /**
         * This method initializes previousButtonPanel
         *
         * @return javax.swing.JPanel
         */
        private JPanel getPreviousButtonPanel() {
            if (previousButtonPanel == null) {
                previousButtonPanel = new javax.swing.JPanel();
                java.awt.GridLayout layout = new java.awt.GridLayout(1, 2);
                layout.setHgap(3);
                previousButtonPanel.setLayout(layout);
                previousButtonPanel.setName("");
                previousButtonPanel.setOpaque(false);
                if (isShowYearButtons()) {
                    previousButtonPanel.add(getPreviousYearButton());
                }
                previousButtonPanel.add(getPreviousMonthButton());
            }
            return previousButtonPanel;
        }

        /**
         * This method initializes nextButtonPanel
         *
         * @return javax.swing.JPanel
         */
        private JPanel getNextButtonPanel() {
            if (nextButtonPanel == null) {
                nextButtonPanel = new javax.swing.JPanel();
                java.awt.GridLayout layout = new java.awt.GridLayout(1, 2);
                layout.setHgap(3);
                nextButtonPanel.setLayout(layout);
                nextButtonPanel.setName("");
                nextButtonPanel.setOpaque(false);
                nextButtonPanel.add(getNextMonthButton());
                if (isShowYearButtons()) {
                    nextButtonPanel.add(getNextYearButton());
                }
            }
            return nextButtonPanel;
        }

        /**
         * This method initializes nextMonthButton
         *
         * @return javax.swing.JButton
         */
        private JButton getNextMonthButton() {
            if (nextMonthButton == null) {
                nextMonthButton = new JButton();
                nextMonthButton.setIcon(getIcons().getNextMonthIconEnabled());
                nextMonthButton.setDisabledIcon(getIcons().getNextMonthIconDisabled());
                nextMonthButton.setText("");
                nextMonthButton.setPreferredSize(new java.awt.Dimension(20, 15));
                nextMonthButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                nextMonthButton.setFocusable(false);
                nextMonthButton.setOpaque(true);
                nextMonthButton.addActionListener(internalController);
                nextMonthButton.setToolTipText(getTexts().getText(ComponentTextDefaults.Key.MONTH));
            }
            return nextMonthButton;
        }

        /**
         * This method initializes nextYearButton
         *
         * @return javax.swing.JButton
         */
        private JButton getNextYearButton() {
            if (nextYearButton == null) {
                nextYearButton = new JButton();
                nextYearButton.setIcon(getIcons().getNextYearIconEnabled());
                nextYearButton.setDisabledIcon(getIcons().getNextYearIconDisabled());
                nextYearButton.setText("");
                nextYearButton.setPreferredSize(new java.awt.Dimension(20, 15));
                nextYearButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                nextYearButton.setFocusable(false);
                nextYearButton.setOpaque(true);
                nextYearButton.addActionListener(internalController);
                nextYearButton.setToolTipText(getTexts().getText(ComponentTextDefaults.Key.YEAR));
            }
            return nextYearButton;
        }

        /**
         * This method initializes previousMonthButton
         *
         * @return javax.swing.JButton
         */
        private JButton getPreviousMonthButton() {
            if (previousMonthButton == null) {
                previousMonthButton = new JButton();
                previousMonthButton.setIcon(getIcons().getPreviousMonthIconEnabled());
                previousMonthButton.setDisabledIcon(getIcons().getPreviousMonthIconDisabled());
                previousMonthButton.setText("");
                previousMonthButton.setPreferredSize(new java.awt.Dimension(20, 15));
                previousMonthButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                previousMonthButton.setFocusable(false);
                previousMonthButton.setOpaque(true);
                previousMonthButton.addActionListener(internalController);
                previousMonthButton.setToolTipText(getTexts().getText(ComponentTextDefaults.Key.MONTH));
            }
            return previousMonthButton;
        }

        /**
         * This method initializes previousMonthButton
         *
         * @return javax.swing.JButton
         */
        private JButton getPreviousYearButton() {
            if (previousYearButton == null) {
                previousYearButton = new JButton();
                previousYearButton.setIcon(getIcons().getPreviousYearIconEnabled());
                previousYearButton.setDisabledIcon(getIcons().getPreviousYearIconDisabled());
                previousYearButton.setText("");
                previousYearButton.setPreferredSize(new java.awt.Dimension(20, 15));
                previousYearButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                previousYearButton.setFocusable(false);
                previousYearButton.setOpaque(true);
                previousYearButton.addActionListener(internalController);
                previousYearButton.setToolTipText(getTexts().getText(ComponentTextDefaults.Key.YEAR));
            }
            return previousYearButton;
        }

        /**
         * This method initializes monthPopupMenu
         *
         * @return javax.swing.JPopupMenu
         */
        private JPopupMenu getMonthPopupMenu() {
            if (monthPopupMenu == null) {
                monthPopupMenu = new javax.swing.JPopupMenu();
                JMenuItem[] menuItems = getMonthPopupMenuItems();
                for (int i = 0; i < menuItems.length; i++) {
                    monthPopupMenu.add(menuItems[i]);
                }
            }
            return monthPopupMenu;
        }

        private JMenuItem[] getMonthPopupMenuItems() {
            if (monthPopupMenuItems == null) {
                monthPopupMenuItems = new JMenuItem[12];
                for (int i = 0; i < 12; i++) {
                    JMenuItem mi = new JMenuItem(getTexts().getText(ComponentTextDefaults.Key.getMonthKey(i)));
                    mi.addActionListener(internalController);
                    monthPopupMenuItems[i] = mi;
                }
            }
            return monthPopupMenuItems;
        }

        @Override
        public void setEnabled(boolean enabled) {
            dayTable.setEnabled(enabled);
            dayTableCellRenderer.setEnabled(enabled);
            nextMonthButton.setEnabled(enabled);
            if (nextYearButton != null) {
                nextYearButton.setEnabled(enabled);
            }
            previousMonthButton.setEnabled(enabled);
            if (previousYearButton != null) {
                previousYearButton.setEnabled(enabled);
            }
            yearSpinner.setEnabled(enabled);
            if (enabled) {
                todayLabel.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_TODAY_SELECTOR_ENABLED));
            } else {
                todayLabel.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_TODAY_SELECTOR_DISABLED));
            }

            super.setEnabled(enabled);
        }
    }

    /**
     * This inner class renders the table of the days, setting colors based on
     * whether it is in the month, if it is today, if it is selected etc.
     *
     * @author Juan Heyns
     */
    private class InternalTableCellRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = -2341614459632756921L;

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Exit this method if the value is null, encountered from JTable#AccessibleJTable
            if (value == null) {
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }

            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setHorizontalAlignment(JLabel.CENTER);

            if (row == -1) {
                label.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_GRID_HEADER));
                label.setBackground(getColors().getColor(ComponentColorDefaults.Key.BG_GRID_HEADER));
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }

            Calendar todayCal = Calendar.getInstance();
            Calendar selectedCal = Calendar.getInstance();
            selectedCal.set(internalModel.getModel().getYear(), internalModel.getModel().getMonth(), internalModel.getModel().getDay());

            int cellDayValue = (Integer) value;
            int lastDayOfMonth = selectedCal.getActualMaximum(Calendar.DAY_OF_MONTH);

            // Other month
            if (cellDayValue < 1 || cellDayValue > lastDayOfMonth) {
                label.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_GRID_OTHER_MONTH));

                Calendar calForDay = Calendar.getInstance();
                calForDay.set(internalModel.getModel().getYear(), internalModel.getModel().getMonth(), cellDayValue);
                DateModel<Calendar> modelForDay = new UtilCalendarModel(calForDay);
                label.setBackground(checkConstraints(modelForDay) ?
                                getColors().getColor(ComponentColorDefaults.Key.BG_GRID) :
                                getColors().getColor(ComponentColorDefaults.Key.BG_GRID_NOT_SELECTABLE)
                );

                //Past end of month
                if (cellDayValue > lastDayOfMonth) {
                    label.setText(Integer.toString(cellDayValue - lastDayOfMonth));
                }
                //Before start of month
                else {
                    Calendar lastMonth = new GregorianCalendar();
                    lastMonth.set(selectedCal.get(Calendar.YEAR), selectedCal.get(Calendar.MONTH) - 1, 1);
                    int lastDayLastMonth = lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
                    label.setText(Integer.toString(lastDayLastMonth + cellDayValue));
                }
            }
            //This month
            else {
                label.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_GRID_THIS_MONTH));

                Calendar calForDay = Calendar.getInstance();
                calForDay.set(internalModel.getModel().getYear(), internalModel.getModel().getMonth(), cellDayValue);
                DateModel<Calendar> modelForDay = new UtilCalendarModel(calForDay);
                label.setBackground(checkConstraints(modelForDay) ?
                                getColors().getColor(ComponentColorDefaults.Key.BG_GRID) :
                                getColors().getColor(ComponentColorDefaults.Key.BG_GRID_NOT_SELECTABLE)
                );

                //Today
                if (todayCal.get(Calendar.DATE) == cellDayValue
                        && todayCal.get(Calendar.MONTH) == internalModel.getModel().getMonth()
                        && todayCal.get(Calendar.YEAR) == internalModel.getModel().getYear()) {
                    label.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_GRID_TODAY));
                    //Selected
                    if (internalModel.getModel().isSelected() && selectedCal.get(Calendar.DATE) == cellDayValue) {
                        label.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_GRID_TODAY_SELECTED));
                        label.setBackground(getColors().getColor(ComponentColorDefaults.Key.BG_GRID_TODAY_SELECTED));
                    }
                }
                //Other day
                else {
                    //Selected
                    if (internalModel.getModel().isSelected() && selectedCal.get(Calendar.DATE) == cellDayValue) {
                        label.setForeground(getColors().getColor(ComponentColorDefaults.Key.FG_GRID_SELECTED));
                        label.setBackground(getColors().getColor(ComponentColorDefaults.Key.BG_GRID_SELECTED));
                    }
                }
            }

            return label;
        }

    }

    /**
     * This inner class hides the public view event handling methods from the
     * outside. This class acts as an internal controller for this component. It
     * receives events from the view components and updates the model.
     *
     * @author Juan Heyns
     */
    private class InternalController implements ActionListener, MouseListener {

        /**
         * Next, Previous and Month buttons clicked, causes the model to be
         * updated.
         */
        public void actionPerformed(ActionEvent arg0) {
            if (!JDatePanel.this.isEnabled()) {
                return;
            }

            if (arg0.getSource() == internalView.getNextMonthButton()) {
                internalModel.getModel().addMonth(1);
            } else if (arg0.getSource() == internalView.getPreviousMonthButton()) {
                internalModel.getModel().addMonth(-1);
            } else if (arg0.getSource() == internalView.getNextYearButton()) {
                internalModel.getModel().addYear(1);
            } else if (arg0.getSource() == internalView.getPreviousYearButton()) {
                internalModel.getModel().addYear(-1);
            } else {
                for (int month = 0; month < internalView.getMonthPopupMenuItems().length; month++) {
                    if (arg0.getSource() == internalView.getMonthPopupMenuItems()[month]) {
                        internalModel.getModel().setMonth(month);
                    }
                }
            }
        }

        /**
         * Mouse down on monthLabel pops up a table. Mouse down on todayLabel
         * sets the value of the internal model to today. Mouse down on day
         * table will set the day to the value. Mouse down on none label will
         * clear the date.
         */
        public void mousePressed(MouseEvent arg0) {
            if (!JDatePanel.this.isEnabled()) {
                return;
            }

            if (arg0.getSource() == internalView.getMonthLabel()) {
                internalView.getMonthPopupMenu().setLightWeightPopupEnabled(false);
                internalView.getMonthPopupMenu().show((Component) arg0.getSource(), arg0.getX(), arg0.getY());
            } else if (arg0.getSource() == internalView.getTodayLabel()) {
                Calendar today = Calendar.getInstance();
                internalModel.getModel().setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
            } else if (arg0.getSource() == internalView.getDayTable()) {
                int row = internalView.getDayTable().getSelectedRow();
                int col = internalView.getDayTable().getSelectedColumn();
                if (row >= 0 && row <= 5) {
                    Integer date = (Integer) internalModel.getValueAt(row, col);

                    // check constraints
                    int oldDay = internalModel.getModel().getDay();
                    internalModel.getModel().setDay(date);
                    if (!checkConstraints(internalModel.getModel())) {
                        // rollback
                        internalModel.getModel().setDay(oldDay);
                        return;
                    }

                    internalModel.getModel().setSelected(true);

                    if (doubleClickAction && arg0.getClickCount() == 2) {
                        fireActionPerformed();
                    }
                    if (!doubleClickAction) {
                        fireActionPerformed();
                    }
                }
            } else if (arg0.getSource() == internalView.getNoneLabel()) {
                internalModel.getModel().setSelected(false);

                if (doubleClickAction && arg0.getClickCount() == 2) {
                    fireActionPerformed();
                }
                if (!doubleClickAction) {
                    fireActionPerformed();
                }
            }
        }

        public void mouseClicked(MouseEvent arg0) {
        }

        public void mouseEntered(MouseEvent arg0) {
        }

        public void mouseExited(MouseEvent arg0) {
        }

        public void mouseReleased(MouseEvent arg0) {
        }

    }

    /**
     * This model represents the selected date. The model implements the
     * TableModel interface for displaying days, and it implements the
     * SpinnerModel for the year.
     *
     * @author Juan Heyns
     */
    protected class InternalCalendarModel implements TableModel, SpinnerModel, ChangeListener {

        private DateModel<?> model;
        private Set<ChangeListener> spinnerChangeListeners;
        private Set<TableModelListener> tableModelListeners;

        public InternalCalendarModel(DateModel<?> model) {
            this.spinnerChangeListeners = new HashSet<ChangeListener>();
            this.tableModelListeners = new HashSet<TableModelListener>();
            this.model = model;
            model.addChangeListener(this);
        }

        public DateModel<?> getModel() {
            return model;
        }

        /**
         * Part of SpinnerModel, year
         */
        public void addChangeListener(ChangeListener arg0) {
            spinnerChangeListeners.add(arg0);
        }

        /**
         * Part of SpinnerModel, year
         */
        public void removeChangeListener(ChangeListener arg0) {
            spinnerChangeListeners.remove(arg0);
        }

        /**
         * Part of SpinnerModel, year
         */
        public Object getNextValue() {
            return Integer.toString(model.getYear() + 1);
        }

        /**
         * Part of SpinnerModel, year
         */
        public Object getPreviousValue() {
            return Integer.toString(model.getYear() - 1);
        }

        /**
         * Part of SpinnerModel, year
         */
        public void setValue(Object text) {
            String year = (String) text;
            model.setYear(Integer.parseInt(year));
        }

        /**
         * Part of SpinnerModel, year
         */
        public Object getValue() {
            return Integer.toString(model.getYear());
        }

        /**
         * Part of TableModel, day
         */
        public void addTableModelListener(TableModelListener arg0) {
            tableModelListeners.add(arg0);
        }

        /**
         * Part of TableModel, day
         */
        public void removeTableModelListener(TableModelListener arg0) {
            tableModelListeners.remove(arg0);
        }

        /**
         * Part of TableModel, day
         */
        public int getColumnCount() {
            return 7;
        }

        /**
         * Part of TableModel, day
         */
        public int getRowCount() {
            return 6;
        }

        /**
         * Part of TableModel, day
         */
        public String getColumnName(int columnIndex) {
            ComponentTextDefaults.Key key = ComponentTextDefaults.Key.getDowKey(((firstDayOfWeek - 1) + columnIndex) % 7);
            return getTexts().getText(key);
        }

        private int[] lookup = null;

        /**
         * Results in a mapping which calculates the number of days before the first day of month
         *
         * DAY OF WEEK
         * M T W T F S S
         * 1 2 3 4 5 6 0
         *
         * or
         *
         * S M T W T F S
         * 0 1 2 3 4 5 6
         *
         * DAYS BEFORE
         * 0 1 2 3 4 5 6
         *
         * @return
         */
        private int[] lookup() {
            if (lookup == null) {
                lookup = new int[8];
                lookup[(firstDayOfWeek - 1) % 7] = 0;
                lookup[(firstDayOfWeek + 0) % 7] = 1;
                lookup[(firstDayOfWeek + 1) % 7] = 2;
                lookup[(firstDayOfWeek + 2) % 7] = 3;
                lookup[(firstDayOfWeek + 3) % 7] = 4;
                lookup[(firstDayOfWeek + 4) % 7] = 5;
                lookup[(firstDayOfWeek + 5) % 7] = 6;
            }
            return lookup;
        }

        /**
         * Part of TableModel, day
         *
         * previous month (... -1, 0) ->
         * current month (1...DAYS_IN_MONTH) ->
         * next month (DAYS_IN_MONTH + 1, DAYS_IN_MONTH + 2, ...)
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            int series = columnIndex + rowIndex * 7 + 1;

            Calendar firstOfMonth = Calendar.getInstance();
            firstOfMonth.set(model.getYear(), model.getMonth(), 1);
            int dowForFirst = firstOfMonth.get(Calendar.DAY_OF_WEEK);
            int daysBefore = lookup()[dowForFirst - 1];

            return series - daysBefore;
        }

        /**
         * Part of TableModel, day
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Class getColumnClass(int arg0) {
            return Integer.class;
        }

        /**
         * Part of TableModel, day
         */
        public boolean isCellEditable(int arg0, int arg1) {
            return false;
        }

        /**
         * Part of TableModel, day
         */
        public void setValueAt(Object arg0, int arg1, int arg2) {
        }

        /**
         * Called whenever a change is made to the model value. Notify the
         * internal listeners and update the simple controls. Also notifies the
         * (external) ChangeListeners of the component, since the internal state
         * has changed.
         */
        private void fireValueChanged() {
            //Update year spinner
            for (ChangeListener cl : spinnerChangeListeners) {
                cl.stateChanged(new ChangeEvent(this));
            }

            //Update month label
            internalView.updateMonthLabel();

            //Update day table
            for (TableModelListener tl : tableModelListeners) {
                tl.tableChanged(new TableModelEvent(this));
            }
        }

        /**
         * The model has changed and needs to notify the InternalModel.
         */
        public void stateChanged(ChangeEvent e) {
            fireValueChanged();
        }

    }

}
