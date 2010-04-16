package net.sourceforge.jdatepicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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

/**
 * @author Juan Heyns, Yue Huang
 * Created on 26-Mar-2004
 * Refactored on 21-Jun-2004
 * Refactored on 8-Jul-2004
 * least modified 14-May-2009
 * 
 * @deprecated
 */
public class JDatePanel extends JPanel {
	
	/**
	 * This inner class renders the table of the days
	 * TODO Refactor
	 */
	private class CalendarTableCellRenderer extends DefaultTableCellRenderer{


		public CalendarTableCellRenderer(){

		}

		public Component getTableCellRendererComponent(JTable arg0,
													   Object arg1,
													   boolean isSelected,
													   boolean hasFocus,
													   int row,
													   int col) {
			JLabel label = (JLabel)super.getTableCellRendererComponent(arg0,arg1,isSelected,hasFocus,row,col);
			label.setHorizontalAlignment(JLabel.CENTER);
			
			if (row == -1){
				String head = (String)arg1;
				label.setForeground(new Color(10,36,106));
				label.setBackground(Color.WHITE);
				label.setHorizontalAlignment(JLabel.CENTER);
				return label;
			}

			GregorianCalendar cal = (GregorianCalendar) calendarModel.getCalendarClone();
			Integer day = (Integer)arg1;
			Calendar today = new GregorianCalendar();
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			//Setting Foreground
			if (cal.get(Calendar.DATE) == day.intValue()){
				if (today.get(Calendar.DATE) == day.intValue() &&
			        today.get(Calendar.MONTH) == calendarModel.getCalendar(Calendar.MONTH) &&
			        today.get(Calendar.YEAR) == calendarModel.getCalendar(Calendar.YEAR)) 
				{
					label.setForeground(Color.RED);
				}
				else {
					label.setForeground(Color.WHITE);
				}
			} 
			else if (day.intValue()<1 || day.intValue()>lastDay){
				label.setForeground(Color.LIGHT_GRAY);
				if (day.intValue()>lastDay){
					label.setText(Integer.toString(day.intValue()-lastDay));
				}
				else{
					Calendar lastMonth = new GregorianCalendar();
					lastMonth.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)-1,1);
					int lastDayLastMonth = lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
					label.setText(Integer.toString(lastDayLastMonth+day.intValue()));
				}
			} 
			else {
				if (today.get(Calendar.DATE) == day.intValue() &&
				        today.get(Calendar.MONTH) == calendarModel.getCalendar(Calendar.MONTH) &&
				        today.get(Calendar.YEAR) == calendarModel.getCalendar(Calendar.YEAR)) 
				{
					label.setForeground(Color.RED);
				}
				else {
					label.setForeground(Color.BLACK);
				}
			}
			
			//Setting background
			if (cal.get(Calendar.DATE) == day.intValue()){
				label.setBackground(new Color(10,36,106));
			} 
			else {
				label.setBackground(Color.WHITE);
			}
			
