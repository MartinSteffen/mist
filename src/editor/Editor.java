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
    gui.GUI gui;
    ChannelEditor channeleditor;
    VariablenEditor variableneditor;

    /**
     * Konstruktoren
     *
     */
    public Editor (gui.GUI rootgui, absynt.Program program, int set_x, int set_y, int set_width, int set_height) {
      super("");
      setSize(set_width, set_height);
      setLocation(set_x, set_y);
      setTitle(editorname);

      programlist = null;
      activeprogram = null;
      activeprocess = null;
      globalselection = null;
      channeleditor = null;
      variableneditor = null;
      gui = rootgui;
      zoom = 100;
      prettyprint = new PrettyPrint();

      /* Initialisierung der Variablen   */

      editor_idcount++;			// Aufrufzaehler
      editor_count++;			// Anzahl laufender Editorfenster
      editor_id = editor_idcount;	// Nummer fuer diesen Editor

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
      addProgram(program);
    }

    public Editor (gui.GUI rootgui, absynt.Program program) {
      this(rootgui, program, 0, 0, 400, 400);
    }

    public Editor (absynt.Program program) {
      this(null, program, 0, 0, 400, 400);
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
      ((ProcessWindow)event.getSource()).removeProcess();
    }
    public void internalFrameActivated(InternalFrameEvent event) {
      if (debug) debugText("activated : "+event);
      activewindow = (ProcessWindow)event.getSource();
      activeprocess = activewindow.getEprocess();
      refreshVariablenEditor();
    }
    public void internalFrameDeactivated(InternalFrameEvent event) {}
    public void internalFrameOpened(InternalFrameEvent event) {
      if (debug) debugText("opened : "+event.getSource());
    }
    public void internalFrameIconified(InternalFrameEvent event) {
      guiCloseProcess(((ProcessWindow)event.getSource()).eprocess.getName());
    }
    public void internalFrameDeiconified(InternalFrameEvent event) {
      guiOpenProcess(((ProcessWindow)event.getSource()).eprocess.getName());
    }
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
      activeprocess = processwindow.getEprocess();
    }

    void refreshVariablenEditor() {
      if (variableneditor != null) {
      	if (activeprocess != null) variableneditor.refresh(activeprocess.getProcess());
        else variableneditor.refresh(null);
      } else {
//      	if (activeprocess != null) variableneditor = new VariablenEditor(this, activeprocess.getProcess());
//      	else variableneditor = new VariablenEditor(this, null);
      }
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

    void addProgram (absynt.Program inprogram) {
//      if (debug) debugText("addProgram : inname = "+inname);
//      String outname = "";
//      outname = checkProgramName(inname);
      String progname = "";
      boolean nameset = false;
      if (inprogram == null) {
      	nameset = true;
      	progname = checkProgramName("unnamed");
      }
      if (programlist == null) {
        programlist = new Eprogram(inprogram);
        activeprogram = programlist;
      } else {
        activeprogram = programlist.appendProgram(inprogram);
      }
      if (nameset) activeprogram.setName(progname);
//      setTitle(editorname+" - "+outname);
      setTitle(editorname+" - "+activeprogram.getName());
      activeprogram.createProcessWindows(this, dpane);
    }

    void addProcess (absynt.Process inproccess) {
    }

    void newProgram () {
      if (debug) debugText("newProgram");
      addProgram(null);
    }

    void exitEditor () {
      closeEditor();
      if (debug) debugText("exitEditor : Nr."+Integer.toString(editor_id));
      if (editor_count == 0 && gui == null) System.exit(0);
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

    void calcPosition1 (String method) {
      if (activeprocess != null) {
        position1.Position position = null;
        if (method.compareTo("Grav") == 0) position = new position1.PositionGrav();

        if (position != null) {
          System.out.println("calcPosition1 : "+method);
          position.positioniere(activeprocess.getProcess());
        }
        activewindow.refreshDisplay();
      } else System.out.println("no active Process !!");
    }

    void calcPosition2 (String method) {
     
      if (activeprocess != null) {
        position2.Position position = null;
        if (method.compareTo("Grav") == 0) {
          System.out.println("calcPosition2 : "+method);
          position2.Positionierung position2grav = new position2.PositionGrav();
          position2grav.positioniere(activeprocess.getProcess());
//          position = new position2.PositionGrav();
        }
        else if (method.compareTo("FR") == 0) position = new position2.PositionFR();
        else if (method.compareTo("Zufall") == 0) position = new position2.PositionZufall();

        if (position != null) {
          System.out.println("calcPosition2 : "+method);
          position.positioniere(activeprocess.getProcess());
        }
        activewindow.refreshDisplay();
      } else System.out.println("no active Process !!");
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
   
    void cleanDesktop () {
      JInternalFrame[] framelist = dpane.getAllFrames();
      if (framelist.length > 0) {
        for (int i=0; i < framelist.length; i++) {
          ((ProcessWindow)framelist[i]).eprocess = null;
          ((ProcessWindow)framelist[i]).closeWindow();
        }
      }
    }

    void removeActiveProgram() {
      if (activeprogram != null) {
        cleanDesktop();
        if (programlist == activeprogram) {
          programlist = activeprogram.next;
          activeprogram = programlist;
          if (programlist != null) programlist.last = null;
        } else {
          activeprogram.last.next = activeprogram.next;
          if (activeprogram.next != null) activeprogram.next.last = activeprogram.last;
          Eprogram newactiveprogram = activeprogram.last;
          activeprogram.next = null;
          activeprogram.last = null;
          activeprogram = newactiveprogram;
          newactiveprogram = null;
        }
      }
    }

    public String [] getProcessIds() {
      String[] outarray;
      if (activeprogram != null) outarray = activeprogram.getProcessNames();
      else outarray = new String[0];
      return(outarray);
    }
    
    // Prozess dauerhaft hinzufuegen und seine id zurueckgeben
    public String NewProcess() {
      String outname = "";
      outname = newProcessWindow(null);
      return(outname);
    }
     
    // Prozess dauerhaft entfernen
    public void RemoveProcess(String id) {
      System.out.println("GUI uses RemoveProcess");
      if (activeprogram != null) {
        Eprocess workprocess = activeprogram.getProcessByName(id);
        if (workprocess != null) workprocess.removeProcess();
        else System.out.println("no workprocess returned !!");
      }
    }
             
    // Das Fenster, von dem Prozess mit der entspr. id, sichtbar machen
    public void OpenProcess(String id) {
      System.out.println("GUI uses OpenProcess");
      Eprocess workprocess = null;
      if (activeprogram != null) {
        workprocess = activeprogram.getProcessByName(id);
        if (workprocess != null) workprocess.makeUnIconified();
        else System.out.println("no workprocess returned !!");
      }
    }

    // Das Fenster, in dem der Prozess zu sehen ist, verstecken
    public void CloseProcess(String id) {
      System.out.println("GUI uses CloseProcess");
      Eprocess workprocess = null;
      if (activeprogram != null) {
        workprocess = activeprogram.getProcessByName(id);
        if (workprocess != null) workprocess.makeIconified();
        else System.out.println("no workprocess returned !!");
      }	
    }
    
    
    // Das Programm wurde in der GUI ausgetauscht => neues Programm darstellen (und altes wegnehmen)
    public void refresh(absynt.Program inprogram) {
      addProgram(inprogram);
    }

    // Das Programm wurde in der GUI geschlossen, der Editor soll alle Fenster schliessen,
    // bloss sich selbst nicht, und auf ein 'refresh(absynt.Program p)' warten
    public void refresh() {
      removeActiveProgram();
    }
    
    void guiAddProcess(String id) {
      if (gui != null) gui.NewProcess(id);
    }

    void guiRemoveProcess(String id) {
      if (gui != null) gui.RemoveProcess(id);
    }
    
    void guiOpenProcess(String id) {
      if (gui != null) gui.OpenProcess(id);
    }
    
    void guiCloseProcess(String id) {
      if (gui != null) gui.CloseProcess(id);
    }
    
// *** Die haetten wir noch gerne    
    // ***
    


    String newProcessWindow (Eprocess inprocess) {
      if (debug) debugText("newProcessWindow");
      String outname = "";
      if (inprocess == null) {
        outname = checkProcessName("unnamed");
        inprocess = activeprogram.newProcess(outname);
      } else outname = inprocess.getName();
      ProcessWindow pwin = new ProcessWindow(this, inprocess);
      pwin.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      pwin.addInternalFrameListener(this);
      dpane.add(pwin);
      pwin.fitIn();
      pwin.moveToFront();
      return(outname);
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

    void highlightState(absynt.Astate instate, absynt.Process inprocess, absynt.Program inprogram, boolean mode) {
      if (programlist != null) {
        Eprogram useprogram = programlist.getEprogramWithProgram(inprogram);
        if (useprogram != null) {
          Eprocess useprocess = useprogram.getEprocessWithProcess(inprocess);
          if (useprocess != null) {
            Estate usestate = useprocess.getEstateWithAstate(instate);
            if (usestate != null) {
              usestate.setHighlighted(mode);
              if (useprocess.getProcessWindow() != null) useprocess.getProcessWindow().refreshDisplay();
            } else System.out.println("Error !! (highlightState) no Estate found");
          } else System.out.println("Error !! (highlightState) no Eprocess found");
        } else System.out.println("Error !! (highlightState) no Eprogram found");
      } else System.out.println("Error !! (highlightState) no Eprogram in editor");
    }

    void highlightTransition(absynt.Transition intransition, absynt.Process inprocess, absynt.Program inprogram, boolean mode) {
      if (programlist != null) {
        Eprogram useprogram = programlist.getEprogramWithProgram(inprogram);
        if (useprogram != null) {
          Eprocess useprocess = useprogram.getEprocessWithProcess(inprocess);
          if (useprocess != null) {
            Etransition usetransition = useprocess.getEtransitionWithTransition(intransition);
            if (usetransition != null) {
              usetransition.setHighlighted(mode);              
              if (useprocess.getProcessWindow() != null) useprocess.getProcessWindow().refreshDisplay();
            } else System.out.println("Error !! (highlightTransition) no Etransition found");
          } else System.out.println("Error !! (highlightTransition) no Eprocess found");
        } else System.out.println("Error !! (highlightTransition) no Eprogram found");
      } else System.out.println("Error !! (highlightTransition) no Eprogram in editor");
    }
   
    public void highlightState(absynt.Astate state, absynt.Process process, absynt.Program program) {
      highlightState(state, process, program, true);
	//      zeichenflaeche.highlightState(state, process, program);
    }
    public void unhighlightState(absynt.Astate state, absynt.Process process, absynt.Program program) {
      highlightState(state, process, program, false);
	//      zeichenflaeche.unhighlightState(state, process, program);
    }
    public void highlightTransition(absynt.Transition transition, absynt.Process process, absynt.Program program) {
      highlightTransition(transition, process, program, true);
	//      zeichenflaeche.highlightTransition(transition, process, program);
    }
    public void unhighlightTransition(absynt.Transition transition, absynt.Process process, absynt.Program program) {
      highlightTransition(transition, process, program, false);
	//      zeichenflaeche.unhighlightTransition(transition, process, program);
    }

    public static void main (String[] argv) {
      Editor prog = new Editor(null, null, 0, 0, 300, 300);
    }

}
