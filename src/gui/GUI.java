
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
    editor.Editor actualeditor = null;
    gui.SimUI mySimUI = null;
    modcheck.Modelchecker modelchecker = null;
    
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
	closeEdMenuItem = new javax.swing.JMenuItem ();
	openParseEdMenuItem = new javax.swing.JMenuItem ();
	jSeparator5 = new javax.swing.JSeparator ();
	removeEdMenuItem = new javax.swing.JMenuItem ();
	toolsMenu = new javax.swing.JMenu ();
	simulatorMenuItem = new javax.swing.JMenuItem ();
	jSeparator6 = new javax.swing.JSeparator ();
	modelcheckerMenuItem = new javax.swing.JMenuItem();
	jSeparator7 = new javax.swing.JSeparator ();

	checkProcessMenuItem = new javax.swing.JMenuItem ();
	checkAllMenuItem = new javax.swing.JMenuItem ();
	helpMenu = new javax.swing.JMenu ();
	contentsMenuItem = new javax.swing.JMenuItem ();
	aboutMenuItem = new javax.swing.JMenuItem ();
	projNameTextField = new javax.swing.JTextField ();
	projBrowserTable = new javax.swing.JTable ();
	
	// *** Menue Session ***
	sessionMenu.setText ("Session");
	sessionMenu.setActionCommand ("Session");
		
	newMenuItem.setText ("New");
	newMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    newMenuItemActionPerformed (evt);
		}});
	sessionMenu.add (newMenuItem);
	
	sessionMenu.add (jSeparator1);

	openMenuItem.setText ("Open");
	openMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    openMenuItemActionPerformed (evt);
		}});
	sessionMenu.add (openMenuItem);

	openParseEdMenuItem.setText ("Open Parsed File");
	openParseEdMenuItem.setActionCommand ("OpenParseEd");
	openParseEdMenuItem.addActionListener (new java.awt.event.ActionListener () {
	    public void actionPerformed (java.awt.event.ActionEvent evt) {
		openParseEdMenuItemActionPerformed (evt);
	    }});
	sessionMenu.add (openParseEdMenuItem);

	
	saveMenuItem.setText ("Save");
	saveMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    saveMenuItemActionPerformed (evt);
		}});
	sessionMenu.add (saveMenuItem);
	
	saveAsMenuItem.setText ("Save As ...");
	saveAsMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    saveAsMenuItemActionPerformed (evt);
		}});
	sessionMenu.add (saveAsMenuItem);
	
	sessionMenu.add (jSeparator2);
	
	closeMenuItem.setText ("Close");
	closeMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    closeMenuItemActionPerformed (evt);
		}});
	sessionMenu.add (closeMenuItem);
	
	sessionMenu.add (jSeparator3);
    
	exitMenuItem.setText ("Exit");
	exitMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    exitMenuItemActionPerformed (evt);
		}});
	sessionMenu.add (exitMenuItem);
	
	menuBar.add (sessionMenu);
	
	// *** Menue Editor ***
	editorMenu.setText ("Editor");
	editorMenu.setEnabled(false);
	
	newEdMenuItem.setText ("New");
	newEdMenuItem.setActionCommand ("NewEd");
	newEdMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    newEdMenuItemActionPerformed (evt);
		}});
	editorMenu.add (newEdMenuItem);
    
	editorMenu.add (jSeparator4);
	
	openEdMenuItem.setText ("Open");
	openEdMenuItem.setActionCommand ("OpenEd");
	openEdMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    openEdMenuItemActionPerformed (evt);
		}});
    	editorMenu.add (openEdMenuItem);
	
	closeEdMenuItem.setText ("Close");
	closeEdMenuItem.setActionCommand ("CloseEd");
	closeEdMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    closeEdMenuItemActionPerformed (evt);
		}});
        editorMenu.add (closeEdMenuItem);

    
	editorMenu.add (jSeparator5);
    
	removeEdMenuItem.setText ("Remove");
	removeEdMenuItem.setActionCommand ("RemoveEd");
	removeEdMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    removeEdMenuItemActionPerformed (evt);
		}});
        editorMenu.add (removeEdMenuItem);
	
	menuBar.add (editorMenu);
    
	// *** Menue Tools ***
	toolsMenu.setText ("Tools");
	toolsMenu.setEnabled(false);
	simulatorMenuItem.setText ("Simulator");
    
	simulatorMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    simulatorMenuItemActionPerformed (evt);
		}});
	toolsMenu.add (simulatorMenuItem);
    
	toolsMenu.add (jSeparator6);
	modelcheckerMenuItem.setText("ModelChecker");
	modelcheckerMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    modelcheckerMenuItemActionPerformed (evt);
		}});
	toolsMenu.add (modelcheckerMenuItem);
	
	toolsMenu.add (jSeparator7);
	
	checkProcessMenuItem.setText ("Check Process");
	checkProcessMenuItem.setActionCommand ("CheckProcess");
		checkProcessMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    checkProcessMenuItemActionPerformed (evt);
		}});
        toolsMenu.add (checkProcessMenuItem);
   
	checkAllMenuItem.setText ("Check All");
	checkAllMenuItem.setActionCommand ("CheckAll");
		checkAllMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    checkAllMenuItemActionPerformed (evt);
		}});
        toolsMenu.add (checkAllMenuItem);
	
	menuBar.add (toolsMenu);
    
	// *** Menue Help ***
	helpMenu.setText ("Help");
	contentsMenuItem.setText ("Contents");
	contentsMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    contentsMenuItemActionPerformed (evt);
		}});
	helpMenu.add (contentsMenuItem);

	aboutMenuItem.setText ("About");
	aboutMenuItem.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    aboutMenuItemActionPerformed (evt);
		}});
	helpMenu.add (aboutMenuItem);
	
	menuBar.add (helpMenu);
	
	addWindowListener (new java.awt.event.WindowAdapter () {
		public void windowClosing (java.awt.event.WindowEvent evt) {
		    exitForm (evt);
		}});
	
	setJMenuBar (menuBar);
	
	// ** Titel ***
	setTitle("Mist GUI");
	
	// *** TextField fuer den Namen der Session ***
	projNameTextField.setText ("No Session opened.");
	projNameTextField.setEditable (false);
	projNameTextField.setFont (new java.awt.Font ("SansSerif", 1, 18));
	getContentPane ().add (projNameTextField, java.awt.BorderLayout.NORTH);
	
	// *** Process Table ***
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
			// Den call an den Editor umleiten
			if (actualeditor == null)
			    return;
			if (visible.booleanValue())
			    actualeditor.OpenProcess(name);
			else actualeditor.CloseProcess(name);
		    }
} 
	    });
	projBrowserTable.setModel(projBrowserTableModel);
	getContentPane ().add (new javax.swing.JScrollPane(projBrowserTable), java.awt.BorderLayout.CENTER);
	
	
    }//GEN-END:initComponents




    // ******  Callback Prozeduren ******
    
    private void aboutMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }
    
    private void contentsMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }

       
    // ******  Callbacks aus Tools  ******

    private void checkAllMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession == null)
	    return;
	checks1.checks c = new checks1.checks(actualsession.workProgram);
	c.start_check();
    }
    
    private void checkProcessMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
    }

    private void modelcheckerMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession == null)
	    return;
	if (modelchecker == null)
	    modelchecker = new modcheck.Modelchecker();
	try {
	    modelchecker.start_modcheck(actualsession.workProgram);
	} catch(modcheck.MCheckException me) {
	    System.out.println(me.getMessage());
	}
	System.out.println("ModelChecker durchgelaufen");
    }

    private void simulatorMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	mySimUI = new gui.SimUI(new simulator.Simulator(this), actualsession.workProgram);
	mySimUI.show();
    }

    
    // ******  Callbacks aus Editor  ******

    private void newEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession == null)
	    return;
	if (actualeditor == null)
	    return;
	// Editor soll neuen Prozess anholen ...
	String id = actualeditor.NewProcess(); 
	// ... und eintragen
	projBrowserTableModel.addRow(new Object[] {
	    id, new Boolean(true), new Boolean(false), new Boolean(true) });
    }
    
    private void openEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession == null)
	    return;
	if (actualeditor == null)
	    return;
	// An Editor umleiten und TableModel updaten
	String id;
	int i;
	while((i = projBrowserTable.getSelectedRow()) != -1) {
	    id = (String) projBrowserTable.getValueAt(i, 0);
	    actualeditor.OpenProcess(id);
	    projBrowserTableModel.setValueAt( new Boolean(true), i, 1) ;
	    projBrowserTable.removeRowSelectionInterval(i,i);
	}
    }
      
    private void closeEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession == null)
	    return;
	if (actualeditor == null)
	    return;
	// An Editor umleiten und TableModel updaten
	String id;
	int i;
	while((i = projBrowserTable.getSelectedRow()) != -1) {
	    id = (String) projBrowserTable.getValueAt(i, 0);
	    actualeditor.CloseProcess(id);
	    projBrowserTableModel.setValueAt( new Boolean(false), i, 1) ;
	    projBrowserTable.removeRowSelectionInterval(i,i);
	}
    }
    
    private void removeEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession == null)
	    return;
	if (actualeditor == null)
	    return;
	
	String id;
	int i;
	while((i = projBrowserTable.getSelectedRow()) != -1) {
	    id = (String) projBrowserTable.getValueAt(i, 0);
	    actualeditor.RemoveProcess(id);
	    projBrowserTableModel.removeRow(i);
	}
    }
    

    // ******  Callbacks aus Session  ******
    
    private void closeMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession == null)
	    return;
	
	saveMenuItemActionPerformed(evt);
	actualsession = null;
	actualeditor.refresh(); // Der Editor soll  den Kram verwerfen

	// Alle Eintraege aus der ProjectTable entferning
	while(projBrowserTableModel.getRowCount() > 0)
	    projBrowserTableModel.removeRow(0);
	
	// Menues setzen
	editorMenu.setEnabled(false);
	toolsMenu.setEnabled(false);
	// Firlefanz
	projNameTextField.setText("No Session opened.");
    }

    private void saveAsMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession == null)
	    return;

	File f;
	javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
	ExampleFileFilter filter = new ExampleFileFilter(); 
	filter.addExtension("blob");
	filter.setDescription("Mist Project Files"); 
	chooser.setFileFilter(filter); 
	int returnVal = chooser.showSaveDialog(this); 
	if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) { 
	    f = chooser.getSelectedFile();
	    f = new File(f.getAbsolutePath() + ".blob");
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
		System.err.println("IOException in saveAsMenuItemActionPerformed\n" + e);
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
		System.err.println("IOException in saveMenuItemActionPerformed\n" + e);
	    }
	}
	
    }

    private void openParseEdMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession != null)
	    saveMenuItemActionPerformed(evt); // Session speichern
	
	actualsession = null;
	File f;
	javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
	ExampleFileFilter filter = new ExampleFileFilter(); 
	filter.addExtension("mst");
	filter.setDescription("Mist Parsed Files"); 
	chooser.setFileFilter(filter); 
	int returnVal = chooser.showOpenDialog(this); 
	if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) { 
	    /*
	    f = chooser.getSelectedFile();
	    System.out.println("loading: " + f.getAbsolutePath() + " ..."); 
	    if (! f.isFile()) {
		System.err.println("Couldn't find  " + f.getAbsolutePath());
		return;
	    }        
	    // Parser aufrufen, laesst sich im Moment noch nicht kompilieren !!!
	     
	    absynt.Program p = null;
	    try {
		p = (new parser.Parser() ).parseFile(f);
	    } 
	    catch (pareser.ParseException pe) {
		return;
	    }
	    catch (java.io.IOException ioe) {
		return;
	    }
	    // Falls keine Exception geworfen wurde, jedoch kein Program enstand ...
	    if (p == null) {
		System.out.println("Couldn't resolve Program out of " + f.getAbsolutePath() );
		return;
	    }
	    // Alles hat geklappt, also Dialog zum starten einer neuen Session anholen
	    new NewSessionUI(this, "Creating new Session", true);
	    // Bemerkung: actualsession ist jetzt neu angelegt (ueber this)
	    // Jetzt kann das workprogram gesetzt werden
	    actualsession.workProgram = p;
	    
	    // Editor starten mit dem neuen workProgram
	    try {
		if (actualeditor == null) // Editor wurde noch nie gestartet
		    actualeditor = new editor.Editor(this, actualsession.workProgram);
		if (actualeditor != null) // Dem Editor das refresh-Signal senden
		    actualeditor.refresh(actualsession.workProgram);
	    } catch (Exception e) {
		System.err.println("Exception in: openParseEdMenuItemActionPerformed " + e.getMessage());
		return;
	    }
	    if (!actualeditor.isShowing())
		actualeditor.show();
	    
	    redrawProcessTable(actualeditor.getProcessIds());
	    // GUI anpassen
	    editorMenu.setEnabled(true);
	    toolsMenu.setEnabled(true);
	    projNameTextField.setText(actualsession.name);
	    */
	}
	
    }
    
    private void openMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession != null)
	    saveMenuItemActionPerformed(evt); // Session speichern

	actualsession = null;
	File f;
	javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
	ExampleFileFilter filter = new ExampleFileFilter(); 
	filter.addExtension("blob");
	filter.setDescription("Mist Project Files"); 
	chooser.setFileFilter(filter); 
	int returnVal = chooser.showOpenDialog(this); 
	if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) { 
	    f = chooser.getSelectedFile();
	    System.out.println("loading: " + f.getAbsolutePath() + " ..."); 
	    
	    if (! f.isFile()) {
		System.err.println("Couldn't find  " + f.getAbsolutePath());
		return;
	    }        
	    try {
		FileInputStream istream;
		ObjectInputStream p;
		
		istream = new FileInputStream(f);
		p = new ObjectInputStream(istream);
		actualsession = (Session) p.readObject();
		istream.close();
	    } catch (IOException e) {
		System.err.println("IOException in: openMenuItemActionPerformed\n" + e.getMessage());
	    } catch (ClassNotFoundException e) {
		System.err.println("ClassNotFoundException in: openMenuItemActionPerformed\n" + e.getMessage());
	    }
	    // Editor starten mit dem aktuellen workProgram
	    if (actualeditor == null) // Editor wurde noch nie gestartet
		actualeditor = new editor.Editor(this, actualsession.workProgram);
	    else if (actualeditor != null) // Dem Editor das refresh-Signal senden
		actualeditor.refresh(actualsession.workProgram);
	    
	    // GUI anpassen
	    editorMenu.setEnabled(true);
	    toolsMenu.setEnabled(true);
	    projNameTextField.setText(actualsession.name);
	    
	    redrawProcessTable(actualeditor.getProcessIds());

	
	}
    }
    
    private void newMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	if (actualsession != null)
	    saveMenuItemActionPerformed(evt);
	actualsession = null;
	// Dialog zum starten einer neuen Session
	new NewSessionUI(this, "Creating new Session", true);
	// Bemerkung: actualsession ist jetzt neu angelegt (ueber this)
	
	// Editor starten mit dem aktuellen workProgram
	try {
	    if (actualeditor == null) // Editor wurde noch nie gestartet
		actualeditor = new editor.Editor(this, actualsession.workProgram);
	    if (actualeditor != null) // Dem Editor das refresh-Signal senden
		actualeditor.refresh(actualsession.workProgram);
	} catch (Exception e) {
	    System.err.println("Exception in: newMenuItemActionPerformed" + e.getMessage());
	    return;
	}
	if (!actualeditor.isShowing())
	    actualeditor.show();
	
	redrawProcessTable(actualeditor.getProcessIds());
	// GUI anpassen
	editorMenu.setEnabled(true);
	toolsMenu.setEnabled(true);
	projNameTextField.setText(actualsession.name);
    }
    
    private void exitMenuItemActionPerformed (java.awt.event.ActionEvent evt) {
	saveMenuItemActionPerformed(evt);
	if (actualeditor != null)
	    actualeditor.dispose();
	System.exit (0);
    }
    
    private void exitForm(java.awt.event.WindowEvent evt) {
    }
    
    


    // *** weiter Prozeduren ***

    /**
     * highlighting interface fuer simulator<-->editor
     */
    public void highlightState(absynt.Astate state, absynt.Process process,  absynt.Program program) {
	actualeditor.highlightState( state,  process,  program);
    }

    /**
     * highlighting interface fuer simulator<-->editor
     */   
    public void unhighlightTransition(absynt.Transition transition, absynt.Process process, absynt.Program program) {
	actualeditor.unhighlightTransition( transition,  process,  program);
    }
    /**
     * highlighting interface fuer simulator<-->editor
     */
    public void unhighlightState(absynt.Astate state, absynt.Process process,  absynt.Program program) {
	actualeditor.unhighlightState( state,  process,   program);
    }

    /**
     * highlighting interface fuer simulator<-->editor
     */   
    public void highlightTransition(absynt.Transition transition, absynt.Process process, absynt.Program program) {
	actualeditor.highlightTransition( transition,  process,  program);
    }

    /**
     * einen neuen Process in die ProcessTable aufnehmen
     */
    public void NewProcess(String id) {
	projBrowserTableModel.addRow
	    (new Object[] { id, new Boolean(true), new Boolean(false), new Boolean(true) });
    }
    
    /**
     * einen Process aus der ProcessTable entfernen
     */
    public void RemoveProcess(String id) {
	int row = 0;
	while ( (row < projBrowserTableModel.getRowCount()) && 
		!(((String) projBrowserTableModel.getValueAt(row, 0)).equals(id)))
	    row++;
	if ( ((String)projBrowserTableModel.getValueAt(row, 0)).equals(id)) // sicher ist sicher
	    projBrowserTableModel.removeRow(row);
    }

    /**
     * einen Process in der ProcessTable als visible markieren
     */
    public void OpenProcess(String id) {
	int row = 0;
	while ((row < projBrowserTableModel.getRowCount())
	       && !(((String) projBrowserTableModel.getValueAt(row, 0)).equals(id)))
	    row++;
	if ( ((String)projBrowserTableModel.getValueAt(row, 0)).equals(id)) // sicher ist sicher
	    projBrowserTableModel.setValueAt(new Boolean(true), row, 1);
    }
    
    /**
     * einen Process in der ProcessTable als hidden markieren
     */
     public void CloseProcess(String id) {
	 int row = 0;
	 while ( (row < projBrowserTableModel.getRowCount()) 
		 && !(((String) projBrowserTableModel.getValueAt(row, 0)).equals(id)))
	     row++;
	 if ( ((String)projBrowserTableModel.getValueAt(row, 0)).equals(id)) // sicher ist sicher
	     projBrowserTableModel.setValueAt(new Boolean(false), row, 1);
     }

    private void redrawProcessTable(String [] ids) {
	// Alle Eintraege wegnehmen ...
	while(projBrowserTableModel.getRowCount() > 0)
	    projBrowserTableModel.removeRow(0);
	
	// ... und neu zeichen
	for (int i=0; i < ids.length; i++) {
	    projBrowserTableModel.addRow(new Object[] {
		ids[i], new Boolean(true), new Boolean(false), new Boolean(true) });
	}
    }
    
    
    /**
     * @param args the command line arguments
     *
     * Opens new GUI with noSession opened.
     */
    public static void main (String args[]) {
	new GUI ().show ();
    }
    
    // *** Variables declaration ***
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
    private javax.swing.JMenuItem closeEdMenuItem;
    private javax.swing.JMenuItem openParseEdMenuItem;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JMenuItem removeEdMenuItem;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JMenuItem simulatorMenuItem;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JMenuItem modelcheckerMenuItem;
    private javax.swing.JSeparator jSeparator7;
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
	
	// Das Programm, welches den aktuellen Automaten beschreibt
	private absynt.Program workProgram = null;
	
	// ****************  Konstruktoren  ****************
	
	// Aufgerufen von: Session->New
	public Session(String _name) {
	    name = new String(_name);
	    workProgram = new absynt.Program(null, null);
	}
	

    }
}







