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
 * EditorAboutDialog show some important notice developed thru programming this
 * nothing serious in here, only some JPanels und JLabels in a JDialog.
 */
class EditorAboutDialog extends JDialog implements ActionListener, WindowListener {

  Editor editor;

  EditorAboutDialog( Editor editroot ) {
      super(editroot, "Project Mist Editor About", true);
      addWindowListener(this);
      editor = editroot;

      Container content = getContentPane();
      Font font = new Font("Tahoma", Font.PLAIN, 9);
      content.setFont(font);

      String labelTextHeader =
        "<html><B>Project Mist Editor</B><br>" +
        "</html>";
      JLabel boldLabel = new JLabel( labelTextHeader, JLabel.CENTER);
      boldLabel.setBorder(BorderFactory.createTitledBorder("Global About"));
      content.add(boldLabel, BorderLayout.NORTH);

      String labelTextCenter =
        "<html><blockquote><font face=helvetica size=2>"+
        "This Editor was programmed by" +
        "<UL>" +
        "  <LI>Alexander Eckloff  ( ataler@gmx.de )" +
        "  <LI>Finn Jacobs  ( finnjacobs@gmx.de )" +
        "</UL>" +
        "</font></blockquote>";
      JLabel fancyLabel = new JLabel( labelTextCenter, new ImageIcon("editor/images/about.gif"), JLabel.CENTER);
      fancyLabel.setBorder(BorderFactory.createTitledBorder("Special Notes"));
      content.add(fancyLabel, BorderLayout.CENTER);

      JButton button_okay = new JButton( "OK" );
      JPanel southPanel = new JPanel();
      southPanel.add( button_okay );
      southPanel.setBorder( BorderFactory.createEmptyBorder(20,20,20,20) );
      content.add( southPanel, BorderLayout.SOUTH );
      button_okay.setActionCommand( "ok" );
      button_okay.addActionListener(this);

      // ... show, what we've assigned till now
      pack();
      setVisible(true);
    }

    // i think, we have this not to comment any further, dont we?!
    public void windowClosed(WindowEvent event) {}
    public void windowDeiconified(WindowEvent event) {}
    public void windowIconified(WindowEvent event) {}
    public void windowActivated(WindowEvent event) {}
    public void windowDeactivated(WindowEvent event) {}
    public void windowOpened(WindowEvent event) {}
 
    public void windowClosing(WindowEvent event) {
      System.out.println("EditorAboutDialog - entferne Dialog : ");
      dispose();
//      System.exit(0);
    } 
    
    public void actionPerformed(ActionEvent event) {
      System.out.println( event.getActionCommand() );
	if( event.getActionCommand() == "ok" ) {
		dispose();
//		System.exit(0);
        };

    }


  public static void main( String[] args ) {
    new EditorAboutDialog( null );
  }



}

