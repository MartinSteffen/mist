
/**
 * Title:        *M*I*S*T*<p>
 * Description:  Diese Version gibt in start_check() eine boolean Variable als Ergebnis zurueck. Bei TRUE trat kein Fehler auf, bei FALSE trat irgendwo ein Fehler auf. Welcher Fehler genau das ist, wird in den einzelnen Checks abgefragt und in einem Fenster ausgegeben
	18.7.2000
 * Copyright:    Copyright (c) André Nitsche<p>
 * Company:      <p>
 * @author André Nitsche
 * @version 1.1
 */
package checks1;
import absynt.*;

public class checks {

  ProcessList pl;
  VardecList vl;
  TransitionList tl;
  AstateList al;
  ChandecList cl;
  boolean channelError=true;
  boolean varError=true;
  boolean stateError=true;


  public checks(Program p) {
  if(p.chans.hasMoreElements())
    cl=p.chans;

    pl=p.procs;
    absynt.Process proc=pl.head;
    tl=proc.steps;
    vl=proc.vars;
    al=proc.states;
  }

  public checks() {}



  public boolean start_check() {

   System.out.println("Check gestartet...");

   //sucht Channel - Fehler
   channelCheck cc=new channelCheck();
   if (cl.hasMoreElements() && tl.hasMoreElements())
     channelError=(channelError && channelCheck.start_check(cl,tl));
   //sucht Variablen Fehler
   varCheck vc=new varCheck();
   if(vl.hasMoreElements() && tl.hasMoreElements())
     varError=(varError && varCheck.start_check(vl,tl));
    //sucht Zustands - Fehler
   stateCheck sc=new stateCheck();
   if(al.hasMoreElements() && tl.hasMoreElements())
    stateError=(stateError && stateCheck.start_check(al,tl));

   return (channelError && varError && stateError);
 }



}
