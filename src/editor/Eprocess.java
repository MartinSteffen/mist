package editor;

import absynt.*;
import editor.einterface.*;
import javax.swing.*;

/**
 * Diese Klasse dient dem Editor als Kapsel fuer einen Process der abstrakten Syntax
 *
 */

public class Eprocess {

  public AbsyntInterface ainterface;
  public Eprocess next;
  public Eprocess last;
  public Estate statelist;
  public Etransition translist;
  public absynt.Process process;
  public absynt.ProcessList proclist;
  public boolean grid;
  public Eprogram program;
  public ProcessWindow processwindow;

/**
 * erzeugt einen neuen Process mit dem uebergeben Namen.
 */
  public Eprocess (Eprogram rootprogram, String inname) {
    ainterface = new AbsyntInterface();
    next = null;
    last = null;
    statelist = null;
    translist = null;
    processwindow = null;
    process = ainterface.makeNewProcess();
    proclist = new absynt.ProcessList(process, null);
    grid = false;
    setName(inname);
    program = rootprogram;
  }

/**
 * Wrappt einen Eprocess um den uebergebenen absynt.Process
 */  
  public Eprocess (Eprogram rootprogram, absynt.Process inprocess, absynt.ProcessList inproclist) {
    ainterface = new AbsyntInterface();
    next = null;
    last = null;
    processwindow = null;
    process = inprocess;
    proclist = inproclist;
    grid = false;
    program = rootprogram;
    // States wrappen
    if (process.states != null) {
      statelist = new Estate(null, process.states.head, process.states);
    }
    // Transitionen wrappen
    if (process.steps != null) {
      translist = new Etransition(null, process.steps.head, process.steps);
      // sourceEstates und TargetEstates ermitteln und anpassen
      Etransition esucher = translist;
      while (esucher != null) {
      	System.out.println("Reconnecting Estates with Etransitions ...");
      	esucher.sourcestate = getEstateWithAstate(esucher.transition.source);
      	esucher.targetstate = getEstateWithAstate(esucher.transition.target);
        esucher = esucher.next;
      }
    }

    if (proclist.next != null) next = new Eprocess(program, proclist.next.head, proclist.next);
  }

/**
 * Setzt das zum Eprocess zugehoerige ProcessWindow auf
 * den uebergebenen Wert
 */
  void setProcessWindow(ProcessWindow inprocesswindow) {
    processwindow = inprocesswindow;
  }

/**
 * Liefert das zugehoerige ProcessWindow zurueck.
 */  
  ProcessWindow getProcessWindow() {
    return(processwindow);
  }

/**
 * schliesst das zu dem Eprocess zugehoerige Fenster.
 */  
  void removeProcessWindow() {
    if (processwindow != null) {
      processwindow.eprocess = null;
      processwindow.closeWindow();
      processwindow = null;
    }
  }

/**
 * Erzeugt ein ProcessWindow fuer den Eprocess.
 */  
  void createProcessWindow(Editor editroot, JDesktopPane dpane) {
    System.out.println("(Eprocess.createProcessWindow()) ...");
    if (processwindow == null) {
      System.out.println("processwindow is null");
      processwindow = new ProcessWindow(editroot, this);
      processwindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      processwindow.addInternalFrameListener(editroot);
      System.out.println("adding window to dpane");
      dpane.add(processwindow);
      editroot.addMenuProcessItem(processwindow.getTitle());
      System.out.println("moving window to front");
      processwindow.moveToFront();
      System.out.println("... ready");
    }
  }

/**
 * Iconifiziert das zugehoerige ProcessWindow, falls vorhanden
 */
  void makeIconified() {
    System.out.println("(Eprocess) makeIconified()");
    if (processwindow != null) processwindow.makeIconified();
    else System.out.println("no processwindow !!");
  }

/**
 * Deiconifiziert das zugehoerige ProcessWindow, falls vorhanden
 */
  void makeUnIconified() {
    System.out.println("(Eprocess) makeIconified()");
    if (processwindow != null) processwindow.makeUnIconified();
    else System.out.println("no processwindow !!");
  }

/**
 * Gibt die Namen der Processe als Stringliste zurueck.
 */
  String[] getProcessNames() {
    System.out.println("(Eproces.getProcessNames) starting ...");
    String[] outarray;
    if (next != null) {
      System.out.println("next NOT null");
      String[] restarray = next.getProcessNames();
      outarray = new String[restarray.length+1];
      for (int i = 0; i < restarray.length; i++) outarray[i] = restarray[i];
      outarray[restarray.length] = getName();
    } else {
      System.out.println("next IS null");
      outarray = new String[1];
      System.out.println("array was initialized");
      outarray[0] = getName();
    }
    System.out.println("... ready (Eproces.getProcessNames)");
    return(outarray);
  }

/**
 * liefert den gekapselten absynt.Process zurueck;
 */
  absynt.Process getProcess() {
    return(process);
  }

/**
 * entfernt den Process aus dem Program
 */
  void removeProcess() {
    if (program != null) {
      removeProcessWindow();
      program.removeProcess(this);
    } else System.out.println("Error !! (Eprocess.removeProcess) eprogram == null");
  }

/**
 * liefert den Namen des Process zurueck
 */
  String getName() {
    System.out.println("(Eproces.getName()) starting ...");
    String outname = "";
    if (process != null) outname = process.name;
    else System.out.println("Error !!!! (Eprocess.getName) no absynt.Process in Eprocess");
    System.out.println("... ready (Eproces.getName())");
    return(outname);
  }

/**
 * Benenmnt den Process mit dem uebergebenen String
 */
  void setName(String inname) {
    if (process != null) process.name = inname;
    else System.out.println("Error !!!! (Eprocess.setName) no absynt.Process in Eprocess");
  }

/**
 * Liefert die absynt.ProcessList des Process zurueck.
 */
  absynt.ProcessList getList() {
    return(proclist);
  }

/**
 * setzt den uebergebenen Eprocess als Nachfolger fest.
 */
  void setNext(Eprocess inprocess) {
    if (inprocess != null) proclist.next = inprocess.getList();
     else proclist.next = null;
  }

/**
 * bringt die Start- und Endzustaende aller Transitionen
 * des Process auf den aktuellen Stand.
 */
  void updateTransitions() {
    if (translist != null) translist.updateTransition();
  }

/**
 * Prueft, ob der uebergebene Estate in dem Eprocess enthalten ist
 */
  boolean stateIsInProcess(Estate instate) {
    boolean wert = false;
    Estate sucher = statelist;
    while (sucher != null && sucher != instate) sucher = sucher.next;
    if (sucher != null) wert = true;
    return(wert);
  }

/**
 * liefert den letzten Zustand zurueck, der sich in der uebergebenen
 * Umgebung befindet. Ist kein Zustand in dieser Umgebung, so ist
 * der Rueckgabewert "null"
 */
  public Estate getStateInRange(float x, float y, float range) {
    Estate outstate = null;
    if (statelist != null) outstate = statelist.getStateInRange(x, y, range);
    return(outstate);
  }

