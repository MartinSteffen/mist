
// SetTransitionDialog.java
//
// for editor/Editor.java of Project Mist 2000

package editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import editor.undo.*;
import absynt.*;
import editor.einterface.*;
import java.beans.PropertyVetoException;
import javax.swing.plaf.basic.*;
import gui.*;

/**
 * SetTransitionDialog init or edit the given transition for states a and b
 * Wenn eine Transition zwischen den States a und b gesetzt wird, werden auch
 * weitere Parameter fuer die jeweilige Transitionsart gebraucht. Dies soll
 * hiermit geschehen, indem man via JDialog den entsprechenden Typ der Transition
 * festlegt, und zu der jeweiligen Transitionsart dann auch die weiteren Parameter
 * direkt spezifizieren kann.
 */

public class SetTransitionDialog extends JDialog implements ActionListener, WindowListener {

    Editor editor;

    // some private, local variables to work with...
    private JLabel string_in_channel;
    private JLabel string_in_variable;
    private JLabel string_out_channel;
    private JLabel string_out_variable;
    private JLabel string_tau_variable;
    private JLabel string_var1;
    private JLabel string_var2;
    private JTextField field_in_channel;
    private JTextField field_in_variable;
    private JTextField field_out_channel;
    private JTextField field_out_variable;
    private JTextField field_var1;
    private JTextField field_var2;
    
    private Container cpane;

    private JPanel toolPanel;
    private JPanel radioPanel;
    private JPanel paramPanel;
    private JPanel paramPanel_input;
    private JPanel paramPanel_output;
    private JPanel paramPanel_assign;
    private JPanel paramPanel_tau;
  
    private JPanel activePanel;
    

