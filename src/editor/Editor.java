package editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
// import editor.undo.*;
import absynt.*;
// import editor.einterface.*;
import java.beans.PropertyVetoException;
import javax.swing.plaf.basic.*;
import gui.*;

/**
 * class Editor zum Bearbeiten eines ubergebenen Projects
 * bis jetzt nur roh
 * @author Alexander Eckloff, Finn Jacobs.
 * @version  $Id: Editor.java,v 1.3 2000-06-16 09:51:59 unix01 Exp $
 */

public class Editor extends javax.swing.JFrame {

    public static int editor_idcount = 0;       // Zaehler fuer Editor-Aufrufe
    public static int editor_count = 0;         // Zaehler fuer aktuelle Instanzen von class Editor
    int editor_id;                              // id-Nummer dieses Editors
//    DebugWindow debugwindow;                    // Debugwindow
    boolean debug;

    EdInterface edinterface;
    String editorname = "Editor";
    
    /**
     * Konstruktoren
     *
     */

    public Editor (String name, absynt.Program program, int set_x, int set_y, int set_width, int set_height) {
      super("");
      setSize(set_width, set_height);
      setLocation(set_x, set_y);
      setTitle(editorname);
      edinterface = null;
      setVisible(true);
      show();
    }

    public Editor(absynt.Program program) {
      this("unnamed", program, 0, 0, 500, 500);
    }

    public Editor(EdInterface inedinterface) {
      this(inedinterface.name, null, inedinterface.x, inedinterface.x ,inedinterface.width, inedinterface.height);
      edinterface = inedinterface;
    }

    /**
     * Editor schliessen
     */
    void exitEditor () {
      closeEditor();
      if (editor_count == 0) System.exit(0);
    }

    /**
     * Editorfenster schliessen
     */
    void closeEditor () {
      this.dispose();
      editor_count--;
    }

    /**
     * Methode zum schliessen des Editors ueber die GUI
     */   
    public void destroy() {
      if (edinterface != null) {
	edinterface.x = this.getX();
	edinterface.y = this.getY();
	edinterface.width = this.getWidth();
	edinterface.height = this.getHeight();
        exitEditor();
      }
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
}
