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
private boolean ok_l;
private TransitionList steps, akt;  
private AstateList states;   // Liste uebergebenen, erreichten
private ModstateList modstates, next;  // und zu betrachtenden Zustaende
private Modstate  nextstate,modstate;
private Transition step;		// gerade zu betrachtende Transition
private Label label;			// gerade zu betrachtendes Label    
/**      * Methode zum Starten des Modelcheckers
     * @param Program p
     */

private boolean check_label(Label l, Modstate m) { // ueberprüft, ob Transition benutzt werden kann
Modstate mod;
Label lab;
boolean ok;
ok=true;
mod=m;
lab=l;
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

akt=steps;
step=akt.head;   // Betrachte Zustand und zugehoerige Transitionen
if(step !=null && modstate.state==step.source) 

{ 
    label=step.lab; 
ok_l=check_label(label,modstate);
    // Behandlung des Labels
if (ok_l==true)
{
    nextstate=new Modstate(step.target,vars);
    modstates=modstates.next;
    modstates.head=nextstate;
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
  
    nextstate=new Modstate(step.target,vars);
    modstates=modstates.next;
    modstates.head=nextstate;
  }
  } //Ende if

}


while (next.hasMoreElements()==true)
    {
      System.out.println("Neuer Zustand betrachtet");
next=next.next;
modstate=next.head;

akt=steps;
step=akt.head;   // Betrachte Zustand und zugehoerige Transitionen
if(step !=null && modstate.state==step.source) 

{ 
    label=step.lab; 
ok_l=check_label(label, modstate);
    // Behandlung des Labels
if(ok_l==true)
{
    nextstate=new Modstate(step.target,vars);
    modstates=modstates.next;
    modstates.head=nextstate;
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
  
nextstate=new Modstate(step.target,vars);
    modstates=modstates.next;
    modstates.head=nextstate;
  }
} //Ende if

} //Ende while

    } // Ende while
 
if(pl.hasMoreElements()==true) pl=pl.next;



	// hier kommt der Code `rein.

    } // method start_modcheck


} // class Modelchecker















