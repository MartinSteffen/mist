package editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import editor.undo.*;
import absynt.*;
import editor.einterface.*;
import java.beans.PropertyVetoException;
import javax.swing.plaf.basic.*;
import gui.*;
import utils.*;

public class Editor extends JFrame implements InternalFrameListener, ActionListener, WindowListener {

    public static int editor_idcount = 0;	// Zaehler fuer Editor-Aufrufe
    public static int editor_count = 0;		// Zaehler fuer aktuelle Instanzen von class Editor
    int editor_id;				// id-Nummer dieses Editors
    Container cpane;				// ContentPane des Editors
    JDesktopPane dpane;
    JLabel infotext;				// InfoLabel untem im Window
    Zeichenflaeche zflaeche;			// Zeichenflaeche
    EditorToolbar editbar;			// Toolbar zur Linken
    TopMenuToolbar topmenubar;			// Buttenleiste oben im Window
    TopMenuBar topmenu;
    Font buttonfont;
    Font menufont;
    Font zeichenfont;
    Font infofont;
    Font iframefont;
    DebugWindow debugwindow;			// Debugwindow
    boolean debug;
    boolean grid;
    String toolcommand;
    String editorname = "Editor";
    ProcessWindow activewindow;
    Eprogram programlist;
    Eprogram activeprogram;
    Eprocess activeprocess;
//    gui.EdInterface edinterface;
    EditorSelection globalselection;
    int zoom;
    PrettyPrint prettyprint;


    /**
     * Konstruktoren
     *
     */
    public Editor (String name, absynt.Program program, int set_x, int set_y, int set_width, int set_height) {
      super("");
      setSize(set_width, set_height);
      setLocation(set_x, set_y);
      setTitle(editorname);

      programlist = null;
      activeprogram = null;
      activeprocess = null;
      globalselection = null;
      zoom = 100;
      prettyprint = new PrettyPrint();

      /* Initialisierung der Variablen   */

      editor_idcount++;			// Aufrufzaehler
      editor_count++;			// Anzahl laufender Editorfenster
      editor_id = editor_idcount;	// Nummer fuer diesen Editor

      addProgram(name, program);

      setEditorFonts();
      debug = false;
      grid = true;
      toolcommand = "select";

      debugwindow = null;
      cpane = getContentPane();
      dpane = new JDesktopPane();
      dpane.putClientProperty("JDesktopPane.dragMode","outline");
      dpane.setFont(iframefont);
      topmenu = new TopMenuBar(this);

      setJMenuBar(topmenu);
      topmenubar = new TopMenuToolbar(this);
      topmenubar.setAlignmentX(LEFT_ALIGNMENT);
      topmenubar.setAlignmentY(TOP_ALIGNMENT);

      editbar = new EditorToolbar(this);
      editbar.setAlignmentX(LEFT_ALIGNMENT);
      editbar.setAlignmentY(TOP_ALIGNMENT);

//      JScrollPane scrollpane = new JScrollPane(zflaeche);
      infotext = new JLabel("infotext :");
      infotext.setFont(infofont);

      JToolBar northtoolbar = new JToolBar(1);
      northtoolbar.setFloatable(false);
      northtoolbar.add(topmenubar);
      northtoolbar.add(editbar);

      JToolBar westtoolbar = new JToolBar(0);
      westtoolbar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
      westtoolbar.setFloatable(false);
//      westtoolbar.add(topmenubar);
//      westtoolbar.add(editbar);
      
      /* Frameinhalt: */

      cpane.add(northtoolbar, BorderLayout.NORTH);
      cpane.add(westtoolbar, BorderLayout.WEST);
      cpane.add(dpane, BorderLayout.CENTER);
      cpane.add(infotext, BorderLayout.SOUTH);

      addWindowListener(this);		// WindowListener hinzufuegen
//      pack();
      setVisible(true);			// Frame sichtbar machen
    }

    public Editor (absynt.Program program) {
      this("unnamed", program, 0, 0, 300, 300);
    }

/*
    public Editor (gui.EdInterface inedinterface) {
      this(inedinterface.name, null, inedinterface.x, inedinterface.x ,inedinterface.width, inedinterface.height);
      edinterface = inedinterface;
    }
*/

