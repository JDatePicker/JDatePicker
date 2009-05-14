package net.sourceforge.jdatepicker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.util.*;
import java.lang.String; 
import javax.swing.table.AbstractTableModel;

/**
 * @author Yue Huang
 *
 * Created on 29-April-2009
 *
 * least modified 14-May-2009
 */

public class EventNotification extends JPanel{
  public Vector<String> events = new Vector<String>();
  private JButton setEvent = new JButton("Set Enevt");
  private JButton viewEvent = new JButton("View Enevt");

  private long nextID = 0;
  private JLabel id = new JLabel("Event ID: "+nextID);
  private JDialog setEventDia;
  private JTextArea ta;
  private JLabel JLdate;
  private String curDate = "";
  private JLabel checkDate;
  private JLabel Jtype = new JLabel("Notification Type: ");
  private JCheckBox tcb = new JCheckBox("Pop Text Window");
  private JCheckBox acb = new JCheckBox("Audio");
  private JLabel jdescription = new JLabel("Set Description For Your Envent:");

  private JButton addEvent = new JButton("New");
  private JButton delEvent = new JButton("Delete");
  private JButton editEvent = new JButton("Edit");
  private JButton applyEvent = new JButton("Apply");
  private JButton setEventOK = new JButton("OK");
  private JButton setEventCancel = new JButton("Cancel");
  private MyTableModel tm;
  private JTable table;
  private JScrollPane jp;
  private JPanel tp;

  private JDialog viewEventDia;
  private JButton viewOK = new JButton("OK");

  private int selectedRow=0;
  private Boolean edit = false;
  private int dateLength=0;

  public EventNotification(){
    super();
    initialize();
  }

  private void initialize(){
    this.setLayout(new GridLayout(3,1));
    JLabel space = new JLabel();
    this.add(setEvent);
    this.add(space);
    this.add(viewEvent);
    addEventListener aeh = new addEventListener();
    addEvent.addActionListener(aeh);
    applyListener ah = new applyListener();
    applyEvent.addActionListener(ah);
    setEventListener seh = new setEventListener();
    setEvent.addActionListener(seh);
    viewEventListener veh = new viewEventListener();
    viewEvent.addActionListener(veh);
    setEventOKListener seokh = new setEventOKListener();
    setEventOK.addActionListener(seokh);
    setEventCancelListener secancelh = new setEventCancelListener();
    setEventCancel.addActionListener(secancelh);
    delEventListener dh = new delEventListener();
    delEvent.addActionListener(dh);
    editEventListener eh = new editEventListener();
    editEvent.addActionListener(eh);
    vOKEventListener vokh = new vOKEventListener();
    viewOK.addActionListener(vokh);
    loadEvents();
  }
  
  public void setNotificationDate(String date){
    JLdate = new JLabel();
    JLdate.setText("Notification Date: "+date);
    JLdate.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
  }