  public Etransition getTransitionInRange(float x, float y, float range) {
    Etransition outtransition = null;
    if (translist != null) outtransition = translist.getTransitionInRange(x, y, range);
    return(outtransition);
  }

/**
 * Schaltet das Raster an oder aus
 */
  public void toggleGrid () {
    if (grid) grid = false; else grid = true;
  }

/**
 * Ueberprueft, ob der uebergebene Name mit dem Prozessnamen uebereinstimmt
 * und liefert das Ergebnis dieser Ueberpruefung als boolean zurueck
 */
  public boolean checkProcessTitle (String inname) {
    boolean wert = false;
    if (getName().compareTo(inname) == 0) wert = true;
    else {
      if (next != null) wert = next.checkProcessTitle(inname); 
    }
    return(wert);
  }

/**
 * Fuegt einen Zustand in den Prozess ein
 */
  public void addState (Estate state) {
/*
    state.next = statelist;
    state.last = null;
    if (statelist != null) statelist.last = state;
    state.setNext(statelist);
    statelist = state;
 */
    if (statelist == null) {
      statelist = state;
      state.last = null;
      state.next = null;
      process.states = state.getList();
    } else {
      Estate sucher = statelist;
      while (sucher.next != null) sucher = sucher.next;
      state.last = sucher;
      sucher.next = state;
      state.next = null;
      state.last.setNext(state);
    }

  }

/**
 * Fuegt einen neuen Zustand anhand der uebergebenen Daten in den
 * Process ein
 */
  public void addState(String sname, absynt.Expr inexpr, float x_cor, float y_cor, int type) {
    Estate estate = new Estate(sname, inexpr, x_cor, y_cor, type);
    addState(estate);
  }

/**
 * Entfernt den uebergebenen Zustand aus dem Process
 */
  public void removeState (Estate instate) {
    Estate sucher = statelist;
// angliegende Transitionen loeschen;
    if (translist != null) {
      Etransition tsucher = translist;
      while (tsucher != null) {
        if (tsucher.isConnectedTo(instate)) {
          Etransition nexttsucher = tsucher.next;
          removeTransition(tsucher);
          tsucher = nexttsucher;
        } else {
          tsucher = tsucher.next;
	}
      }
    }
    while (sucher != null && sucher != instate) sucher = sucher.next;    
    if (sucher != null) {
      if (sucher == statelist) {
        if (statelist.next != null) statelist.next.last = null;
        statelist = statelist.next;
        process.states = instate.getList().next;        
      } else {
      	sucher.last.next = sucher.next;
      	if (sucher.next != null) sucher.next.last = sucher.last;
        sucher.last.setNext(sucher.next);      	
      }
      sucher.clear();
//      sucher.dispose();
      sucher = null;
    }
  }

/**
 * Fuegt die Uebergebene Transition in den Process ein
 */
  public void addTransition (Etransition transition) {
    if (translist == null) {
      translist = transition;
      transition.next = null;
      transition.last = null;
      process.steps = transition.getList();
    } else {
      Etransition sucher = translist;
      while (sucher.next != null) sucher = sucher.next;
      transition.last = sucher;
      sucher.next = transition;
      transition.next = null;
      sucher.setNext(transition);
    }
  }

/**
 * Entfernt die uebergeben Transition aus dem Process
 */ 
  void removeTransition (Etransition intransition) {
    Etransition tsucher = translist;
    while (tsucher != null && tsucher != intransition) tsucher = tsucher.next;
    if (tsucher != null) {
      if (tsucher == translist) {
      	if (translist.next != null) translist.next.last = null;
      	translist = translist.next;
      	process.steps = intransition.getList().next;
      } else {
      	tsucher.last.next = tsucher.next;
      	if (tsucher.next != null) tsucher.next.last = tsucher.last;
      	tsucher.last.setNext(tsucher.next);
      }
      tsucher.clear();
      tsucher = null;
    }
  }

/**
 * Fuegt ein EditorObject in den Prozess ein
 */
  void paste (EditorObject eobject) {
    if (eobject instanceof Estate) addState((Estate)eobject);
    else if (eobject instanceof Etransition) addTransition((Etransition)eobject);
  }

/**
 * Liefert eine EditorSelection zurueck in der alle EditorObjekte enthalten
 * sind, die sich in dem Uebergeben Rechteck befinden
 */
  EditorSelection rangeSelect (float x1, float y1, float x2, float y2) {
    float ox1, oy1, ox2, oy2;
    if (x1 <= x2) {
      ox1 = x1;
      ox2 = x2;
    } else {
      ox1 = x2;
      ox2 = x1;
    }
    if (y1 <= y2) {
      oy1 = y1;
      oy2 = y2;
    } else {
      oy1 = y2;
      oy2 = y1;
    }
    EditorSelection outselection = null;
    if (statelist != null) outselection = statelist.rangeSelect(ox1, oy1, ox2, oy2);
    if (outselection != null) {
      EditorSelection sucher = outselection;
      while (sucher.next != null) sucher = sucher.next;     
      if (translist != null) sucher.next = translist.rangeSelect(ox1, oy1, ox2, oy2);
    } else {
      if (translist != null) outselection = translist.rangeSelect(ox1, oy1, ox2, oy2);
    }
    return(outselection);
  }

/**
 * Loescht alle Transitionen und Zustaende aus dem Process
 */  
  void clear() {
    Etransition tsucher = translist;
    Etransition tsucher2;
    while (tsucher != null) {
      tsucher2 = tsucher;	
      tsucher = tsucher.next;
      tsucher2.clear();
//      tsucher2.dispose();
    }
    translist = null;
    Estate ssucher = statelist;
    Estate ssucher2;
    while (ssucher != null) {
      ssucher2 = ssucher;
      ssucher = ssucher.next;
      ssucher2.clear();
//      ssucher.dispose();
    }
  }

/**
 * Checkt, ob bereits ein Zustand mit dem uebergebenen Namen im Prozess
 * vorhanden ist.
 */
  public boolean checkStateName (String inname) {
    boolean wert = false;
    if (statelist != null) {
      wert = statelist.checkStateName(inname);
    }
    return(wert);
  }

/**
 * Liefert den Process mit dem uebergebenen Namen zurueck, falls vorhanden.
 */
  Eprocess getProcessByName(String inname) {
    Eprocess outprocess = null;
    if (getName().compareTo(inname) == 0) outprocess = this;
    else {
      if (next != null) outprocess = next.getProcessByName(inname);
    }
    return(outprocess);
  }

/**
 * liefert den Eprocess zurueck, der den uebergebenen absynt.Process kapselt
 */
  Eprocess getEprocessWithProcess(absynt.Process inprocess) {
    Eprocess outprocess = null;
    if (getProcess() == inprocess) outprocess = this;
    else {
      if (next != null) outprocess = next.getEprocessWithProcess(inprocess);
    }
    return(outprocess);
  }

/**
 * liefert den Estate zurueck, der den uebergebenen absynt.Astate kapselt
 */
  Estate getEstateWithAstate (absynt.Astate instate) {
    Estate outstate = null;
    if (statelist != null) outstate = statelist.getEstateWithAstate(instate);
    return(outstate);
  }

/**
 * liefert die Etransition zurueck, die die uebergebene absynt.Transition kapselt
 */
  Etransition getEtransitionWithTransition (absynt.Transition intransition) {
    Etransition outtransition = null;
    if (translist != null) outtransition = translist.getEtransitionWithTransition(intransition);
    return(outtransition);
  }
}