    // Konstuktor SetTransitionDialog
    SetTransitionDialog (Editor editroot, Etransition etransition) {
      super(editroot, "edit transition parameters", true);
      addWindowListener(this);
      editor = editroot;

      // implement radiobuttons
      JRadioButton button_output = new JRadioButton( "output" );
      JRadioButton button_input = new JRadioButton( "input" );
      JRadioButton button_assign = new JRadioButton( "assign" );
      JRadioButton button_tau = new JRadioButton( "tau" );

      button_tau.setSelected(true);

      // implement buttons for okay and cancel
      JButton button_ok = new JButton( "OK" );
      JButton button_cancel = new JButton( "Cancel!" );

      // join all radiobuttons into a buttongroup
      ButtonGroup groupRadio = new ButtonGroup();
      groupRadio.add( button_output );
      groupRadio.add( button_input );
      groupRadio.add( button_assign );
      groupRadio.add( button_tau );

      // join all bottom-Buttons to one group
      ButtonGroup groupBottom = new ButtonGroup();
      groupBottom.add( button_ok );
      groupBottom.add( button_cancel );

      // panel with RadioButtons with downorder
      radioPanel = new JPanel();
      radioPanel.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "type", TitledBorder.LEFT, TitledBorder.ABOVE_TOP) );
      radioPanel.setLayout( new GridLayout(0,1) );
      radioPanel.add( button_input );
      radioPanel.add( button_output );
      radioPanel.add( button_assign );
      radioPanel.add( button_tau );
      // radioPanel.setPreferredSize( new Dimension(90, 120) );


      // textfield and labels for parameters input,output,assign,tau
      // I kept in mind that every Jxxxx must be initialized for each panel
      field_in_channel = new JTextField( 20 );
      field_in_variable = new JTextField( 20 );
      field_out_channel = new JTextField( 20 );
      field_out_variable = new JTextField( 20 );
      field_var1 = new JTextField( 20 );
      field_var2 = new JTextField( 20 );
      string_in_channel = new JLabel( "channel:" );
      string_in_variable = new JLabel( "variable:" );
      string_out_channel = new JLabel( "channel:" );
      string_out_variable = new JLabel( "variable:" );
      string_var1 = new JLabel( "variable1:" );
      string_var2 = new JLabel( "variable2:" );
      field_in_channel.setText( "initstring_input" );
      field_in_channel.addActionListener(this);
      field_out_channel.setText( "initstring_output" );
      field_out_channel.addActionListener(this);

      // parameter Panel for INPUT
      paramPanel_input = new JPanel();
      paramPanel_input.setLayout( new GridLayout(2,1) );
      paramPanel_input.add( string_in_channel );
      paramPanel_input.add( field_in_channel );
      paramPanel_input.add( string_in_variable );
      paramPanel_input.add( field_in_variable );
      
      // parameter Panel for OUTPUT
      paramPanel_output = new JPanel();
      paramPanel_output.setLayout( new GridLayout(2,1) );
      paramPanel_output.add( string_out_channel );
      paramPanel_output.add( field_out_channel );
      paramPanel_output.add( string_out_variable );
      paramPanel_output.add( field_out_variable );

      // parameter Panel for ASSIGN
      paramPanel_assign = new JPanel();
      paramPanel_assign.setLayout( new GridLayout(2,1) );
      paramPanel_assign.add( string_var1 );
      paramPanel_assign.add( field_var1 );
      paramPanel_assign.add( string_var2 );
      paramPanel_assign.add( field_var2 );

      // parameter Panel for TAU
      // -> still yet no parameter, we have to check this
      // -> with the uppergroup unix01 to get sure about this.
      paramPanel_tau = new JPanel();
      paramPanel_tau.setLayout( new GridLayout(0,1) );
      string_tau_variable = new JLabel( "no parameters, sorry!", JLabel.CENTER );
      paramPanel_tau.add( string_tau_variable, BorderLayout.CENTER );

      // paramPanel mit initial Panel aufrufen!
      init_paramPanel( paramPanel_tau );

      // toolPanel noch initialisieren!
      init_toolPanel();

      // panel with the okay and the cancel buttons
      JPanel bottomPanel = new JPanel();
      bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
      bottomPanel.setLayout( new GridLayout(1,0) );
      bottomPanel.add( button_ok );
      bottomPanel.add( button_cancel );

      // create all buttons and show them
      cpane = getContentPane();
      cpane.setBackground(Color.lightGray);
      cpane.setLayout(new BorderLayout());
      cpane.add( toolPanel, BorderLayout.CENTER );
      cpane.add( bottomPanel, BorderLayout.SOUTH );

      // bind radiobuttons to ActionListener with special class to
      // change to the right paramPanel & setActionCommands here.
      button_input.setActionCommand("input");
      button_output.setActionCommand("output");
      button_assign.setActionCommand("assign");
      button_tau.setActionCommand("tau");
      button_ok.setActionCommand("ok");
      button_cancel.setActionCommand("cancel");
      button_ok.addActionListener(this);
      button_cancel.addActionListener(this);

      button_input.addActionListener( new SwitchPanel( paramPanel_input ) );
      button_output.addActionListener( new SwitchPanel( paramPanel_output ) );
      button_assign.addActionListener( new SwitchPanel( paramPanel_assign ) );
      button_tau.addActionListener( new SwitchPanel( paramPanel_tau ) );

      // set compactvisuell for Java....
      pack();
      setVisible(true);
    }

    void init_paramPanel( JPanel toPanel ) {
      // panel with parameter situation specified for radioButtons
      paramPanel = new JPanel();
      paramPanel.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "parameter", TitledBorder.LEFT, TitledBorder.ABOVE_TOP) );
      paramPanel.setLayout( new GridLayout(2,1) );
      paramPanel.add( toPanel );
      paramPanel.setPreferredSize( new Dimension( 200, 120 ) );
	activePanel = toPanel;
    }

    void init_toolPanel() {
      // radioPanel and bottomPanel setting in one Border!
      toolPanel = new JPanel();
      toolPanel.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Transition", TitledBorder.LEFT, TitledBorder.ABOVE_TOP) );
      toolPanel.add( radioPanel, BorderLayout.WEST );
      toolPanel.add( paramPanel, BorderLayout.EAST );
    }


    // class SwitchPanel to exchange the paramPanel
    // Es wird jeweils das richtig paramPanel_xxx uebergeben
    // und anschliessend eingesetzt.
    class SwitchPanel implements ActionListener {
      JPanel newpanel;
      
      SwitchPanel (JPanel inpanel) {
        newpanel = inpanel;
      }
      
      public void actionPerformed(ActionEvent event) {
        paramPanel.remove(activePanel);
        activePanel = newpanel;
        paramPanel.add(activePanel, BorderLayout.EAST);
        pack();
        paramPanel.repaint();
      }
      
    }
    
    
    public void windowClosed(WindowEvent event) {}
    public void windowDeiconified(WindowEvent event) {}
    public void windowIconified(WindowEvent event) {}
    public void windowActivated(WindowEvent event) {}
    public void windowDeactivated(WindowEvent event) {}
    public void windowOpened(WindowEvent event) {}
 
    public void windowClosing(WindowEvent event) {
      System.out.println("SetTranstionDialog - entferne Dialog : ");
      dispose();
//      System.exit(0);
    } 
    
    public void actionPerformed(ActionEvent event) {
      // System.out.println("SetTransitionDialog: "+event);
      System.out.println( event.getActionCommand() );
	if( event.getActionCommand() == "cancel" ) {
		dispose();
//		System.exit(0);
        };
	if( event.getActionCommand() == "ok" ) {
		dispose();
//		System.exit(0);
        };
/*	if( event.getActionCommand() == "input" ) {
	}
	if( event.getActionCommand() == "output" ) {
	}
	if( event.getActionCommand() == "assign" ) {
	}
	if( event.getActionCommand() == "tau" ) {
	}
*/
    }
    
    public static void main (String[] args) {
      SetTransitionDialog stdialog = new SetTransitionDialog(null, null);
    }

}
