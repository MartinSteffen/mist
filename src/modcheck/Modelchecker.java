/**
 * Modelchecker-Klasse.
 * @author Frank Neumann, Aneta Kuemper, Eike Schulz
 * @version 1.0
 */
package modcheck;

import absynt.*;
import absynt.Process;

public class Modelchecker {
private Initstate init; //Anfangszustand
private ProcessList pl; // List der Prozesse
private Process pr;
private VardecList vars;
private boolean ok_l, in, checkok;
private TransitionList steps, akt;  
private AstateList states;   // Liste uebergebenen, erreichten
private ModstateList modstates, next, start;  // und zu betrachtenden Zustaende
private Modstate  nextstate,modstate;
private Transition step;		// gerade zu betrachtende Transition
private Label label;			// gerade zu betrachtendes Label    
/**      * Methode zum Starten des Modelcheckers
     * @param Program p
     */

  private boolean check_label(Label _l, Modstate _m) {

    // ueberprüft, ob Transition benutzt werden kann

    boolean  ok  = true; // Rueckgabewert.

    return ok;
  }


  // --------------- ^ Felder und Methoden fuer "check_label" ^ ---------------


private VardecList after_trans(Label l, Modstate m) {
  Modstate mod;
  Label lab;
  mod=m;
  lab=l;
  VardecList varlist;
  varlist=mod.vars;
  return varlist;
}

private boolean in_list(ModstateList ml, Modstate m) {
  boolean in;
  Modstate mstate, liststate;
  ModstateList mlist;
  in=false;
  mlist=ml;
  mstate=m;
  liststate=mlist.head;
  if (liststate.vars==mstate.vars && liststate.state==mstate.state)
  { in=true;}
  
  while (mlist.hasMoreElements()==true && in==false)
  {
    mlist=mlist.next;
    liststate=mlist.head;
    if (liststate.vars==mstate.vars && liststate.state==mstate.state)
    { in=true;}
  }
  return in;
}

private boolean check_state(Modstate m ) {
  Modstate mstate;
  boolean ok;
  mstate=m;
  ok=true;
  
  return ok;
}
  

    public void start_modcheck (Program p)
	throws MCheckException {

pl=p.procs;
pr=pl.head;
init=pr.init;
vars =pr.vars;
steps=pr.steps;
states=pr.states;



modstate=new Modstate(init,vars);

modstates = new ModstateList(modstate,null);
next=modstates;

modstate=next.head;
checkok=check_state(modstate);
if (checkok==false) {
// Behandlung falls der Zustand nicht korrekt ist  
}

akt=steps;
step=akt.head;   // Betrachte Zustand und zugehoerige Transitionen
if(step !=null && modstate.state==step.source) 

{ 
    label=step.lab; 
ok_l=check_label(label,modstate);

    // Behandlung des Labels
if (ok_l==true)
{
  vars=after_trans(label,modstate);
    nextstate=new Modstate(step.target,vars);
   in=in_list(start,nextstate);
    if(in==false)
    {
    modstates=modstates.next;
    modstates.head=nextstate;
  }
  }
} //Ende if



while (akt.hasMoreElements()==true) {
    akt=akt.next;
    step=akt.head;
if(modstate.state==step.source) 
    { label=step.lab;  // Zustand und zug. Trans.
ok_l=check_label(label, modstate);
    // Behandlung des Labels

if(ok_l==true) {
  vars=after_trans(label,modstate);
    nextstate=new Modstate(step.target,vars);
    in=in_list(start,nextstate);
    if(in==false)
    {
    modstates=modstates.next;
    modstates.head=nextstate;
  }
  }
  } //Ende if

}


while (next.hasMoreElements()==true)
    {
      System.out.println("Neuer Zustand betrachtet");
next=next.next;
modstate=next.head;

checkok=check_state(modstate);
if (checkok==false) {
  // Behandlung falls Modstate nicht korrekt
}

akt=steps;
step=akt.head;   // Betrachte Zustand und zugehoerige Transitionen
if(step !=null && modstate.state==step.source) 

{ 
    label=step.lab; 
ok_l=check_label(label, modstate);
    // Behandlung des Labels
if(ok_l==true)
{
  vars=after_trans(label,modstate);
    nextstate=new Modstate(step.target,vars);
   in=in_list(start,nextstate);
    if(in==false)
    {
    modstates=modstates.next;
    modstates.head=nextstate;
  }
  }
} //Ende if



while (akt.hasMoreElements()==true) {
    akt=akt.next;
    step=akt.head;
if(modstate.state==step.source) 
    { label=step.lab;  // Zustand und zug. Trans.
ok_l=check_label(label, modstate);
    // Behandlung des Labels
if (ok_l==true)
{
  vars=after_trans(label,modstate);
nextstate=new Modstate(step.target,vars);
   in=in_list(start,nextstate);
    if(in==false)
    {
    modstates=modstates.next;
    modstates.head=nextstate;
  }
  }
} //Ende if

} //Ende while

    } // Ende while
 
    
    // checke die weiteren Processe
while (pl.hasMoreElements()==true){
  System.out.println("Neuer Process betrachtet");
  pl=pl.next;

  // kopiere hierhin das Checken des 1. Processes
  
  
	// hier kommt der Code `rein.
}
    } // method start_modcheck


} // class Modelchecker















