/*
 * GUI.java
 *
 * Created on June 11, 2000, 12:04 AM
 */
 
package gui;

import java.util.Vector;
import java.io.File;
import editor.*;

/** 
 *
 * @author  broder@icepool.de, hkraas@web.de
 * @version 
 */
public class GUI extends javax.swing.JFrame {

    Session actualsession = null;
    
    /** Creates new form GUI */
    public GUI() {
	initComponents ();
	pack ();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
	menuBar = new javax.swing.JMenuBar ();
	sessionMenu = new javax.swing.JMenu ();
	newMenuItem = new javax.swing.JMenuItem ();
	jSeparator1 = new javax.swing.JSeparator ();
	openMenuItem = new javax.swing.JMenuItem ();
	saveMenuItem = new javax.swing.JMenuItem ();
	saveAsMenuItem = new javax.swing.JMenuItem ();
	jSeparator2 = new javax.swing.JSeparator ();
	closeMenuItem = new javax.swing.JMenuItem ();
	jSeparator3 = new javax.swing.JSeparator ();
	exitMenuItem = new javax.swing.JMenuItem ();
	editorMenu = new javax.swing.JMenu ();
	newEdMenuItem = new javax.swing.JMenuItem ();
	jSeparator4 = new javax.swing.JSeparator ();
	openEdMenuItem = new javax.swing.JMenuItem ();
	openParseEdMenuItem = new javax.swing.JMenuItem ();
	jSeparator5 = new javax.swing.JSeparator ();
	closeEdMenuItem = new javax.swing.JMenuItem ();
	toolsMenu = new javax.swing.JMenu ();
	simulatorMenuItem = new javax.swing.JMenuItem ();
	jSeparator6 = new javax.swing.JSeparator ();
	checkProcessMenuItem = new javax.swing.JMenuItem ();
	checkAllMenuItem = new javax.swing.JMenuItem ();
	helpMenu = new javax.swing.JMenu ();
	contentsMenuItem = new javax.swing.JMenuItem ();
	aboutMenuItem = new javax.swing.JMenuItem ();
	projNameTextField = new javax.swing.JTextField ();
	projBrowserTable = new javax.swing.JTable ();
	
	sessionMenu.setText ("Session");
	sessionMenu.setActionCommand ("Session");
	sessionMenu.setLabel ("Session");
	
	newMenuItem.setText ("New");
	newMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    newMenuItemActionPerformed (evt);
		}
	    }
				       );
	
	sessionMenu.add (newMenuItem);
	
	sessionMenu.add (jSeparator1);
	openMenuItem.setText ("Open");
	openMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    openMenuItemActionPerformed (evt);
	}
	    }
					);
	
	sessionMenu.add (openMenuItem);
	saveMenuItem.setText ("Save");
	saveMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
	  saveMenuItemActionPerformed (evt);
		}
	    }
				    );
	
	sessionMenu.add (saveMenuItem);
	saveAsMenuItem.setText ("Save As ...");
	saveAsMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    saveAsMenuItemActionPerformed (evt);
	}
	    }
				      );
	
	sessionMenu.add (saveAsMenuItem);
	
	sessionMenu.add (jSeparator2);
	closeMenuItem.setText ("Close");
    closeMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		closeMenuItemActionPerformed (evt);
	    }
	}
				     );
    
    sessionMenu.add (closeMenuItem);
    
    sessionMenu.add (jSeparator3);
    exitMenuItem.setText ("Exit");
    exitMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		exitMenuItemActionPerformed (evt);
	    }
	}
				    );
    
    sessionMenu.add (exitMenuItem);
    menuBar.add (sessionMenu);
    
    
    editorMenu.setText ("Editor");
    editorMenu.setEnabled(false);
    newEdMenuItem.setText ("New");
    newEdMenuItem.setActionCommand ("NewEd");
    newEdMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		newEdMenuItemActionPerformed (evt);
	    }
	}
				     );
    
    editorMenu.add (newEdMenuItem);
    editorMenu.add (jSeparator4);
    
    openEdMenuItem.setText ("Open");
    openEdMenuItem.setActionCommand ("OpenEd");
    openEdMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		openEdMenuItemActionPerformed (evt);
	    }
	}
				      );
    
    editorMenu.add (openEdMenuItem);
    openParseEdMenuItem.setText ("Open Parsed File");
    openParseEdMenuItem.setActionCommand ("OpenParseEd");
    openParseEdMenuItem.setLabel ("Open Text File");
    openParseEdMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		openParseEdMenuItemActionPerformed (evt);
	    }
	}
					   );
    
    editorMenu.add (openParseEdMenuItem);
    
    editorMenu.add (jSeparator5);
    closeEdMenuItem.setText ("Close");
    closeEdMenuItem.setActionCommand ("CloseEd");
    closeEdMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		closeEdMenuItemActionPerformed (evt);
	    }
	}
				       );
    
    editorMenu.add (closeEdMenuItem);
    menuBar.add (editorMenu);
    

    toolsMenu.setText ("Tools");
    toolsMenu.setEnabled(false);
    simulatorMenuItem.setText ("Simulator");
    simulatorMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		simulatorMenuItemActionPerformed (evt);
	    }
	}
					 );
    
    toolsMenu.add (simulatorMenuItem);
    
    toolsMenu.add (jSeparator6);
    checkProcessMenuItem.setText ("Check Process");
    checkProcessMenuItem.setActionCommand ("CheckProcess");
    checkProcessMenuItem.setLabel ("CheckProcess");
    checkProcessMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		checkProcessMenuItemActionPerformed (evt);
	    }
	}
					    );
    
    toolsMenu.add (checkProcessMenuItem);
    checkAllMenuItem.setText ("Check All");
    checkAllMenuItem.setActionCommand ("CheckAll");
    checkAllMenuItem.setLabel ("CheckAll");
    checkAllMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		checkAllMenuItemActionPerformed (evt);
	    }
	}
					);
    
    toolsMenu.add (checkAllMenuItem);
    menuBar.add (toolsMenu);
    

    helpMenu.setText ("Help");
    contentsMenuItem.setText ("Contents");
    contentsMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		contentsMenuItemActionPerformed (evt);
	    }
	}
					);
    
    helpMenu.add (contentsMenuItem);
    aboutMenuItem.setText ("About");
    aboutMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		aboutMenuItemActionPerformed (evt);
	    }
	}
				     );
    
    helpMenu.add (aboutMenuItem);
    menuBar.add (helpMenu);
    addWindowListener (new java.awt.event.WindowAdapter () {
	    public void windowClosing (java.awt.event.WindowEvent evt) {
		exitForm (evt);
	    }
	}
		       );
    
    projNameTextField.setText ("No Session opened.");
    projNameTextField.setEditable (false);
    projNameTextField.setFont (new java.awt.Font ("SansSerif", 1, 18));

    
    getContentPane ().add (projNameTextField, java.awt.BorderLayout.NORTH);
    
    projBrowserTableModel = new javax.swing.table.DefaultTableModel 
	(new Object [][] {},  new String [] { "Name", "Editor", "Checked : OK", "In Project" })  
	{  
	    Class[] types = new Class [] {
		java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
	    };
	    boolean[] canEdit = new boolean [] {
		false, true, false, true
	    };
	    
	    public Class getColumnClass (int columnIndex) {
		return types [columnIndex];
	    }
	    
	    public boolean isCellEditable (int rowIndex, int columnIndex) {
		return canEdit [columnIndex];
	    }
	};
    projBrowserTableModel.addTableModelListener(new javax.swing.event.TableModelListener () {
	    public void tableChanged(javax.swing.event.TableModelEvent evt) {
		// nur reagieren, wenn der Inhalt einer Zelle veraendert wurde
		if (evt.getType() == javax.swing.event.TableModelEvent.UPDATE) {
		    System.out.println("First Row: " + evt.getFirstRow() +
				       "\nLast Row:  " + evt.getLastRow() + 
				       "\nColumn: " + evt.getColumn() + "\n");
		    // Koordinaten des Events
		    int row = evt.getFirstRow();
		    int column = evt.getColumn();
		    // Name des Editors
		    String name = (String) projBrowserTableModel.getValueAt(row, 0);
		    Boolean visible = (Boolean) projBrowserTableModel.getValueAt(row, 1);
		    Boolean inproject = (Boolean)  projBrowserTableModel.getValueAt(row, 3);
		    actualsession.modifyEditors(name, visible, inproject);
		    
		}
	    } 
	});
    projBrowserTable.setModel(projBrowserTableModel);
    
    
    getContentPane ().add (new javax.swing.JScrollPane(projBrowserTable), java.awt.BorderLayout.CENTER);
    
    setJMenuBar (menuBar);
    
    }//GEN-END:initComponents
    
    private void aboutMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_aboutMenuItemActionPerformed
    
    private void contentsMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentsMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_contentsMenuItemActionPerformed
    
    private void checkAllMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAllMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_checkAllMenuItemActionPerformed
    
    private void checkProcessMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkProcessMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_checkProcessMenuItemActionPerformed
    
    private void simulatorMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulatorMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_simulatorMenuItemActionPerformed
    
    private void closeEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeEdMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_closeEdMenuItemActionPerformed
    
    private void openParseEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openParseEdMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_openParseEdMenuItemActionPerformed
    
    private void openEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openEdMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_openEdMenuItemActionPerformed
    
    private void newEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newEdMenuItemActionPerformed
	// Add your handling code here:
	if(actualsession!=null)
	    actualsession.addEditor();
    
    }//GEN-LAST:event_newEdMenuItemActionPerformed
    
    private void closeMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_closeMenuItemActionPerformed
    
    private void saveAsMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_saveAsMenuItemActionPerformed
    
    private void saveMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_saveMenuItemActionPerformed
    
    private void openMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
	// Add your handling code here:
    }//GEN-LAST:event_openMenuItemActionPerformed
    
    private void newMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
	// Add your handling code here:
	actualsession = new Session();
	
    }//GEN-LAST:event_newMenuItemActionPerformed
    
    private void exitMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
	System.exit (0);
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
	System.exit (0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     *
     * Opens new GUI with noSession opened.
     */
    public static void main (String args[]) {
	new GUI ().show ();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu sessionMenu;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu editorMenu;
    private javax.swing.JMenuItem newEdMenuItem;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JMenuItem openEdMenuItem;
    private javax.swing.JMenuItem openParseEdMenuItem;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JMenuItem closeEdMenuItem;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JMenuItem simulatorMenuItem;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JMenuItem checkProcessMenuItem;
    private javax.swing.JMenuItem checkAllMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JTextField projNameTextField;
    private javax.swing.JTable projBrowserTable;
    private javax.swing.table.DefaultTableModel projBrowserTableModel;
    // End of variables declaration//GEN-END:variables
    
    
    // private Elementklasse zur Verwaltung einer GUI Session
    private class Session {
	// Das Programm, welches den Automaten beschreibt
	public  absynt.Program workProgram = null;
	// Vector haelt EdInterfaces
	Vector editorinterfaces = new Vector();
		
	public Session() {
	    // Example Program benutzen
	    workProgram = (new absynt.Example()).getExample1();
	    editorMenu.setEnabled(true);
	    toolsMenu.setEnabled(true);
	    projNameTextField.setText ("New Session.");
		
	}
	
	public Session(File file) {
	    // Example Program benutzen
	    workProgram = (new absynt.Example()).getExample1();
	    editorMenu.setEnabled(true);
	    toolsMenu.setEnabled(true);
	    projNameTextField.setText ("New Session.");
	}

	private boolean containsEdInterface(String _name) {
	    EdInterface ie;
	    for(int i = 0; i < editorinterfaces.size(); i++) {
		if( ((EdInterface)editorinterfaces.elementAt(i)).name.equals(_name) )
		    return true;
	    }
	    return false;
	}
	
	private EdInterface getEdInterface(String _name) {
	    EdInterface ie;
	    for(int i = 0; i < editorinterfaces.size(); i++) {
		ie = (EdInterface)editorinterfaces.elementAt(i);
		if (ie.name.equals(_name))
		    return ie;
	    }
	    return null;
    	}
	
	// Methoden zur Verwaltung der Editoren
	public void addEditor() {
	    EdInterface ei = new EdInterface("New Process" + projBrowserTableModel.getRowCount());
	    editorinterfaces.addElement(ei);
	    projBrowserTableModel.addRow(new Object[] {
		new String("New Process" + projBrowserTableModel.getRowCount()), 
		new Boolean(true), new Boolean(false), new Boolean(true) });
	}
    

	public void modifyEditors(String name, Boolean visible, Boolean inproject) {
	    // diese Methode wird von einem Listener in projBrowserTableModel aufgerufen
	    if (containsEdInterface(name)) {
		EdInterface ie = getEdInterface(name);
		ie.setVisible(visible);
		ie.setInProject(inproject);
		
	    }
	    
	}
    }
}







