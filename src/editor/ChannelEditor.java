package editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import absynt.*;

public class ChannelEditor extends JFrame implements WindowListener {

    Editor editor;
    absynt.Program program;
    Container cpane;
    JTable channeltable;
    DefaultTableModel channeltablemodel;
    String dialogstring;
    StringDialog stringdialog;

/**
 * erstellt einen ChannelEditor fuer das uebergebene absynt.Program
 */
    public ChannelEditor(Editor editroot, absynt.Program inprogram) {
      super("ChannelEditor");
      setSize(250, 200);
      editor = editroot;
      program = inprogram;
      cpane = getContentPane();
      cpane.add(createButtonBar(), BorderLayout.NORTH);
      String[] coloumnnames = {"Name"};
      channeltablemodel = new javax.swing.table.DefaultTableModel(coloumnnames, 0)
      {
        public boolean isCellEditable (int row, int coloumn) { return(false);}
      };
      channeltable = new JTable(new Object[][] {}, coloumnnames);
      channeltable.setModel(channeltablemodel);
      JScrollPane scrollpane = new JScrollPane(channeltable);
      cpane.add(scrollpane, BorderLayout.CENTER);
      readProgram();
      setVisible(true);
      show();
    }

    JPanel createButtonBar() {
      JPanel outpanel = new JPanel();

      JButton button_add = new JButton("add");
      button_add.addActionListener(new AddListener(this));
      JButton button_remove = new JButton("remove");
      button_remove.addActionListener(new RemoveListener(this));
      JButton button_rename = new JButton("rename");
      button_rename.addActionListener(new RenameListener(this));

      outpanel.add(button_add, BorderLayout.WEST);
      outpanel.add(button_remove, BorderLayout.CENTER);
      outpanel.add(button_rename, BorderLayout.EAST);
        
      return(outpanel);
    }
    
    class AddListener implements ActionListener {
      
      ChannelEditor cheditor;
      
      public AddListener(ChannelEditor cheditroot) {
        cheditor = cheditroot;
      }
      
      public void actionPerformed(ActionEvent event) {
        dialogstring = "";
        stringdialog = new StringDialog(cheditor, dialogstring);
        if (dialogstring.length() > 0 && !isInList(dialogstring)) {
          addChannel(dialogstring);
//          channeltablemodel.addRow(new Object[] {dialogstring});
        }
      }
    }

    class RenameListener implements ActionListener {
      
      ChannelEditor cheditor;
      
      public RenameListener(ChannelEditor cheditroot) {
        cheditor = cheditroot;
      }
      
      public void actionPerformed(ActionEvent event) {
      	int selected = channeltable.getSelectedRow();
      	if (selected != -1) {
          dialogstring = (String)channeltablemodel.getValueAt(selected, 0);
          String oldname = dialogstring;
          stringdialog = new StringDialog(cheditor, dialogstring);
          if (dialogstring.length() > 0 && !(oldname.compareTo(dialogstring) == 0) && !isInList(dialogstring)) {
            renameChannel(oldname, dialogstring);
//            channeltablemodel.addRow(new Object[] {dialogstring});
            refresh();
          }
        }
      }
    }

    class RemoveListener implements ActionListener {
      
      ChannelEditor cheditor;
      
      public RemoveListener(ChannelEditor cheditroot) {
        cheditor = cheditroot;
      }
      