    public void internalFrameClosed(InternalFrameEvent event) {
      if (debug) debugText("closed : "+event);
      if (activewindow == event.getSource()) {
        if (debug) debugText("closing active window !");
        activewindow = null;
      }
    }
    public void internalFrameActivated(InternalFrameEvent event) {
      if (debug) debugText("activated : "+event);
      activewindow = (ProcessWindow)event.getSource();
    }
    public void internalFrameDeactivated(InternalFrameEvent event) {}
    public void internalFrameOpened(InternalFrameEvent event) {
      if (debug) debugText("opened : "+event.getSource());
    }
    public void internalFrameIconified(InternalFrameEvent event) {}
    public void internalFrameDeiconified(InternalFrameEvent event) {}
    public void internalFrameClosing(InternalFrameEvent event) {}

    public void windowClosed(WindowEvent event) {
      if (debug) debugText("closed : "+event);
      if (activewindow == event.getSource()) {
        if (debug) debugText("closing active window !");
        activewindow = null;
      }
    }
    public void windowDeiconified(WindowEvent event) {}
    public void windowIconified(WindowEvent event) {}
    public void windowActivated(WindowEvent event) {}
    public void windowDeactivated(WindowEvent event) {}
    public void windowOpened(WindowEvent event) {}

    public void windowClosing(WindowEvent event) {
      exitEditor();
    }

    void setActiveWindow (ProcessWindow processwindow) {
      activewindow = processwindow;
    }

    String checkProgramName (String inname) {
      if (debug) debugText("checkProgramName : inname = "+inname);
      String outname;
      String tempname;
      if (inname.length() < 1) tempname = "unnamed"; else tempname = inname;
      outname = tempname;
      if (programlist != null) {
        int count = 0;
        while (programlist.checkProgramTitle(outname)) {
          count++;
          outname = tempname+" ["+Integer.toString(count)+"]";
          if (debug) debugText("checkProgramName : outname = "+outname);
        }
      }
      return(outname);
    }

    String checkProcessName (String inname) {
      if (debug) debugText("checkProcessName : inname = "+inname);
      String outname;
      String tempname;
      if (inname.length() < 1) tempname = "unnamed"; else tempname = inname;
      outname = tempname;
      int count = 0;
      while (activeprogram.checkProcessTitle(outname)) {
        count++;
        outname = tempname+" ["+Integer.toString(count)+"]";
        if (debug) debugText("checkProcessName : outname = "+outname);
      }
      return(outname);
    }

    String checkStateName (Eprocess eprocess, String inname) {
      if (debug) debugText("checkStateName : inname = "+inname);
      String outname;
      String tempname;
      if (inname.length() < 1) tempname = "unnamed"; else tempname = inname;
      outname = tempname;
      int count = 0;
      while (eprocess.checkStateName(outname)) {
        count++;
        outname = tempname+" ["+Integer.toString(count)+"]";
        if (debug) debugText("checkStateName : outname = "+outname);
      }
      return(outname);
    }

    void setEditorFonts () {
      buttonfont = new Font("Helvetica", Font.PLAIN, 12);
      menufont = new Font("Helvetica", Font.PLAIN, 12);
      zeichenfont = new Font("Helvetica", Font.BOLD, 14);
      infofont = new Font("Currier", Font.PLAIN, 12);
      iframefont = new Font("Helvetica", Font.PLAIN, 17);
    }

    public void actionPerformed(ActionEvent event) {}

    void execute (String command) {
      if (debug) debugText("execute : "+command);
      if (command.compareTo("grid") == 0) toggleGrid();
      else if (command.compareTo("close") == 0) exitEditor();
      else if (command.compareTo("addProcess") == 0) newProcessWindow(null);
      else if (command.compareTo("clear") == 0) clear();
/*      else if (command.compareTo("undo") == 0) undo();
        else if (command.compareTo("redo") == 0) redo(); */
      else if (command.compareTo("undo") == 0) {
        zoom = zoom +100;
      	activewindow.setZoom(zoom);
      }
      else if (command.compareTo("redo") == 0) {
        if (zoom > 100) zoom = zoom - 100;
        if (zoom < 100) zoom = 100;
        activewindow.setZoom(zoom);
      }
    }