			return label;
		}

	}

	/**
	 * This inner class hides the public event handling methods from the outside
	 * TODO Refactor
	 */
	private class EventHandler implements ActionListener, MouseListener, ChangeListener{

		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == nextMonthButton){
				GregorianCalendar cal = (GregorianCalendar)calendarModel.getCalendarClone();
				int day = cal.get(Calendar.DATE);
				cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,1);
				if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)){
					cal.set(Calendar.DATE,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				}
				else {
					cal.set(Calendar.DATE,day);
				}
				calendarModel.setCalendar(cal.getTime());
			}
			else if (arg0.getSource() == previousMonthButton){
				GregorianCalendar cal = (GregorianCalendar)calendarModel.getCalendarClone();
				int day = cal.get(Calendar.DATE);
				cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)-1,1);
				if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)){
					cal.set(Calendar.DATE,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				}
				else {
					cal.set(Calendar.DATE,day);
				}
				calendarModel.setCalendar(cal.getTime());
			}
			else if (arg0.getSource() == nextYearButton) {
				GregorianCalendar cal = (GregorianCalendar)calendarModel.getCalendarClone();
				cal.set(cal.get(Calendar.YEAR) + 1, cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
				calendarModel.setCalendar(cal.getTime());
			}
			else if (arg0.getSource() == previousYearButton) {
				GregorianCalendar cal = (GregorianCalendar)calendarModel.getCalendarClone();
				cal.set(cal.get(Calendar.YEAR) - 1, cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
				calendarModel.setCalendar(cal.getTime());
			} else {
				for (int month=0; month<monthPopupMenuItems.length; month++) {
					if (arg0.getSource() == monthPopupMenuItems[month]){
						GregorianCalendar cal = (GregorianCalendar)calendarModel.getCalendarClone();
						int day = cal.get(Calendar.DATE);
						cal.set(cal.get(Calendar.YEAR),month,1);
						if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)){
							cal.set(Calendar.DATE,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
						}
						else {
							cal.set(Calendar.DATE,day);
						}
						calendarModel.setCalendar(cal.getTime());
					}
				}
			}
		}

		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getSource() == monthLabel){
				getMonthPopupMenu().setLightWeightPopupEnabled(false);
				monthPopupMenu.show((Component) arg0.getSource(),arg0.getX(),arg0.getY());
			}
			else if (arg0.getSource() == todayLabel){
				calendarModel.setCalendar(new Date());
				fireActionPerformed();
			}
			else if (arg0.getSource() == dayTable){
				int row = dayTable.getSelectedRow();
				int col = dayTable.getSelectedColumn();
				if (row >= 0 && row <= 5) {
					Integer date = (Integer)calendarModel.getValueAt(row, col);
					calendarModel.setCalendar(Calendar.DAY_OF_MONTH,date.intValue());
					fireActionPerformed();
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

		public void stateChanged(ChangeEvent arg0) {
			if (arg0.getSource()==calendarModel){
				Iterator i = getChangeListeners().iterator();
				while (i.hasNext()){
					ChangeListener cl = (ChangeListener) i.next();
					cl.stateChanged(new ChangeEvent(JDatePanel.this));
				}
			}
		}
	}

	/**
	 * This model represents the selected date, it is used by the table and year spinner
	 * TODO has to be refactored
	 */
	private class GregorianCalendarModel implements TableModel, SpinnerModel{

		private GregorianCalendar calendar;
		private Vector changeListeners;     // spinner
		private Vector monthLabels;         // label
		private Vector tableModelListeners; // table

		public GregorianCalendarModel(){
			calendar = new GregorianCalendar();
			changeListeners = new Vector();
			tableModelListeners = new Vector();
			monthLabels = new Vector();
		}

		public void addChangeListener(ChangeListener arg0) {
			changeListeners.add(arg0);
		}

		public void addMonthLabel(JLabel label){
			monthLabels.add(label);
			fireMonthTextFieldsUpdate();
		}

		public void addTableModelListener(TableModelListener arg0) {
			tableModelListeners.add(arg0);
		}

		public void addTodayLabel(JLabel label) {
			DateFormat df1 = DateFormat.getDateInstance(DateFormat.MEDIUM);
			label.setText("Today: " + df1.format(new Date()));
		}

		private void fireCalendarChanged(){
			//notify the spinner view of a change
			Iterator i = changeListeners.iterator();
			while (i.hasNext()){
				ChangeListener cl = (ChangeListener) i.next();
				cl.stateChanged(new ChangeEvent(this));
			}
		}

		public void fireCalendarInvalidated(){
			fireCalendarChanged();
			fireTableModelEvent();
			fireMonthTextFieldsUpdate();
		}

		private void fireMonthTextFieldsUpdate(){
			//change monthLabel text
			Iterator i = monthLabels.iterator();
			while (i.hasNext()){
				JLabel label = (JLabel) i.next();
				DateFormatSymbols df = new DateFormatSymbols();
				label.setText(df.getMonths()[calendar.get(Calendar.MONTH)]);
			}
		}

		private void fireTableModelEvent(){
			//notify the table view of a change
			Iterator i = tableModelListeners.iterator();
			while (i.hasNext()){
				TableModelListener tl = (TableModelListener) i.next();
				tl.tableChanged(new TableModelEvent(this));
			}
		}

		public int getCalendar(int field){
			return calendar.get(field);
		}

		public Calendar getCalendarClone(){
			return (Calendar) calendar.clone();
		}

		public Class getColumnClass(int arg0) {
			return Integer.class;
		}

		public int getColumnCount() {
			return 7;
		}

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

		public Object getNextValue() {
			return Integer.toString(getCalendar(Calendar.YEAR)+1);
		}

		public Object getPreviousValue() {
			return Integer.toString(getCalendar(Calendar.YEAR)-1);
		}

		public int getRowCount() {
			return 6;
		}

		public Object getValue() {
			return Integer.toString(getCalendar(Calendar.YEAR));
		}

		public Object getValueAt(int arg0, int arg1) {
			Calendar firstDayOfMonth = (Calendar) calendar.clone();
			firstDayOfMonth.set(Calendar.DATE,1);
			int DOW = firstDayOfMonth.get(Calendar.DAY_OF_WEEK);
			int value = arg1 - DOW + arg0*7 + 2;
			return new Integer(value);
		}

		public boolean isCellEditable(int arg0, int arg1) {
			return false;
		}

		public void removeChangeListener(ChangeListener arg0) {
			changeListeners.remove(arg0);
		}

		public void removeMonthLabel(JLabel label){
			monthLabels.remove(label);
		}

		public void removeTableModelListener(TableModelListener arg0) {
			tableModelListeners.remove(arg0);
		}

		public void setCalendar(Date date){
			calendar.setTime(date);
			fireCalendarInvalidated();
		}

		public void setCalendar(int field, int value){
			calendar.set(field, value);
			fireCalendarInvalidated();
		}

		public void setCalendar(int year,int month, int date){
			calendar.set(year, month, date);
			fireCalendarInvalidated();
		}

		public void setValue(Object arg0) {
			int year = Integer.parseInt((String)arg0);
			setCalendar(Calendar.YEAR,year);
		}

		public void setValueAt(Object arg0, int arg1, int arg2) {
		}
	}
	
	/**
	 * This tests the JDatePanel
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame testFrame = new JFrame();
		JPanel content = new JPanel();
		//content.add(new JDatePanel());
		//testFrame.getContentPane().add(content);
		JDatePanel datePanel = new JDatePanel();
		datePanel.setShowYearButtons(true);
		testFrame.getContentPane().add(datePanel);
		datePanel.setShowYearButtons(false);
		datePanel.setShowYearButtons(true);

		testFrame.setSize(300,300);
		testFrame.addWindowFocusListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		testFrame.setVisible(true);
	}
	
	private Vector actionListeners = null;
	
	private GregorianCalendarModel calendarModel = null;
	
	private javax.swing.JPanel centerPanel = null;
	private Vector changeListeners = null;
	
	private javax.swing.JTable dayTable = null;
	
	private CalendarTableCellRenderer dayTableCellRenderer = null;
	private JTableHeader dayTableHeader = null;
	private EventHandler eventHandler = null;
	private javax.swing.JLabel monthLabel = null;
	private javax.swing.JPopupMenu monthPopupMenu = null;
	private JMenuItem[] monthPopupMenuItems = null;
	private javax.swing.JButton nextMonthButton = null;
	private javax.swing.JPanel northCenterPanel = null;
	
	private javax.swing.JPanel northPanel = null;
	private javax.swing.JButton previousMonthButton = null;
	private javax.swing.JPanel southPanel = null;
	private javax.swing.JLabel todayLabel = null;
	private javax.swing.JSpinner yearSpinner = null;
	private javax.swing.JButton previousYearButton = null;
	private javax.swing.JButton nextYearButton = null;
	private javax.swing.JPanel previousButtonPanel = null;
	private javax.swing.JPanel nextButtonPanel = null;

	private boolean showYearButtons = false;
	
	private DateFormat dateFormat;
	
	/**
	 * This is the default constructor
	 */
	public JDatePanel() {
		super();
		dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
		initialize();
	}
	
	public void setFontSize(int size) {
		Font font = new Font ("Arial", Font.PLAIN, size);
		getMonthLabel().setFont(font);
		getTodayLabel().setFont(font);
		getYearSpinner().setFont(font);
		dayTable.setFont(font);
		dayTable.setRowHeight(size*2);
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * The actionListener is notified when a user clicks on a date
	 * @param arg
	 */
	public void addActionListener(ActionListener arg){
		getActionListeners().add(arg);
	}

	/**
	 * Change listeners will be notified when the selected date is changed
	 * @param arg
	 */
	public void addChangeListener(ChangeListener arg){
		getChangeListeners().add(arg);
	}

	/**
	 * called internally when actionListeners should be notified
	 *
	 */
	private void fireActionPerformed(){
		Iterator i = getActionListeners().iterator();
		while (i.hasNext()){
			ActionListener al = (ActionListener) i.next();
			al.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"date selected"));
		}
	}
	
	private Vector getActionListeners(){
		if (actionListeners == null) {
			actionListeners = new Vector();
		}
		return actionListeners;
	}

	/**
	 * Returns a clone of the Calender object holding the date internally
	 * @return Calendar
	 */
	public Calendar getCalendarClone(){
		return getCalenderModel().getCalendarClone();
	}
	
	private GregorianCalendarModel getCalenderModel() {
		if (calendarModel == null) {
			calendarModel = new GregorianCalendarModel();
			calendarModel.addChangeListener(eventHandler);
		}
		return calendarModel;
	}

	/**

	 * This method initializes centerPanel	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new javax.swing.JPanel();
			centerPanel.setLayout(new java.awt.BorderLayout());
			centerPanel.setOpaque(false);
			centerPanel.add(getDayTableHeader(), java.awt.BorderLayout.NORTH);
			centerPanel.add(getDayTable(), java.awt.BorderLayout.CENTER);
		}
		return centerPanel;
	}
	
	private Vector getChangeListeners(){
		if (changeListeners == null) {
			changeListeners = new Vector();
		}
		return changeListeners;
	}

	/**

	 * This method initializes dayTable	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private javax.swing.JTable getDayTable() {
		if (dayTable == null) {
			dayTable = new javax.swing.JTable();
			dayTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
      dayTable.setRowHeight(18); 
			//dayTable.setOpaque(false);
			dayTable.setPreferredSize(new java.awt.Dimension(100,80));
			dayTable.setModel(getCalenderModel());
			dayTable.setShowGrid(true);
			dayTable.setGridColor(Color.WHITE);
			dayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			dayTable.setCellSelectionEnabled(true);
			dayTable.setRowSelectionAllowed(true);
			dayTable.setFocusable(false);
			dayTable.addMouseListener(getEventHandler());
			TableColumn column = null;
			for (int i = 0; i < 7; i++) {
				column = dayTable.getColumnModel().getColumn(i);
				column.setPreferredWidth(15);
				column.setCellRenderer(getDayTableCellRenderer());
			}
		}
		return dayTable;
	}
	
	private CalendarTableCellRenderer getDayTableCellRenderer() {
		if (dayTableCellRenderer == null) {
			dayTableCellRenderer = new CalendarTableCellRenderer();
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
	
	private EventHandler getEventHandler() {
		if (eventHandler == null) {
			eventHandler = new EventHandler();
		}
		return eventHandler;
	}

	/**

	 * This method initializes monthLabel	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getMonthLabel() {
		if (monthLabel == null) {
			monthLabel = new javax.swing.JLabel();
			monthLabel.setForeground(java.awt.SystemColor.activeCaptionText);
			monthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			monthLabel.setText("uninitialised");
			monthLabel.addMouseListener(getEventHandler());
			getCalenderModel().addMonthLabel(monthLabel);
		}
		return monthLabel;
	}

	/**

	 * This method initializes monthPopupMenu	

	 * 	

	 * @return javax.swing.JPopupMenu	

	 */    
	private javax.swing.JPopupMenu getMonthPopupMenu() {
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
				mi.addActionListener(eventHandler);
				monthPopupMenuItems[i] = mi;
			}
		}
		return monthPopupMenuItems;
	}

	/**
	 * This method initializes nextMonthButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private javax.swing.JButton getNextMonthButton() {
		if (nextMonthButton == null) {
			nextMonthButton = new javax.swing.JButton(new JNextIcon(4,7));
			nextMonthButton.setText("");
			nextMonthButton.setPreferredSize(new java.awt.Dimension(20,15));
			nextMonthButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			nextMonthButton.setFocusable(false);
			nextMonthButton.addActionListener(getEventHandler());
			nextMonthButton.setToolTipText("Next Month");
		}
		return nextMonthButton;
	}

	/**
	 * This method initializes nextYearButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private javax.swing.JButton getNextYearButton() {
		if (nextYearButton == null) {
			nextYearButton = new javax.swing.JButton(new JNextIcon(8,7, true));
			nextYearButton.setText("");
			nextYearButton.setPreferredSize(new java.awt.Dimension(20,15));
			nextYearButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			nextYearButton.setFocusable(false);
			nextYearButton.addActionListener(getEventHandler());
			nextYearButton.setToolTipText("Next Year");
		}
		return nextYearButton;
	}

	/**

	 * This method initializes northCenterPanel	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getNorthCenterPanel() {
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
	 * This method initializes northPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private javax.swing.JPanel getNorthPanel() {
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
	 * This method initializes previousButtonPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getPreviousButtonPanel() {
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
	private javax.swing.JPanel getNextButtonPanel() {
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
	 * This method initializes previousMonthButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private javax.swing.JButton getPreviousMonthButton() {
		if (previousMonthButton == null) {
			previousMonthButton = new javax.swing.JButton(new JPreviousIcon(4,7));
			previousMonthButton.setText("");
			previousMonthButton.setPreferredSize(new java.awt.Dimension(20,15));
			previousMonthButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			previousMonthButton.setFocusable(false);
			previousMonthButton.addActionListener(getEventHandler());
			previousMonthButton.setToolTipText("Previous Month");
		}
		return previousMonthButton;
	}
	
	/**
	 * This method initializes previousMonthButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private javax.swing.JButton getPreviousYearButton() {
		if (previousYearButton == null) {
			previousYearButton = new javax.swing.JButton(new JPreviousIcon(8,7, true));
			previousYearButton.setText("");
			previousYearButton.setPreferredSize(new java.awt.Dimension(20,15));
			previousYearButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			previousYearButton.setFocusable(false);
			previousYearButton.addActionListener(getEventHandler());
			previousYearButton.setToolTipText("Previous Year");
		}
		return previousYearButton;
	}

	/**

	 * This method initializes southPanel	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getSouthPanel() {
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
	private javax.swing.JLabel getTodayLabel() {
		if (todayLabel == null) {
			todayLabel = new javax.swing.JLabel();
			todayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			todayLabel.setText("uninitialised");
			todayLabel.addMouseListener(getEventHandler());
			getCalenderModel().addTodayLabel(todayLabel);
		}
		return todayLabel;
	}

	/**

	 * This method initializes yearSpinner	

	 * 	

	 * @return javax.swing.JSpinner	

	 */    
	private javax.swing.JSpinner getYearSpinner() {
		if (yearSpinner == null) {
			yearSpinner = new javax.swing.JSpinner();
			yearSpinner.setModel(getCalenderModel());
		}
		return yearSpinner;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
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
	 * removes the actionListener
	 * @param arg
	 */
	public void removeActionListener(ActionListener arg){
		getActionListeners().remove(arg);
	}

	/**
	 * removes the specified change listener
	 * @param arg
	 */
	public void removeChangeListener(ChangeListener arg){
		getChangeListeners().remove(arg);
	}
	
	/**
	 * Prints out the selectedDate in long format
	 */
	public String toString(){
		return dateFormat.format(calendarModel.getCalendarClone().getTime());
	}

	/**
	 * Set the tool tip text for the Previous Year Button.
	 * @param toolTipText
	 */
	public void setPreviousYearButtonToolTipText(String toolTipText) {
		this.getPreviousYearButton().setToolTipText(toolTipText);
	}

	/**
	 * Gets the tool tip text for the Previous Year Button.
	 * @return the tool tip text for the previous year button
	 */
	public String getPreviousYearButtonToolTipText() {
	       return this.getPreviousYearButton().getToolTipText();
      	}	       

	/**
	 * Set the tool tip text for the Next Year Button.
	 * @param toolTipText
	 */
	public void setNextYearButtonToolTipText(String toolTipText) {
		this.getNextYearButton().setToolTipText(toolTipText);
	}

	/**
	 * Gets the tool tip text for the Next Year Button.
	 * @return the tool tip text for the next year button
	 */
	public String getNextYearButtonToolTipText() {
	       return this.getNextYearButton().getToolTipText();
      	}	       

	/**
	 * Set the tool tip text for the Previous Month Button.
	 * @param toolTipText
	 */
	public void setPreviousMonthButtonToolTipText(String toolTipText) {
		this.getPreviousMonthButton().setToolTipText(toolTipText);
	}

	/**
	 * Gets the tool tip text for the Previous Month Button.
	 * @return the tool tip text for the previous month button
	 */
	public String getPreviousMonthButtonToolTipText() {
	       return this.getPreviousMonthButton().getToolTipText();
      	}	       

	/**
	 * Set the tool tip text for the Next Month Button.
	 * @param toolTipText
	 */
	public void setNextMonthButtonToolTipText(String toolTipText) {
		this.getNextMonthButton().setToolTipText(toolTipText);
	}

	/**
	 * Gets the tool tip text for the Next Month Button.
	 * @return the tool tip text for the next month button
	 */
	public String getNextMonthButtonToolTipText() {
	       return this.getNextMonthButton().getToolTipText();
      	}	       

	/**
	 * Sets the visibilty of the Year navigation buttons
	 * @param showYearButtons
	 */
	public void setShowYearButtons(boolean showYearButtons) {
		this.showYearButtons = showYearButtons;
		if (this.showYearButtons) {
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
	 * Is the year navigation buttons active
	 * @return visiblity of the year
	 */
	public boolean isShowYearButtons() {
		return this.showYearButtons;
	}

  }  