      public void actionPerformed(ActionEvent event) {
        int selected = channeltable.getSelectedRow();
        if (selected != -1) { 
          String channame = (String)channeltablemodel.getValueAt(selected, 0);
          removeChannel(channame);
//          channeltablemodel.removeRow(selected);
          refresh();
        }
      }
    }
 
/**
 * testet, ob bereits ein Channel mit dem uebergebenen String im Program existiert
 */
    boolean isInList(String inname) {
      System.out.println("starting search in List");
      boolean wert = false;
      if (program.chans != null) {
        absynt.ChandecList chandeclist = program.chans;
        while (chandeclist != null && !wert) {
          if (chandeclist.head != null) {
            absynt.Chandec chandec = chandeclist.head;
            if (chandec.chan != null) {
              absynt.Channel channel = chandec.chan;
              if (channel.name.compareTo(inname) == 0) wert = true;
            } else System.out.println("Error !! (ChannelEditor : isInList) Chandec without Chandec");
          } else System.out.println("Error !! (ChannelEditor : isInList) ChandecList without Chandec");
          chandeclist = chandeclist.next;
        }
      }
      return(wert);
    }

/**
 * fuegt dem Program einen neuen Channel mit dem uebergebenen Namen hinzu
 */
    void addChannel(String inname) {
      System.out.println("addChannel");
      if (program.chans != null) {
      	absynt.ChandecList chandeclist = program.chans;
      	while (chandeclist.next != null) chandeclist = chandeclist.next;
      	chandeclist.next = new absynt.ChandecList(new absynt.Chandec(new absynt.Channel(inname)), null);
      } else {
        program.chans = new absynt.ChandecList(new absynt.Chandec(new absynt.Channel(inname)), null);
      }
      channeltablemodel.addRow(new Object[] {inname});
    }

/**
 * loescht den Channel aus dem Program, der mit dem String uebereinstimmt
 */
    void removeChannel(String inname) {
      System.out.println("removeChannel");
      boolean removed = false;
      absynt.ChandecList chandeclist= null;
      absynt.Chandec chandec = null;
      absynt.Channel channel = null;
      if (program.chans != null) {
        if (program.chans.head != null) {
          if (program.chans.head.chan != null) {
            if (program.chans.head.chan.name.compareTo(inname) == 0) {
              program.chans = program.chans.next;
            } else {
              chandeclist = program.chans;
              while (chandeclist.next != null && !removed) {
              	chandec = chandeclist.next.head;
              	if (chandec != null) {
                  channel = chandec.chan;
                  if (channel != null) {
                    if (channel.name.compareTo(inname) == 0) {
                      System.out.println("removing Channel");
                      chandeclist.next = chandeclist.next.next;
                      removed = true;
                    }
                  } else System.out.println("Error !! (ChannelEditor : removeChannel) no Channel in Chandec");
              	} else System.out.println("Error !! (ChannelEditor : removeChannel) no Chandec in ChandecList");
                if (chandeclist.next == null) System.out.println("next is null");
                else chandeclist = chandeclist.next;
              }
            }
          } else System.out.println("Error !! (ChannelEditor : removeChannel) no Channel in Chandec");
        } else System.out.println("Error !! (ChannelEditor : removeChannel) no Chandec in ChandecList");
      } 
    }

/**
 * benennt einen Channel um
 */
    void renameChannel(String oldname, String newname) {
      System.out.println("rename Channel");
      boolean renamed = false;
      if (program.chans != null) {
        absynt.ChandecList chandeclist = program.chans;
        while (chandeclist != null && !renamed) {
          if (chandeclist.head != null) {
            absynt.Chandec chandec = chandeclist.head;
            if (chandec.chan != null) {
              absynt.Channel channel = chandec.chan;
              if (channel.name.compareTo(oldname) == 0) {
              	channel.name = newname;
              	renamed = true;
              }
            } else System.out.println("Error !! (ChannelEditor : isInList) Chandec without Chandec");
          } else System.out.println("Error !! (ChannelEditor : isInList) ChandecList without Chandec");
          chandeclist = chandeclist.next;
        }
      }
    }

/**
 * liest die Channels des Programs und erzeugt den Table
 */
    void readProgram() {
// uebergebenes Program einlesen und in den Table schreiben.
      System.out.println("readProgram");
      if (program != null) {
        setTitle("ChannelEditor - "+program.name);
        absynt.ChandecList chandeclist = program.chans;
        while (chandeclist != null) {
          if (chandeclist.head != null) {
            absynt.Chandec chandec = chandeclist.head;
            if (chandec != null) {
              absynt.Channel channel = chandec.chan;
              Object[] names = {channel.name};
              channeltablemodel.addRow(names);
            } else System.out.println("Error !! (ChannelEditor : readProgram) Chandec without Chandec");
          } else System.out.println("Error !! (ChannelEditor : readProgram) ChandecList without Chandec");
          chandeclist = chandeclist.next;
        }
      } else setTitle("ChannelEditor - (null-program)");
    }

/**
 * loescht den Table
 */
    void clearTable(){
// Table loeschen
      while (channeltablemodel.getRowCount() > 0) channeltablemodel.removeRow(0);
    }

/**
 * loescht den Table und liest ihn neu ein
 */
    void refresh() {
// Table neu einlesen
      clearTable();
      readProgram();
    }

/**
 * loescht den Table und liest ihn vom neuen Program neu ein
 */
    void refresh(absynt.Program inprogram) {
// anderes Program, Table neu einlesen
      clearTable();
      program = inprogram;
      readProgram();
    }
    
    public void windowClosed(WindowEvent event) {
      try {
        this.finalize();
      } catch (java.lang.Throwable e) {
      	System.out.println(e.getMessage());
      }
    }
    public void windowDeiconified(WindowEvent event) {}
    public void windowIconified(WindowEvent event) {}
    public void windowActivated(WindowEvent event) {}
    public void windowDeactivated(WindowEvent event) {}
    public void windowOpened(WindowEvent event) {}

    public void windowClosing(WindowEvent event) {
      try {
        this.finalize();
      } catch (java.lang.Throwable e) {
      	System.out.println(e.getMessage());
      }
    }
    
/**
 * DialogFenster zur Stringeingabe
 */
    class StringDialog extends JDialog implements ActionListener {

      JTextField namefield;
      Container dialogcpane;
      ChannelEditor cheditor;

      StringDialog(ChannelEditor cheditroot, String inname) {
        super(cheditroot, "Stringeingabe", true);
        cheditor = cheditroot;
        namefield = new JTextField(inname, 20);
        JButton button_ok = new JButton("Ok");
        button_ok.setActionCommand("Ok");
        button_ok.addActionListener(this);
        dialogcpane = getContentPane();
        dialogcpane.add(namefield, BorderLayout.NORTH);
        dialogcpane.add(button_ok, BorderLayout.SOUTH);
        pack();
        setVisible(true);
//        show();
      }
      
      String getNameText() {
        return(namefield.getText());
      }

      void closeDialog () {
        try {
          this.dispose();
          this.finalize();
        } catch (java.lang.Throwable e) {
          System.out.println(e.getMessage());
        }
      }

      public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().compareTo("Ok") == 0) {
          System.out.println("Ok was klicked");
          String newname = namefield.getText();
          System.out.println("name was rescued");
          cheditor.dialogstring = newname;
          System.out.println("name was send");
          dispose();
          System.out.println("window was disposed");
        }
      }     

    }

}