// SetStateDialog.java
//
// for editor/Editor.java of Project Mist 2000


package editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import editor.undo.*;
import absynt.*;
import editor.einterface.*;
import java.beans.PropertyVetoException;
import javax.swing.plaf.basic.*;
import gui.*;

/**
 * SetStateDialog contains the dialog to insert the necessary information for a
 * state implemented in state of absynt.
 *
 * Nichts grossartiges hier, dieses Dialog dient nur dem Einstellen von Namen,
 * Start oder Endzustand (kommt das noch in die Absynt? ;-).
 *
 */
 
class SetStateDialog extends JDialog implements ActionListener, WindowListener {
 
    // variables...
    Editor editor;
    Container cpane;
    Estate estate;
    Eprocess eprocess;
    JTextField namefield;
    boolean init;
    JRadioButton normal_state;
    JRadioButton init_state;
    int intype;
 
    /**
      * konstruktor inits the dialog with buttons, stringgadgets etc.
      * nothing special about this... 
      */
    SetStateDialog (Editor editroot, Eprocess inprocess, Estate estate, boolean initial) {
      super(editroot, "SetStateDialog", true);
      addWindowListener(this);
      this.editor = editroot;
      this.estate = estate;
      this.eprocess = inprocess;
      this.init = initial;
      if (estate != null ) this.intype = estate.type;
      else this.intype = -1;
      
      cpane = getContentPane();
      
      // panel in top of the dialog
      Border etchedborder1 = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "name", TitledBorder.LEFT, TitledBorder.ABOVE_TOP);
      JPanel northpanel = new JPanel();
      northpanel.setBorder(etchedborder1);
      namefield = new JTextField(15);
      if (estate != null) namefield.setText(estate.getName());
      northpanel.add(namefield);

      // panel in center of the dialog
      Border etchedborder2 = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "type", TitledBorder.LEFT, TitledBorder.ABOVE_TOP);
      JPanel centerpanel = new JPanel();
      centerpanel.setLayout(new GridLayout(1, 0));
      centerpanel.setBorder(etchedborder2);

      // radiobuttons in the centerpanel for
      // state type -> normal or init
      normal_state = new JRadioButton("normal");
      init_state = new JRadioButton("init");
      
      // group the radiobuttons for best choice ;-)
      ButtonGroup radiogroup = new ButtonGroup();
      radiogroup.add(normal_state);
      radiogroup.add(init_state);
      if (estate != null) {
        if (estate.type == 0) {
          normal_state.setSelected(true);
        } else {
          init_state.setSelected(true);
	}
      } else {
        normal_state.setSelected(true);
      }
      
      // add radiobuttons to the center panel
      centerpanel.add(normal_state);
      centerpanel.add(init_state);
           
      // panel in bottom of the dialog
      JPanel southpanel = new JPanel();      
      JButton b_ok = new JButton("Ok");
      JButton b_cancel = new JButton("Cancel");
      
      // set ActionCommands and ActionListener to
      // the buttons in the bottom panel
      b_ok.setActionCommand("Ok");
      b_cancel.setActionCommand("Cancel");
      
      b_ok.addActionListener(this);
      b_cancel.addActionListener(this);

      // add bottom buttons to the bottom panel
      southpanel.add(b_ok, BorderLayout.WEST);
      southpanel.add(b_cancel, BorderLayout.EAST);
      
      // add panels to the container...
      cpane.add(northpanel, BorderLayout.NORTH);
      cpane.add(centerpanel, BorderLayout.CENTER);
      cpane.add(southpanel, BorderLayout.SOUTH);
      
      // ... and show the how they look like 8-)
      pack();
      setVisible(true);
    }
    
    // windowListener and Actionlistener stuff beginning
    // here...
    public void windowClosed(WindowEvent event) {}
    public void windowDeiconified(WindowEvent event) {}
    public void windowIconified(WindowEvent event) {}
    public void windowActivated(WindowEvent event) {}
    public void windowDeactivated(WindowEvent event) {}
    public void windowOpened(WindowEvent event) {}
 
    public void windowClosing(WindowEvent event) {
      System.out.println("entferne Dialog : ");
      dispose();
//      System.exit(0);
    } 
    
    public void actionPerformed(ActionEvent event) {
      if (event.getActionCommand().compareTo("Ok") == 0) {
      	System.out.println("Ok");
        dispose();
        String statename = namefield.getText();
        if (this.init) statename = editor.checkStateName(eprocess, namefield.getText());
        if (estate == null) {
          System.out.println("(SetStateDialog) instate was null");
          if (normal_state.isSelected()) {
            estate = new Estate(statename, null, -1, -1, 0);
          } else {
            estate = new Estate(statename, null, -1, -1, 1);
          }
        } else {
          System.out.println("(SetStateDialog) instate not null");
          if (normal_state.isSelected()) {
            estate.setNormalState(statename, estate.getExpr());
            if (intype == 1) eprocess.updateTransitions();
          } else {
            estate.setInitState(statename, estate.getExpr());
            if (intype == 0) eprocess.updateTransitions();
          }
        }
        if (this.init) {
          if (this.eprocess != null) this.eprocess.addState(this.estate);
        }
      }
      else if (event.getActionCommand().compareTo("Cancel") == 0) {
      	System.out.println("Cancel");
//      System.out.println("SetStateDialog: "+event);
        dispose();
      }  
    }
    
    // for normal testing... #-)
/*
    public static void main (String[] args) {
      SetStateDialog stdialog = new SetStateDialog(null, null, null, true);
    }
*/
}

