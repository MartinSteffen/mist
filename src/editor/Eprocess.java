package editor;

import absynt.*;
import editor.einterface.*;

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
  public String name;
  public Eprogram program;

  public Eprocess (Eprogram rootprogram, String inname) {
    ainterface = new AbsyntInterface();
    next = null;
    last = null;
    statelist = null;
    translist = null;
    process = ainterface.makeNewProcess();
    proclist = new absynt.ProcessList(process, null);
    grid = false;
    name = inname;
    program = rootprogram;
  }
  
  public Eprocess (Eprogram rootprogram, String inname, absynt.Process inprocess, absynt.ProcessList inproclist) {
    ainterface = new AbsyntInterface();
    next = null;
    last = null;
    process = inprocess;
    proclist = inproclist;
    grid = false;
    name = inname;
    program = rootprogram;
    if (proclist.next != null) next = new Eprocess(program, name+"?", proclist.next.head, proclist.next);
  }


  absynt.ProcessList getList() {
    return(proclist);
  }

  void setNext(Eprocess inprocess) {
    if (inprocess != null) proclist.next = inprocess.getList();
     else proclist.next = null;
  }

  void updateTransitions() {
    if (translist != null) translist.updateTransition();
  }

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
    if (name.compareTo(inname) == 0) wert = true;
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

}
