package editor;

import absynt.*;
import editor.einterface.*;

public class Etransition extends EditorObject {

  Etransition next;
  Etransition last;
  absynt.Transition transition;
  absynt.TransitionList translist;
  Estate sourcestate;
  Estate targetstate;
//  absynt.Label lab;
  static AbsyntInterface einterface = new AbsyntInterface();
  boolean highlighted;

  public Etransition (Estate s, Estate t, absynt.Label l) {
//    lab = l;
    sourcestate = s;
    targetstate = t;
    einterface = new AbsyntInterface();
    transition = einterface.makeNewTransition(sourcestate.getAstate(), targetstate.getAstate(), l);
    translist = einterface.makeNewTransitionList();
    translist.head = transition;
    next = null;
    last = null;
    highlighted = false;
  }

/**
 * wrappt eine Etransition um eine uebergebene absynt.Transition mit Liste
 */
  public Etransition(Etransition lastetransition, absynt.Transition intransition, absynt.TransitionList intransitionlist) {
    highlighted = false;
    // Transition & List
    transition = intransition;
    translist = intransitionlist;
    // last & next
    last = lastetransition;
    next = null;
    if (intransitionlist.next != null) next = new Etransition(this, intransitionlist.next.head, intransitionlist.next);
    
  }

  boolean isConnectedTo(Estate instate) {
    boolean wert = false;
    if (sourcestate == instate || targetstate == instate) wert = true;
    System.out.println("Transition is connected to "+instate.getName());
    return(wert);
  }

  absynt.TransitionList getList() {
    return(translist);
  }

  void setNext (Etransition nexttransition) {
    if (nexttransition != null) translist.next = nexttransition.getList();
      else translist.next = null;
  }

  absynt.Transition getTransition() {
    return(transition);
  }

  void setSource(Estate instate) {
    sourcestate = instate;
    transition.source = instate.getState();
  }

  void setTarget(Estate instate) {
    targetstate = instate;
    transition.target = instate.getState();
  }
  
  Estate getSource() {
    return(sourcestate);
  }
  
  Estate getTarget() {
    return(targetstate);
  }

  void updateTransition() {
    transition.source = sourcestate.getState();
    transition.target = targetstate.getState();
    if (next != null) next.updateTransition();
  }

  absynt.Label getLabel() {
    absynt.Label outlabel = null;
    if (transition != null) {
      if (transition.lab != null) outlabel = transition.lab;
    }
    return(outlabel);
  }

/**
 * Festlegung des Typs der Transition als Input-Transition
 */  
  void setInput(absynt.Expr expr, String channel, String variable) {
    if (transition == null) {
      transition = einterface.makeNewTransition(sourcestate.getAstate(), targetstate.getAstate(), new absynt.Label(expr, new absynt.Input_action(new absynt.Channel(channel), new absynt.Variable(variable))));
    } else {
      transition.lab = null;
      transition.lab = new absynt.Label(expr, new absynt.Input_action(new absynt.Channel(channel), new absynt.Variable(variable)));
    }
  }

/**
 * Festlegung des Typs der Transition als Output-Transition
 */  
  void setOutput(absynt.Expr expr, String channel, absynt.Expr actionexpr) {
    if (transition == null) {
      transition = einterface.makeNewTransition(sourcestate.getAstate(), targetstate.getAstate(), new absynt.Label(expr, new absynt.Output_action(new absynt.Channel(channel), actionexpr)));
    } else {
      transition.lab = null;
      transition.lab = new absynt.Label(expr, new absynt.Output_action(new absynt.Channel(channel), actionexpr));
    }
  }

/**
 * Festlegung des Typs der Transition als Assign-Transition
 */  
  void setAssign(absynt.Expr expr, String variable, absynt.Expr actionexpr) {
    if (transition == null) {
      transition = einterface.makeNewTransition(sourcestate.getAstate(), targetstate.getAstate(), new absynt.Label(expr, new absynt.Assign_action(new absynt.Variable(variable), actionexpr)));
    } else {
      transition.lab = null;
      transition.lab = new absynt.Label(expr, new absynt.Assign_action(new absynt.Variable(variable), actionexpr));
    }
  }

/**
 * Festlegung des Typs der Transition als Tau-Transition
 */  
  void setTau(absynt.Expr expr) {
    if (transition == null) {
      transition = einterface.makeNewTransition(sourcestate.getAstate(), targetstate.getAstate(), new absynt.Label(expr, new absynt.Tau_action()));
    } else {
      transition.lab = null;
      transition.lab = new absynt.Label(expr, new absynt.Tau_action());
    }
  }


/**
 * loescht den gesamten Inhalt der Etransition
 */
  void clear() {
  	
// loeschen der TransitionList
    if (translist != null) {
      translist.head = null;
      translist.next = null;
      translist = null;
    }
    
// loeschen der Transition
    if (transition != null) {
      transition = null;
    }

// loeschen des Rests
    sourcestate = null;
    targetstate = null;
    next = null;
    last = null;
  } // end clear()

/**
 * liefert eine komplette Kopie der Transition als EditorObject
 */  
  EditorObject copy () {
    Etransition outtransition = new Etransition (sourcestate, targetstate, einterface.copyLabel(transition.lab));
    return(outtransition);
  } // end copy()

