package editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import absynt.*;

public class VariablenEditor extends JFrame implements WindowListener {

    Editor editor;
    absynt.Process process;
    Container cpane;
    JTable variabletable;
    DefaultTableModel variabletablemodel;
    String dialogstring;
    StringDialog stringdialog;

/**
 * erstellt einen ChannelEditor fuer das uebergebene absynt.Program
 */
    public VariablenEditor(Editor editroot, absynt.Process inprocess) {
      super("VariablenEditor");
      setSize(250, 200);
      editor = editroot;
      process = inprocess;
      cpane = getContentPane();
      cpane.add(createButtonBar(), BorderLayout.NORTH);
      String[] coloumnnames = {"Name"};
      variabletablemodel = new javax.swing.table.DefaultTableModel(coloumnnames, 0)
      {
        public boolean isCellEditable (int row, int coloumn) { return(false);}
      };
      variabletable = new JTable(new Object[][] {}, coloumnnames);
      variabletable.setModel(variabletablemodel);
      JScrollPane scrollpane = new JScrollPane(variabletable);
      cpane.add(scrollpane, BorderLayout.CENTER);
      readProcess();
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
      
      VariablenEditor vareditor;
      
      public AddListener(VariablenEditor vareditroot) {
        vareditor = vareditroot;
      }
      
      public void actionPerformed(ActionEvent event) {
        dialogstring = "";
        stringdialog = new StringDialog(vareditor, dialogstring);
        if (dialogstring.length() > 0 && !isInList(dialogstring)) {
          addVariable(dialogstring);
//          channeltablemodel.addRow(new Object[] {dialogstring});
        }
      }
    }

    class RenameListener implements ActionListener {
      
      VariablenEditor vareditor;
      
      public RenameListener(VariablenEditor vareditroot) {
        vareditor = vareditroot;
      }
      
      public void actionPerformed(ActionEvent event) {
      	int selected = variabletable.getSelectedRow();
      	if (selected != -1) {
          dialogstring = (String)variabletablemodel.getValueAt(selected, 0);
          String oldname = dialogstring;
          stringdialog = new StringDialog(vareditor, dialogstring);
          if (dialogstring.length() > 0 && !(oldname.compareTo(dialogstring) == 0) && !isInList(dialogstring)) {
            renameVariable(oldname, dialogstring);
//            channeltablemodel.addRow(new Object[] {dialogstring});
            refresh();
          }
        }
      }
    }

    class RemoveListener implements ActionListener {
      
      VariablenEditor vareditor;
      
      public RemoveListener(VariablenEditor vareditroot) {
        vareditor = vareditroot;
      }
      
