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
private boolean ok_l, in, checkok,ok_pr, ok_p;
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

    return LabelHandler.checkExpr(_l, _m); // Rueckgabewert.

  }



private VardecList after_trans(Label l, Modstate m){
  VardecList var;
  var=m.vars;
/**  try {
    var= LabelHandler.accomplishAction(l,m);
  } catch (NumberFormatException nfr){
    throw nfr;}
  */ 
  
  return var;
}

private boolean in_list(ModstateList ml, Modstate m) {
 boolean in;
  Modstate mstate, liststate;
  ModstateList mlist;
System.out.println("begin inlist");
  in=false;
if (ml!=null)
{
  mlist=ml;
  mstate=m;
  if (mlist.head !=null) {
    
  liststate=mlist.head;
System.out.println("liststate=mlist.head");
  
  if (liststate.vars==mstate.vars && liststate.state==mstate.state)
  { in=true;}
  
System.out.println("in=true");
}

  while (mlist.hasMoreElements()==true && in==false)
  {
    mlist=mlist.next;
    liststate=mlist.head;
    if (liststate.vars==mstate.vars && liststate.state==mstate.state)
    { in=true;}
  }
  }
else System.out.println("Liste leer");
if (in==false) System.out.println("Zustand nicht in Liste vorhanden");
else System.out.println("Zustand in Liste vorhanden");
  return in;
}

private boolean check_state(Modstate m ) {
  Modstate mstate;
  boolean ok;
  mstate=m;
  ok=true;
  
  return ok;
}
  

private boolean check_process(Process pr) {
  boolean ok;
  int zaehler;
  Process p;
  zaehler=0;
  ok=true;
  p=pr;
  init=p.init; System.out.println("InitState übergeben");
vars =p.vars;
steps=p.steps;
states=p.states;

modstate=new Modstate(init,vars);

modstates = new ModstateList(modstate,null);
next=modstates;
start=modstates;
modstate=next.head;
checkok=check_state(modstate); System.out.println("1. Zustand gecheckt");
if (checkok==false) {
// Behandlung falls der Zustand nicht korrekt ist  
}
if (steps==null) System.out.println("akt==null");
akt=steps;
if( akt.head==null) System.out.println("akt.head==null");
step=akt.head;   // Betrachte Zustand und zugehoerige Transitionen
if(step !=null && modstate.state==step.source) 
System.out.println("1. Transition");
{ 
    label=step.lab; 
ok_l=check_label(label,modstate);

    // Behandlung des Labels
if (ok_l==true)
{
  System.out.println("Label ok");
  vars=after_trans(label,modstate);
  System.out.println("after_trans ok");
    nextstate=new Modstate(step.target,vars);
   in=in_list(start,nextstate);
  System.out.println("inlist ok");
    if(in==false)
    {
      modstates.next= new ModstateList(nextstate,null);
    modstates=modstates.next;
    System.out.println("in Modstatelist eingefügt");
  }
  }
} //Ende if



while (akt.hasMoreElements()==true) {
  System.out.println("while");
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
    modstates.next= new ModstateList(nextstate,null);
    modstates=modstates.next;
    System.out.println("while in ModstateListe eingefügt");
  }
  }
  } //Ende if

}


while ((next.hasMoreElements())==true && (zaehler <50))
    {
      System.out.println("Neuer Zustand betrachtet");
next=next.next;
modstate=next.head;
zaehler++;
checkok=check_state(modstate);
if (checkok==false) {
  // Behandlung falls Modstate nicht korrekt
}

akt=steps;
step=akt.head;   // Betrachte Zustand und zugehoerige Transitionen
if(step !=null && modstate.state==step.source) 

{ 
System.out.println("Transition vorhanden");
    label=step.lab; 
ok_l=check_label(label, modstate);
    // Behandlung des Labels
if(ok_l==true)
{
System.out.println("Label ok");
  vars=after_trans(label,modstate);
    nextstate=new Modstate(step.target,vars);
   in=in_list(start,nextstate);
    if(in==false)
    {
      modstates.next=new ModstateList(nextstate,null);
      
    modstates=modstates.next;
    System.out.println(" neuer Modstate in Liste eingefügt");
  }
  }
} //Ende if

else System.out.println("ausgehende Transition hat nicht Modstate als begin");


while (akt.hasMoreElements()==true) {
System.out.println("Transition vorhanden");
    akt=akt.next;
    step=akt.head;
if(modstate.state==step.source) 
    { label=step.lab;  // Zustand und zug. Trans.
System.out.println("ausgehende Transition hat modstate als begin");
ok_l=check_label(label, modstate);
    // Behandlung des Labels
if (ok_l==true)
{
  vars=after_trans(label,modstate);
nextstate=new Modstate(step.target,vars);
   in=in_list(start,nextstate);
    if(in==false)
    {
      modstates.next= new ModstateList(nextstate,null);
  modstates=modstates.next;
    System.out.println("neuer Modstate in Liste eingefügt");
  }
  }
} //Ende if
else System.out.println("aus gehende Transition hat modstate nicht als begin");

} //Ende while
System.out.println("keine weitere ausgehende Transition vorhanden");

    } // Ende while
 
    
  return ok;
}

private boolean check_program(Program prog) {
  boolean ok;
  Program p;
  ok=true;
p=prog;
pl=p.procs;
pr=pl.head;
ok=check_process(pr);

    // checke die weiteren Processe
while (pl.hasMoreElements()==true && ok==true){
  System.out.println("Neuer Process betrachtet");
  pl=pl.next;
pr=pl.head;
ok=check_process(pr);

  // kopiere hierhin das Checken des 1. Processes
  
  
	// hier kommt der Code `rein.
}
 
  return ok;
}


    public boolean start_modcheck (Program p)
	throws MCheckException {
boolean ok;
ok=check_program(p);

return ok;


    } // method start_modcheck


} // class Modelchecker