  public void outjdp()throws IOException{
    OutputStreamWriter output = null; //create a writer for out a config file
    File file = new File("net/sourceforge/jdatepicker/events/events.jdp");
    try { 
      output = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file))); //create a writer for out config file
    }catch(FileNotFoundException e) {
      System.err.println("Cannot open events.jdp for output");
    } 
    try{
      String str="";
      for(int i=0; i<events.size(); i++){
        str+=events.get(i)+"\n";
      }
      output.write(str,0,str.length());//write all infos to a jdp file
    }catch(IOException e){
    }
    output.close();
  }

  public void loadEvents(){
    File file = new File("net/sourceforge/jdatepicker/events/events.jdp");
    try{
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file))); 
      try{
        String str_br=br.readLine(); //try to read first line
        if(str_br==null){
          nextID=0;
        }
        while(str_br!=null){
          events.add(str_br);
          str_br=br.readLine(); //read next line
        }
      }catch(IOException e){
      }
    }catch(FileNotFoundException e){
      System.err.println ("File events.jdp can't be found");
    }
  }

  public void addTP(JPanel jtp){
    jtp.add(jp);
  }

  /**listener for addEvent **/
  private class addEventListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      edit = false;
      acb.setSelected(false);
      JLdate.setText(curDate);
      ta.setText("");
      if(events.size()>0){
        StringTokenizer  st = new StringTokenizer(events.get(events.size()-1));
        nextID = Integer.parseInt(st.nextToken())+1;
      }else{
        nextID = nextID+1;
      }
      id.setText("Event ID: "+nextID);
    }
  }

  /**listener for editEvent **/
  private class editEventListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      edit = true;
      selectedRow = table.getSelectedRow();
      for(int i=0; i<4; i++){
        if(i==0){ //get id
          id.setText("Event ID: "+tm.getValueAt(table.getSelectedRow(),i));
        }else if (i==1){ //get date
          JLdate.setText("Notification Date: "+tm.getValueAt(table.getSelectedRow(),i));
        }else if (i==2){ //get alarm
          if(((String)tm.getValueAt(table.getSelectedRow(),i)).compareTo("Yes")==0){
            acb.setSelected(true);
          }else{
            acb.setSelected(false);
          }
        }else{ //get description
          ta.setText((String)tm.getValueAt(table.getSelectedRow(),i));
        }
      }
    }
  }

  /**listener for setEvent **/
  private class setEventListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      edit = false;
      setEventDialog();
    }
  }

  /**listener for delEvent **/
  private class delEventListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      edit = false;
      for(int i=table.getSelectedRow()+table.getSelectedRowCount()-1; i>=table.getSelectedRow();i--){
        events.remove(i);
      }
      try{
        outjdp();
      }catch(IOException e) {
        e.printStackTrace();
      }
      setEventDia.dispose();
      setEventDialog();
    }
  }

  /**listener for apply **/
  private class applyListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      String str = "";
      str = dateLength +" "+ id.getText().substring(10, id.getText().length()) + " " + JLdate.getText().substring(19,JLdate.getText().length())+" ";
      if(acb.isSelected()==true){
        str += "1 ";
      }else{
        str += "0 ";
      }
      str +=ta.getText();
      if(edit == false){
        events.add(str);
        acb.setSelected(false);
        ta.setText("");
      }else{
        events.set(selectedRow,str);
      }
      try{
        outjdp();
      }catch(IOException e) {
        e.printStackTrace();
      }
      setEventDia.dispose();
      setEventDialog();
    }
  }

  /**listener for viewEvent **/
  private class viewEventListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      viewEventDialog();
    }
  }

  /**listener for viewOK**/
  private class vOKEventListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      viewEventDia.dispose();
    }
  }

  /**listener for setEventOK **/
  private class setEventOKListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      String str = "";
      str =dateLength +" "+ id.getText().substring(10, id.getText().length()) + " " + JLdate.getText().substring(19,JLdate.getText().length())+" ";
      if(acb.isSelected()==true){
        str += "1 ";
      }else{
        str += "0 ";
      }
      str +=ta.getText();
      if(edit == false){
        events.add(str);
      }else{
        events.set(selectedRow,str);
      }
      try{
        outjdp();
      }catch(IOException e) {
        e.printStackTrace();
      }
      edit = false;
      setEventDia.dispose();
    }
  }

  /**listener for setEventCancel **/
  private class setEventCancelListener implements ActionListener {
    public void actionPerformed(ActionEvent event){
      edit = false;
      setEventDia.dispose();
    }
  }
  
  private class MyTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID", "Date", "Audio Alarm", "Description"};
    private Object[][] data = new Object[events.size()][4];
    public int getColumnCount() {
      return columnNames.length;
    }

    public int getRowCount() {
      return data.length;
    }

    public String getColumnName(int col) {
      return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
      return data[row][col];
    }

    public Class getColumnClass(int c) {
      return getValueAt(0, c).getClass();
    }

    public void setValueAt(Object value, int row, int col) {
      data[row][col] = value;
      fireTableCellUpdated(row, col);
    }
  }

  public void setDateLength(String str_date){
    StringTokenizer stdate = new StringTokenizer(str_date);
    int i=0;
    while (stdate.hasMoreTokens()){
      String str_t = stdate.nextToken();
      i++;
    }
    dateLength = i;
  }
  
  public void addTable(){
    tm = new MyTableModel();
    table = new JTable(tm);
    table.setAutoCreateRowSorter(true);
    for(int i=0;i<events.size();i++){
      Vector<String> rd = new Vector<String>();
      StringTokenizer  st = new StringTokenizer(events.get(i)); //need identify
      int j=0;
      int len = 3;
      String str = "";
      while(st.hasMoreTokens()){
        if(j==0){
          len = Integer.parseInt(st.nextToken())+1;
        }
        if(j==1){
          rd.add(st.nextToken());
        }else if(j>=2 && j<=len){
          if(j==len){
            str += st.nextToken();
            rd.add(str);
            str="";
          }else{
            str += st.nextToken()+" ";
          }
        }else if(j==len+1){
          if(st.nextToken().compareTo("1")==0){
            rd.add("Yes");
          }else{
            rd.add("No");
          }
        }else if(j>=len+2){
          str+=st.nextToken()+" ";
        }
        j++;
      }
      rd.add(str);

      for(j=0; j<rd.size();j++){
        tm.setValueAt((Object)rd.get(j),i,j);
      }
    }
    jp = new JScrollPane(table);
  }

  /**method for set event dialog **/
  public void setEventDialog(){
    setEventDia = new JDialog(null,"Set Event",Dialog.ModalityType.APPLICATION_MODAL);
    if(events.size()>0){
      StringTokenizer  st = new StringTokenizer(events.get(events.size()-1));
      String str = st.nextToken();
      nextID = Integer.parseInt(st.nextToken())+1;
    }else{
      nextID = 0;
    }
    id.setText("Event ID: "+nextID);
    edit = false;
    addTable();

    curDate = JLdate.getText();
    Container descriptionC = new Container();
    descriptionC.setLayout(new BorderLayout());
    jdescription.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
    descriptionC.add(jdescription, BorderLayout.NORTH);
    ta = new JTextArea ();
    ta.setLineWrap(true); 
    JScrollPane jsp = new JScrollPane(ta);
    descriptionC.add(jsp,BorderLayout.CENTER);
    tp = new JPanel();
    tp.setLayout(new GridLayout(2,1));
    tp.add(descriptionC);
    tp.add(jp);

    Container ttc = new Container();
    ttc.setLayout(new GridLayout(3,1));
    ttc.add(id);
    id.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
    ttc.add(JLdate);
    ttc.add(Jtype);
    Jtype.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
    Container tb = new Container();
    tb.setLayout(new GridLayout(1,2));
    tb.add(tcb);
    tb.add(acb);
    tcb.setSelected(true);
    tcb.setEnabled(false);
    acb.setSelected(false);
    Container tc = new Container();
    tc.setLayout(new BorderLayout());
    tc.add(ttc, BorderLayout.NORTH);
    tc.add(tb, BorderLayout.CENTER);

    Container setButtons = new Container();
    setButtons.setLayout(new GridLayout(2,3));
    setButtons.add(addEvent);
    setButtons.add(editEvent);
    setButtons.add(delEvent);
    setButtons.add(setEventOK);
    setButtons.add(applyEvent);
    setButtons.add(setEventCancel);
    setEventDia.setLayout(new BorderLayout());
    setEventDia.add(tc,BorderLayout.NORTH);
    setEventDia.add(tp,BorderLayout.CENTER);
    setEventDia.add(setButtons,BorderLayout.SOUTH);
    setEventDia.setSize(500,500); //set dialog size
    setEventDia.setLocation(this.getGraphicsConfiguration().getBounds().width/8,
                        this.getGraphicsConfiguration().getBounds().height/8); //set location of dialog diaplay
    setEventDia.setAlwaysOnTop(true); //set dialog always on top of any windows
    setEventDia.setVisible(true); //set visible of dialog of timer seting
  }

 /**method for view event dialog **/
  public void viewEventDialog(){
    viewEventDia = new JDialog(null,"View Event",Dialog.ModalityType.APPLICATION_MODAL);
    addTable();
    viewEventDia.setLayout(new BorderLayout());
    viewEventDia.add(jp,BorderLayout.CENTER);
    viewEventDia.add(viewOK,BorderLayout.SOUTH);
    viewEventDia.setSize(400,400); //set dialog size
    viewEventDia.setLocation(this.getGraphicsConfiguration().getBounds().width/8,
                        this.getGraphicsConfiguration().getBounds().height/8); //set location of dialog diaplay
    viewEventDia.setAlwaysOnTop(true); //set dialog always on top of any windows
    viewEventDia.setVisible(true); //set visible of dialog of timer seting
  }
}