/*
 * GUI.java
 *
 * Created on June 11, 2000, 12:04 AM
 */
 
package gui;

import java.util.Vector;
import java.io.*;
import editor.*;

/** 
 *
 * @author  broder@icepool.de, hkraas@web.de
 * @version 
 */
public class GUI extends javax.swing.JFrame {

    Session actualsession = null;
    gui.SimUI mySimUI = null;
    
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
		    // Koordinaten des Events
		    int row = evt.getFirstRow();
		    int column = evt.getColumn();
		    // Name des Editors
		    String name = (String) projBrowserTableModel.getValueAt(row, 0);
		    Boolean visible = (Boolean) projBrowserTableModel.getValueAt(row, 1);
		    Boolean inproject = (Boolean)  projBrowserTableModel.getValueAt(row, 3);
		    // actualsession.modifyEditors(name, visible, inproject);
		    
		}
	    } 
	});
    projBrowserTable.setModel(projBrowserTableModel);
    
    
    getContentPane ().add (new javax.swing.JScrollPane(projBrowserTable), java.awt.BorderLayout.CENTER);
    
    setJMenuBar (menuBar);
    
    }//GEN-END:initComponents
    
    private void aboutMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }
    
    private void contentsMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }
    
    private void checkAllMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }
    
    private void checkProcessMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }
    
    private void simulatorMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
	// mySimUI = new gui.SimUI(new simulator.Simulator(this));
	// mySimUI.show();
    }
    
    private void closeEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
	Vector names = new Vector();
	int i;
	while((i = projBrowserTable.getSelectedRow()) != -1) {
	    names.addElement((String) projBrowserTable.getValueAt(i, 0));
	    projBrowserTableModel.removeRow(i);
	}
	
	// actualsession.closeEditors(names);
    }
    
    private void openParseEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }
    
    private void openEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }
    
    private void newEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
	// if(actualsession!=null)
	//     actualsession.addEditor();
    
    }
    
    private void closeMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	saveMenuItemActionPerformed(evt);
	actualsession = null;
	// Menues setzen
	editorMenu.setEnabled(false);
	toolsMenu.setEnabled(false);
	// Firlefanz
	projNameTextField.setText("");
	setTitle("");
    }

    
    
    private void saveAsMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	File f;
	
	if (actualsession == null)
	    return;
	javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
	ExampleFileFilter filter = new ExampleFileFilter(); 
	filter.addExtension("mst");
	filter.setDescription("Mist Project Files"); 
	chooser.setFileFilter(filter); 
	int returnVal = chooser.showSaveDialog(this); 
	if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) { 
	    f = chooser.getSelectedFile();
	    actualsession.filename = f.getAbsolutePath(); 
	    System.out.println("trying to save Session as: " + f.getAbsolutePath() + "..."); 
	    
 
	    FileOutputStream ostream;
	    ObjectOutputStream p;
	    
	    try {
		ostream = new FileOutputStream(f.getAbsolutePath());
		p = new ObjectOutputStream(ostream);
		p.writeObject(actualsession);
		p.flush();
		ostream.close();
	    } catch (IOException e) {
		System.err.println("IOException in saveAsMenuItemActionPerformed: Save\n" + e);
		actualsession.filename = "";
	    }
	} 
    }
    
    private void saveMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession == null) 
	    return;
	if (actualsession.filename.equals("")) // Es wurde noch nie gespeichert
	    saveAsMenuItemActionPerformed(evt); 
	else { 
	    
	    
	    FileOutputStream ostream;
	    ObjectOutputStream p;
	    try {
		ostream = new FileOutputStream(actualsession.filename);
		p = new ObjectOutputStream(ostream);
		p.writeObject(actualsession);
		p.flush();
		ostream.close();
	    } catch (IOException e) {
		System.err.println("IOException in saveMenuItemActionPerformed: Save\n" + e);
	    }
	}
	
    }
    
    private void openMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	File f;
	if (actualsession != null)
	    saveMenuItemActionPerformed(evt); // Session speichern
	actualsession = null;
	
	javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
	ExampleFileFilter filter = new ExampleFileFilter(); 
	filter.addExtension("mst");
	filter.setDescription("Mist Project Files"); 
	chooser.setFileFilter(filter); 
	int returnVal = chooser.showOpenDialog(this); 
	if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) { 
	    f = chooser.getSelectedFile();
	    System.out.println("trying to load: " + f.getAbsolutePath() + " ..."); 
	    
	   
	    if (! f.isFile()) {
		System.err.println("Konnte " + f.getAbsolutePath() + " nicht finden");
		return;
	    }        
	    try {
		FileInputStream istream;
		ObjectInputStream p;
		
		istream = new FileInputStream(f);
		p = new ObjectInputStream(istream);
		
		// Aehaem ... 
		actualsession = (Session) p.readObject();
		istream.close();
	    } catch (IOException e) {
		System.err.println("IOException in: openMenuItemActionPerformed\n" + e.getMessage());
	    } catch (ClassNotFoundException e) {
		System.err.println("ClassNotFoundException in: openMenuItemActionPerformed\n" + e.getMessage());
	    }
	}
	// Menues setzen
	editorMenu.setEnabled(true);
	toolsMenu.setEnabled(true);
	// Firlefanz
	projNameTextField.setText(actualsession.name);
	setTitle(actualsession.name);

 	
    }
    
    private void newMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession != null)
	    saveMenuItemActionPerformed(evt);
	
	actualsession = new Session();
	// Menues setzen
	editorMenu.setEnabled(true);
	toolsMenu.setEnabled(true);
	// Firlefanz
	projNameTextField.setText(actualsession.name);
	setTitle(actualsession.name);
    }
    
    private void exitMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	saveMenuItemActionPerformed(evt);
	System.exit (0);
    }
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {
	System.exit (0);
    }
    
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
    

    
    // **************** Session **************************************+
    // private Elementklasse zur Verwaltung einer GUI Session
    static  class Session implements java.io.Serializable {
	// Name der Session
	public String name = new String("");
	public String filename = new String("");
	
	// Das Programm, welches den Automaten beschreibt
	private absynt.Program workProgram = null;
	
	// Ein Editor, der alle geoeffneten Editoren handelt 
	private editor.Editor editor = null;
	
	// ****************  Konstruktoren  ****************
	
	// Aufgerufen von: Session->New
	public Session() {
	    name = "Example Program";
	    
	    workProgram = (new absynt.Example()).getExample1(); // Example Program benutzen
	}
	

	/*
	// Methoden zur Verwaltung der Editoren
	public void addEditor() {
	    
	    EdInterface ei = new EdInterface("New Process" + projBrowserTableModel.getRowCount());
	    editorinterfaces.addElement(ei);
	    projBrowserTableModel.addRow(new Object[] {
		new String("New Process" + projBrowserTableModel.getRowCount()), 
		new Boolean(true), new Boolean(false), new Boolean(true) });
	}
    
	public void closeEditors(Vector names) {
	    for (int i = 0; i < names.size(); i++) {
		if (containsEdInterface((String) names.elementAt(i))) {
		    EdInterface ie = getEdInterface((String) names.elementAt(i));
		    if(! editorinterfaces.contains(ie)) 
			System.err.println("Session.closeEditors: didn't found EdInterface in Vector.");
		    else {
			// EdInterface aus dem Vector werfen
			ie.destroyEditor();
			editorinterfaces.removeElement(ie);
		    }
		}
	    }
	}

	public void modifyEditors(String name, Boolean visible, Boolean inproject) {
	    // diese Methode wird von einem Listener in projBrowserTableModel aufgerufen
	    if (containsEdInterface(name)) {
		EdInterface ie = getEdInterface(name);
		ie.setVisible(visible);
		ie.setInProject(inproject);
	    }
	    
	}
	*/
    }
}







