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

public class ProcessWindow extends JInternalFrame {

    Editor editor;
    Zeichenflaeche zeichenflaeche;
    Eprocess eprocess;
    Container cpane;
    EditorSelection processselection;
    ScrollPane scrollpane;
    Dimension size;
    int zoom;

    public ProcessWindow (Editor editroot, Eprocess inprocess) {
      super("", true, true, true, true);
      editor = editroot;
      eprocess = inprocess;
      if (eprocess != null) {
        eprocess.setProcessWindow(this);
      }
      processselection = null;
      scrollpane = new ScrollPane();
      zeichenflaeche = new Zeichenflaeche(this);
      scrollpane.add(zeichenflaeche);
      setTitle(eprocess.getName());
      setSize(new Dimension(300, 300));
      setLocation(10 ,10);
//      addWindowListener(this);
      cpane = getContentPane();
      cpane.add(scrollpane, BorderLayout.CENTER);
      size = getSize();
      moveToFront();
    }

//    public void windowClosed(WindowEvent event) {}
//    public void windowDeiconified(WindowEvent event) {}
//    public void windowIconified(WindowEvent event) {}
//    public void windowActivated(WindowEvent event) {
//      editor.setActiveWindow(this);
//    }
//    public void windowDeactivated(WindowEvent event) {}
//    public void windowOpened(WindowEvent event) {}
//    public void windowClosing(WindowEvent event) {
//    }

    void removeProcess() {
      if (eprocess != null) {
        String id = eprocess.getName();
      	eprocess.removeProcess();
        editor.guiRemoveProcess(id);
      }
    }

    void closeWindow() {
      eprocess = null;
      try {
      	this.setClosed(true);
      } catch (PropertyVetoException e) {
        System.out.println(e.getMessage());
      }
      this.dispose();
    }

    Eprocess getEprocess() {
      return(eprocess);
    }

    public boolean isGrid() {
      return(eprocess.grid);
    }

    public void toggleGrid() {
      eprocess.toggleGrid();
      zeichenflaeche.refresh();
    }
    
    void refreshDisplay() {
      zeichenflaeche.paint(zeichenflaeche.getGraphics());
    }

    void clearSelection () {
      if (processselection != null) processselection = processselection.clear();
    }

    EditorSelection copySelection() {
      EditorSelection outselection = null;
      if (processselection != null) outselection = processselection.copy();
      return (outselection);
    }
    
    void setSelection(EditorSelection inselection) {
      clearSelection();
      processselection = inselection;
    }
    
    void setZoom(int zoom_percent) {
      if (zoom != zoom_percent) {
        zoom = zoom_percent;
        size = getSize();
        zeichenflaeche.setSize(new Dimension(((zoom * size.width)/100), ((zoom * size.height)/100)));
        refreshDisplay();
        editor.setInfotext("zoom ("+Integer.toString(zoom)+"%) to ("+Integer.toString(((zoom * size.width)/100))+", "+Integer.toString(((zoom * size.height)/100))+")");
      }
    }

    void checkActivation() {
      if (!isSelected()) {
        winActivation();
      }
    }

    void winActivation() {
      try {
        moveToFront();
      	setSelected(true);
      } catch (PropertyVetoException e) {
        System.out.println(e.getMessage());
      }    	
    }
    
    void fitIn() {
      try {
      	setMaximum(true);
      	setSelected(true);
      } catch (PropertyVetoException e) {
        System.out.println(e.getMessage());
      }
    }
    
    void makeIconified() {
      System.out.println("(ProcessWindow) makeIconified()");
      try {
        if (!isIcon()) {
          setIcon(true);
        }
      } catch (PropertyVetoException e) {
        System.out.println(e.getMessage());
      }
    }

    void makeUnIconified() {
      System.out.println("(ProcessWindow) makeUnIconified()");
      try {
        if (isIcon()) {
          setIcon(false);
          moveToFront();
        }
      } catch (PropertyVetoException e) {
        System.out.println(e.getMessage());
      }
    }
}