      public void actionPerformed(ActionEvent event) {
        int selected = variabletable.getSelectedRow();
        if (selected != -1) { 
          removeVariable((String)variabletablemodel.getValueAt(selected, 0));
//          channeltablemodel.removeRow(selected);
          refresh();
        }
      }
    }
 
/**
 * testet, ob bereits eine Variable mit dem uebergebenen String im Process existiert
 */
    boolean isInList(String inname) {
      System.out.println("starting search in List");
      boolean wert = false;
      if (process.vars != null) {
        absynt.VardecList vardeclist = process.vars;
        while (vardeclist != null && !wert) {
          if (vardeclist.head != null) {
            absynt.Vardec vardec = vardeclist.head;
            if (vardec.var != null) {
              absynt.Variable variable = vardec.var;
              if (variable.name.compareTo(inname) == 0) wert = true;
            } else System.out.println("Error !! (VariablenEditor : isInList) Chandec without Chandec");
          } else System.out.println("Error !! (VariablenEditor : isInList) ChandecList without Chandec");
          vardeclist = vardeclist.next;
        }
      }
      return(wert);
    }

/**
 * fuegt die Variable einen neuen Channel mit dem uebergebenen Namen hinzu
 */
    void addVariable(String inname) {
      System.out.println("addVariable");
      if (process.vars != null) {
      	absynt.VardecList vardeclist = process.vars;
      	while (vardeclist.next != null) vardeclist = vardeclist.next;
      	vardeclist.next = new absynt.VardecList(new absynt.Vardec(new absynt.Variable(inname)), null);
      } else {
        process.vars = new absynt.VardecList(new absynt.Vardec(new absynt.Variable(inname)), null);
      }
      variabletablemodel.addRow(new Object[] {inname});
    }

/**
 * loescht die Variable aus dem Process, der mit dem String uebereinstimmt
 */
    void removeVariable(String inname) {
      System.out.println("removeVariable");
      boolean removed = false;
      absynt.VardecList vardeclist= null;
      absynt.Vardec vardec = null;
      absynt.Variable variable = null;
      if (process.vars != null) {
        vardec = process.vars.head;
        if (vardec != null) {
          variable = vardec.var;
          if (variable != null) {
            if (variable.name.compareTo(inname) == 0) {
              process.vars = process.vars.next;
            } else {
              vardeclist = process.vars;
              while (vardeclist.next != null && !removed) {
              	vardec = vardeclist.next.head;
              	if (vardec != null) {
                  variable = vardec.var;
                  if (variable != null) {
                    if (variable.name.compareTo(inname) == 0) {
                      System.out.println("removing Variable");
                      vardeclist.next = vardeclist.next.next;
                      removed = true;
                    }
                  }
              	}
                if (vardeclist.next == null) System.out.println("next is null");
                else vardeclist = vardeclist.next;
              }
            }
          }
        }
      }
    }

/**
 * benennt eine Variable um
 */
    void renameVariable(String oldname, String newname) {
      System.out.println("rename Variable");
      boolean renamed = false;
      if (process.vars != null) {
        absynt.VardecList vardeclist = process.vars;
        while (vardeclist != null && !renamed) {
          if (vardeclist.head != null) {
            absynt.Vardec vardec = vardeclist.head;
            if (vardec.var != null) {
              absynt.Variable variable = vardec.var;
              if (variable.name.compareTo(oldname) == 0) {
              	variable.name = newname;
              	renamed = true;
              }
            } else System.out.println("Error !! (VariablenEditor : renameVariable) VardecList without Vardec");
          } else System.out.println("Error !! (VariablenEditor : renameVariable) VardecList without Vardec");
          vardeclist = vardeclist.next;
        }
      }
    }

/**
 * liest die Channels des Programs und erzeugt den Table
 */
    void readProcess() {
// uebergebenes Program einlesen und in den Table schreiben.
      System.out.println("readProcess");
      if (process != null) {
        setTitle("ChannelEditor - "+process.name);
        absynt.VardecList vardeclist = process.vars;
        while (vardeclist != null) {
          if (vardeclist.head != null) {
            absynt.Vardec vardec = vardeclist.head;
            if (vardec != null) {
              absynt.Variable variable = vardec.var;
              Object[] names = {variable.name};
              variabletablemodel.addRow(names);
            } else System.out.println("Error !! (VariablenEditor : readProcess) Vardec == null");
          } else System.out.println("Error !! (VariablenEditor : readProcess) VardecList without Chandec");
          vardeclist = vardeclist.next;
        }
      } else setTitle("ChannelEditor - (null-program)");
    }

/**
 * loescht den Table
 */
    void clearTable(){
// Table loeschen
      while (variabletablemodel.getRowCount() > 0) variabletablemodel.removeRow(0);
    }

/**
 * loescht den Table und liest ihn neu ein
 */
    void refresh() {
// Table neu einlesen
      clearTable();
      readProcess();
    }

/**
 * loescht den Table und liest ihn vom neuen Program neu ein
 */
    void refresh(absynt.Process inprocess) {
// anderes Program, Table neu einlesen
      clearTable();
      process = inprocess;
      readProcess();
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
      VariablenEditor vareditor;

      StringDialog(VariablenEditor vareditroot, String inname) {
        super(vareditroot, "Stringeingabe", true);
        vareditor = vareditroot;
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
          vareditor.dialogstring = newname;
          System.out.println("name was send");
          dispose();
          System.out.println("window was disposed");
        }
      }     

    }

}