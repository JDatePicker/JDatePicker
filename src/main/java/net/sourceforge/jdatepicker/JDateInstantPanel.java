package net.sourceforge.jdatepicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.sourceforge.jdatepicker.graphics.JNextIcon;
import net.sourceforge.jdatepicker.graphics.JPreviousIcon;

public abstract class JDateInstantPanel<T> extends JPanel implements JDateInstantComponent<T> {

	private static final long serialVersionUID = -2299249311312882915L;
	
	private HashSet<ActionListener> actionListeners;
	private HashSet<ChangeListener> changeListeners;
	private Properties i18nStrings;
	private boolean showYearButtons;
	
	protected InternalCalendarModel internalModel;
	private InternalView internalView;
	private InternalController internalController;

	protected JDateInstantPanel() {
		showYearButtons = false;
		actionListeners = new HashSet<ActionListener>();
		changeListeners = new HashSet<ChangeListener>();
		i18nStrings = new Properties(); //TODO default strings
		
		internalModel = new InternalCalendarModel(Calendar.getInstance());
		internalView = new InternalView();
		internalController = new InternalController();
		
		add(internalView);
	}

	public void addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}

	public void removeActionListener(ActionListener actionListener) {
		actionListeners.remove(actionListener);
	}

	//TODO actually fire these events, but add some config to how they should happen.
	/**
	 * Called internally when actionListeners should be notified.
	 */
	private void fireActionPerformed() {
		for (ActionListener actionListener : actionListeners) {
			actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "DateInstant selected"));
		}
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
	private void fireStateChanged() {
		for (ChangeListener changeListener : changeListeners) {
			changeListener.stateChanged(new ChangeEvent(this));
		}
	}

	public void setI18nStrings(Properties i18nStrings) {
		this.i18nStrings = i18nStrings;
	}
	
	public Properties getI18nStrings() {
		return i18nStrings;
	}

	/**
	 * Sets the visibilty of the Year navigation buttons.
	 * 
	 * @param showYearButtons
	 */
	public void setShowYearButtons(boolean showYearButtons) {
		this.showYearButtons = showYearButtons;
		internalView.updateShowYearButtons();
	}

	/**
	 * Is the year navigation buttons active.
	 * 
	 * @return visiblity of the year
	 */
	public boolean isShowYearButtons() {
		return this.showYearButtons;
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
			DateFormatSymbols df = new DateFormatSymbols();
			monthLabel.setText(df.getMonths()[internalModel.getCalendar().get(Calendar.MONTH)]);
		}
		
		public InternalView() {
			initialise();
		}
		
		/**
		 * Initialise the control.
		 */
		private void initialise() {
			this.setLayout(new java.awt.BorderLayout());
			this.setSize(200,220);
			this.setPreferredSize(new java.awt.Dimension(200,220));
			this.setBackground(java.awt.SystemColor.activeCaptionText);
			this.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black,1));
			this.setOpaque(false);
			this.add(getNorthPanel(), java.awt.BorderLayout.NORTH);
			this.add(getSouthPanel(), java.awt.BorderLayout.SOUTH);
			this.add(getCenterPanel(), java.awt.BorderLayout.CENTER);
		}
		
		/**
		 * This method initializes northPanel	
		 * 	
		 * @return javax.swing.JPanel	
		 */    
		private JPanel getNorthPanel() {
			if (northPanel == null) {
				northPanel = new javax.swing.JPanel();
				northPanel.setLayout(new java.awt.BorderLayout());
				northPanel.setName("");
				northPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3,3,3,3));
				northPanel.setBackground(java.awt.SystemColor.activeCaption);
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
				northCenterPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,5,0,5));
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
				monthLabel.setForeground(java.awt.SystemColor.activeCaptionText);
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
				//southPanel.setOpaque(false);
				southPanel.setBackground(Color.WHITE);
				southPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3,3,3,3));
				southPanel.add(getTodayLabel(), java.awt.BorderLayout.CENTER);
			}
			return southPanel;
		}

		/**
		 * This method initializes todayLabel	
		 * 	
		 * @return javax.swing.JLabel	
		 */    
		private JLabel getTodayLabel() {
			if (todayLabel == null) {
				todayLabel = new javax.swing.JLabel();
				todayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				todayLabel.addMouseListener(internalController);
				
				DateFormat df1 = DateFormat.getDateInstance(DateFormat.MEDIUM);
				todayLabel.setText("Today: " + df1.format(new Date()));
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
				dayTable.setRowHeight(18); 
				//dayTable.setOpaque(false);
				dayTable.setPreferredSize(new java.awt.Dimension(100,80));
				dayTable.setModel(internalModel);
				dayTable.setShowGrid(true);
				dayTable.setGridColor(Color.WHITE);
				dayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				dayTable.setCellSelectionEnabled(true);
				dayTable.setRowSelectionAllowed(true);
				dayTable.setFocusable(false);
				dayTable.addMouseListener(internalController);
				TableColumn column = null;
				for (int i = 0; i < 7; i++) {
					column = dayTable.getColumnModel().getColumn(i);
					column.setPreferredWidth(15);
					column.setCellRenderer(getDayTableCellRenderer());
				}
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
				java.awt.GridLayout layout = new java.awt.GridLayout(1,2);
				layout.setHgap(3);
				previousButtonPanel.setLayout(layout);
				previousButtonPanel.setName("");
				previousButtonPanel.setBackground(java.awt.SystemColor.activeCaption);
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
				java.awt.GridLayout layout = new java.awt.GridLayout(1,2);
				layout.setHgap(3);
				nextButtonPanel.setLayout(layout);
				nextButtonPanel.setName("");
				nextButtonPanel.setBackground(java.awt.SystemColor.activeCaption);
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
				nextMonthButton = new javax.swing.JButton(new JNextIcon(4,7));
				nextMonthButton.setText("");
				nextMonthButton.setPreferredSize(new java.awt.Dimension(20,15));
				nextMonthButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
				nextMonthButton.setFocusable(false);
				nextMonthButton.addActionListener(internalController);
				nextMonthButton.setToolTipText("Next Month");
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
				nextYearButton = new javax.swing.JButton(new JNextIcon(8,7, true));
				nextYearButton.setText("");
				nextYearButton.setPreferredSize(new java.awt.Dimension(20,15));
				nextYearButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
				nextYearButton.setFocusable(false);
				nextYearButton.addActionListener(internalController);
				nextYearButton.setToolTipText("Next Year");
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
				previousMonthButton = new javax.swing.JButton(new JPreviousIcon(4,7));
				previousMonthButton.setText("");
				previousMonthButton.setPreferredSize(new java.awt.Dimension(20,15));
				previousMonthButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
				previousMonthButton.setFocusable(false);
				previousMonthButton.addActionListener(internalController);
				previousMonthButton.setToolTipText("Previous Month");
				previousMonthButton.addActionListener(internalController);
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
				previousYearButton = new javax.swing.JButton(new JPreviousIcon(8,7, true));
				previousYearButton.setText("");
				previousYearButton.setPreferredSize(new java.awt.Dimension(20,15));
				previousYearButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
				previousYearButton.setFocusable(false);
				previousYearButton.addActionListener(internalController);
				previousYearButton.setToolTipText("Previous Year");
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
				for (int i=0; i<menuItems.length; i++) {
					monthPopupMenu.add(menuItems[i]);
				}
			}
			return monthPopupMenu;
		}
		
		private JMenuItem[] getMonthPopupMenuItems(){
			if (monthPopupMenuItems == null) {
				DateFormatSymbols df = new DateFormatSymbols();
				String[] months = df.getMonths();
				monthPopupMenuItems = new JMenuItem[months.length-1];
				for (int i=0; i<months.length-1; i++) {
					JMenuItem mi = new JMenuItem(months[i]);
					mi.addActionListener(internalController);
					monthPopupMenuItems[i] = mi;
				}
			}
			return monthPopupMenuItems;
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

		public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean isSelected, boolean hasFocus, int row, int col) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(arg0, arg1, isSelected, hasFocus, row, col);
			label.setHorizontalAlignment(JLabel.CENTER);

			if (row == -1) {
				label.setForeground(new Color(10, 36, 106));
				label.setBackground(Color.WHITE);
				label.setHorizontalAlignment(JLabel.CENTER);
				return label;
			}

			Calendar cal = internalModel.getCalendar();
			Calendar today = Calendar.getInstance();
			Integer day = (Integer) arg1;
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			// Setting Foreground
			if (cal.get(Calendar.DATE) == day.intValue()) {
				if (today.get(Calendar.DATE) == day.intValue() 
						&& today.get(Calendar.MONTH) == internalModel.getCalendar().get(Calendar.MONTH)
						&& today.get(Calendar.YEAR) == internalModel.getCalendar().get(Calendar.YEAR)) {
					label.setForeground(Color.RED);
				} else {
					label.setForeground(Color.WHITE);
				}
			} else if (day.intValue() < 1 || day.intValue() > lastDay) {
				label.setForeground(Color.LIGHT_GRAY);
				if (day.intValue() > lastDay) {
					label.setText(Integer.toString(day.intValue() - lastDay));
				} else {
					Calendar lastMonth = new GregorianCalendar();
					lastMonth.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, 1);
					int lastDayLastMonth = lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
					label.setText(Integer.toString(lastDayLastMonth + day.intValue()));
				}
			} else {
				if (today.get(Calendar.DATE) == day.intValue()
						&& today.get(Calendar.MONTH) == internalModel.getCalendar().get(Calendar.MONTH)
						&& today.get(Calendar.YEAR) == internalModel.getCalendar().get(Calendar.YEAR)) {
					label.setForeground(Color.RED);
				} else {
					label.setForeground(Color.BLACK);
				}
			}

			// Setting background
			if (cal.get(Calendar.DATE) == day.intValue()) {
				label.setBackground(new Color(10, 36, 106));
			} else {
				label.setBackground(Color.WHITE);
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
			if (arg0.getSource() == internalView.getNextMonthButton()) {
				Calendar cal = internalModel.getCalendar();
				int day = cal.get(Calendar.DATE);
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1);
				if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				} else {
					cal.set(Calendar.DATE, day);
				}
				internalModel.setCalendar(cal);
			} 
			else if (arg0.getSource() == internalView.getPreviousMonthButton()) {
				Calendar cal = internalModel.getCalendar();
				int day = cal.get(Calendar.DATE);
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, 1);
				if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				} else {
					cal.set(Calendar.DATE, day);
				}
				internalModel.setCalendar(cal);
			} 
			else if (arg0.getSource() == internalView.getNextYearButton()) {
				Calendar cal = internalModel.getCalendar();
				cal.set(cal.get(Calendar.YEAR) + 1, cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
				internalModel.setCalendar(cal);
			} 
			else if (arg0.getSource() == internalView.getPreviousYearButton()) {
				Calendar cal = internalModel.getCalendar();
				cal.set(cal.get(Calendar.YEAR) - 1, cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
				internalModel.setCalendar(cal);
			} 
			else {
				for (int month = 0; month < internalView.getMonthPopupMenuItems().length; month++) {
					if (arg0.getSource() == internalView.getMonthPopupMenuItems()[month]) {
						Calendar cal = internalModel.getCalendar();
						int day = cal.get(Calendar.DATE);
						cal.set(cal.get(Calendar.YEAR), month, 1);
						if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
							cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
						} else {
							cal.set(Calendar.DATE, day);
						}
						internalModel.setCalendar(cal);
					}
				}
			}
		}

		/**
		 * Mouse click on monthLabel pops up a table. Mouse click on todayLabel
		 * sets the value of the internal model to today. Mouse click on day
		 * table will set the day to the value.
		 */
		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getSource() == internalView.getMonthLabel()) {
				internalView.getMonthPopupMenu().setLightWeightPopupEnabled(false);
				internalView.getMonthPopupMenu().show((Component) arg0.getSource(), arg0.getX(), arg0.getY());
			} 
			else if (arg0.getSource() == internalView.getTodayLabel()) {
				internalModel.setCalendar(Calendar.getInstance());
			} 
			else if (arg0.getSource() == internalView.getDayTable()) {
				int row = internalView.getDayTable().getSelectedRow();
				int col = internalView.getDayTable().getSelectedColumn();
				if (row >= 0 && row <= 5) {
					Calendar cal = internalModel.getCalendar();
					Integer date = (Integer) internalModel.getValueAt(row, col);
					cal.set(Calendar.DATE, date);
					internalModel.setCalendar(cal);
				}
			}
		}
	
		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
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
	protected class InternalCalendarModel implements TableModel, SpinnerModel {

		private Calendar value;
		private HashSet<ChangeListener> spinnerChangeListeners;
		private HashSet<TableModelListener> tableModelListeners;

		public InternalCalendarModel(Calendar value){
			this.value = value;
			setToMidnight();
			this.spinnerChangeListeners = new HashSet<ChangeListener>();
			this.tableModelListeners = new HashSet<TableModelListener>();
		}
		
		private void setToMidnight() {
			value.set(Calendar.HOUR, 0);
			value.set(Calendar.MINUTE, 0);
			value.set(Calendar.SECOND, 0);
			value.set(Calendar.MILLISECOND, 0);
		}

		/**
		 * Get a clone of the value.
		 * 
		 * @return
		 */
		public Calendar getCalendar(){
			return (Calendar) value.clone();
		}

		/**
		 * Set the value.
		 * 
		 * @param calendar
		 */
		public void setCalendar(Calendar calendar){
			this.value = calendar;
			setToMidnight();
			fireValueChanged();
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
			return Integer.toString(getCalendar().get(Calendar.YEAR)+1);
		}

		/**
		 * Part of SpinnerModel, year
		 */
		public Object getPreviousValue() {
			return Integer.toString(getCalendar().get(Calendar.YEAR)-1);
		}

		/**
		 * Part of SpinnerModel, year
		 */
		public void setValue(Object text) {
			value.set(Calendar.YEAR, new Integer((String)text));
			setToMidnight();
			fireValueChanged();
		}

		/**
		 * Part of SpinnerModel, year
		 */
		public Object getValue() {
			return Integer.toString(getCalendar().get(Calendar.YEAR));
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
		public String getColumnName(int arg0) {
			String name = "";
			switch (arg0){
				case 0:
					name = "Sun";
					break;
				case 1:
					name = "Mon";
					break;
				case 2:
					name = "Tue";
					break;
				case 3:
					name = "Wed";
					break;
				case 4:
					name = "Thu";
					break;
				case 5:
					name = "Fri";
					break;
				case 6:
					name = "Sat";
					break;
			}
			return name;
		}

		/**
		 * Part of TableModel, day
		 */
		@SuppressWarnings("unchecked")
		public Class getColumnClass(int arg0) {
			return Integer.class;
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
		public Object getValueAt(int arg0, int arg1) {
			Calendar firstDayOfMonth = (Calendar) value.clone();
			firstDayOfMonth.set(Calendar.DATE, 1);
			int DOW = firstDayOfMonth.get(Calendar.DAY_OF_WEEK);
			int value = arg1 - DOW + arg0*7 + 2;
			return new Integer(value);
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
			
			//Notify external change listeners
			fireStateChanged();
		}

	}
	
}