  void cut (Eprocess eprocess) {
    eprocess.removeTransition(this);
  }

  void paste (Eprocess eprocess) {}


/**
 * Move: Da die Koordinaten zur Zeit nur von den Zustaenden abhaengen
 * brauchen wir hier nichts machen.
 */
  void move (float x, float y) {}
  
  void print () {
    String sname;
    String tname;
    if (getSource() != null) sname = getSource().getName();
      else sname = "error!!";
    if (getTarget() != null) tname = getTarget().getName();
      else tname = "error!!";
    System.out.println("Transition : source = "+sname+" target = "+tname);
  }

  boolean isInRectangle (float x1, float y1, float x2, float y2) {
    boolean outvalue = false;
    if (sourcestate.isInRectangle(x1, y1, x2, y2) && targetstate.isInRectangle(x1, y1, x2, y2)) outvalue = true;
    return (outvalue);
  }
  
  EditorSelection rangeSelect (float x1, float y1, float x2, float y2) {
    EditorSelection outselection = null;
    if (isInRectangle(x1, y1, x2, y2)) {
      select();
      outselection = new EditorSelection();
      outselection.editorobject = this;
      if (next != null) outselection.next = next.rangeSelect(x1, y1, x2, y2);
    } else {
      if (next != null) outselection = next.rangeSelect(x1, y1, x2, y2);
    }
    return (outselection);
  }

/**
 * liefert die Etransition zurueck, die die uebergebene absynt.Transition kapselt
 */
  Etransition getEtransitionWithTransition (absynt.Transition intransition) {
    Etransition outtransition = null;
    if (getTransition() == intransition) outtransition = this;
    else {
      if (next != null) outtransition = next.getEtransitionWithTransition(intransition);
    }
    return(outtransition);
  }

  Etransition getTransitionInRange(float x, float y, float range) {
    Etransition outtransition = null;
      float x1_cor = sourcestate.getX();
      float y1_cor = sourcestate.getY();
      float x2_cor = targetstate.getX();
      float y2_cor = targetstate.getY();
      
      float x_cor = (x1_cor + x2_cor) / 2;
      float y_cor = (y1_cor + y2_cor) / 2;
      
/*      if ((((x-range) < x_cor) && ((x+range) > x_cor)) && (((y-range) < y_cor) && ((y+range) > y_cor))) outstate = this;
        else if (next != null) outstate = next.getStateInRange(x, y, range);
      if (outstate != null) {
      	System.out.println("state in Range");
      	Estate statesucher = null;
      	if (outstate.next != null) statesucher = outstate.next.getStateInRange(x, y, range);
      	if (statesucher != null) outstate = statesucher;
      }
*/
      if (next != null) outtransition = next.getTransitionInRange(x, y, range);
      if (outtransition == null) {
      	if ((((x-range) < x_cor) && ((x+range) > x_cor)) && (((y-range) < y_cor) && ((y+range) > y_cor))) {
          System.out.println("Transition in Range");
          outtransition = this;
        }
      }
    return(outtransition);
  }

/**
 * schaltet highlighted an und aus
 */
    void setHighlighted(boolean mode) {
      highlighted = mode;
    }
}