    void addProgram (String inname, absynt.Program inprogram) {
      if (debug) debugText("addProgram : inname = "+inname);
      String outname = "";
      outname = checkProgramName(inname);
      if (programlist == null) {
        programlist = new Eprogram(outname, inprogram);
        activeprogram = programlist;
      } else {
        activeprogram = programlist.appendProgram(outname, inprogram);
      }
      setTitle(editorname+" - "+outname);
    }

    void addProcess (absynt.Process inproccess) {
    }

    void newProgram () {
      if (debug) debugText("newProgram");
      addProgram("unnamed", null);
    }

    void exitEditor () {
      closeEditor();
      if (debug) debugText("exitEditor : Nr."+Integer.toString(editor_id));
      if (editor_count == 0) System.exit(0);
    }

    void closeEditor () {
      this.dispose();
      editor_count--;
    }

    void clearSelection() {
      if (globalselection != null) globalselection.clear();
    }
    
    void setSelection(EditorSelection inselection) {
      clearSelection();
      globalselection = inselection;
    }
    
    EditorSelection copySelection() {
      EditorSelection outselection = null;
      if (globalselection != null) outselection = globalselection.copy();
      return (outselection);
    }

/*
    void newProcessWindow (Eprocess inprocess) {
      if (debug) debugText("newProcessWindow");
      if (inprocess == null) {
        inprocess = activeprogram.newProcess(checkProcessName("unnamed"));
      }
      ProcessWindow pwin = new ProcessWindow(this, inprocess);
    }
*/

    void newProcessWindow (Eprocess inprocess) {
      if (debug) debugText("newProcessWindow");
      if (inprocess == null) {
        inprocess = activeprogram.newProcess(checkProcessName("unnamed"));
      }
      ProcessWindow pwin = new ProcessWindow(this, inprocess);
      pwin.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      pwin.addInternalFrameListener(this);
      dpane.add(pwin);
      pwin.fitIn();
      pwin.moveToFront();
    }

    void clear() {};
    void undo() {};
    void redo() {};

    public void editCopy () {
      if (activewindow != null) setSelection(activewindow.copySelection());
      System.out.println("GlobalSelection :");
      globalselection.print();
    }
    
    public void editPaste () {
      if (activewindow != null) activewindow.setSelection(copySelection());
      if (activewindow != null && activewindow.processselection != null) activewindow.processselection.paste(activewindow.eprocess);
      activewindow.refreshDisplay();
    }
    
    public void editCut () {
      clearSelection();
      if (activewindow != null && activewindow.processselection != null) {
        setSelection(activewindow.copySelection());
        activewindow.processselection.cut(activewindow.eprocess);
        activewindow.refreshDisplay();
      }
    }

    void toggleGrid() {
      if (activewindow != null) {
        activewindow.toggleGrid();
      }
    }

    void debugText (String text) {
      if (debugwindow != null) debugwindow.appendText(text);
    }

    void setInfotext (String text) {
      infotext.setText(text);
    }

    void prettyPrint() {
      if (activeprogram != null) {
        prettyprint.print(activeprogram.getProgram());
      }
    }
    
    /**
     * Methode zum schliessen des Editors ueber die GUI
     */   
    public void destroy() {
      exitEditor();
    }
    
    public void highlightState(absynt.Astate state, absynt.Process process, absynt.Program program) {
	//      zeichenflaeche.highlightState(state, process, program);
    }
    public void unhighlightState(absynt.Astate state, absynt.Process process, absynt.Program program) {
	//      zeichenflaeche.unhighlightState(state, process, program);
    }
    public void highlightTransition(absynt.Transition transition, absynt.Process process, absynt.Program program) {
	//      zeichenflaeche.highlightState(transition, process, program);
    }
    public void unhighlightTransition(absynt.Transition transition, absynt.Process process, absynt.Program program) {
	//      zeichenflaeche.highlightState(transition, process, program);
    }

    public static void main (String[] argv) {
      Editor prog = new Editor("program 1",null, 0, 0, 300, 300);
    }

}
