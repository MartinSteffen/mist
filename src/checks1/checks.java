
/**
 * Title:        *M*I*S*T*<p>
 * Description:  <p>
 * Copyright:    Copyright (c) André Nitsche<p>
 * Company:      <p>
 * @author André Nitsche
 * @version 1.0
 */
package checks1;
import absynt.*;

public class checks {

  ProcessList pl;
  VardecList vl;
  TransitionList tl;
  AstateList al;
  ChandecList cl;


  public checks(Program p) {
  cl=p.chans;
  pl=p.procs;
  absynt.Process proc=pl.head;
  tl=proc.steps;
  vl=proc.vars;
  al=proc.states;
  }

  public checks() {}



  public void start_check() {
   System.out.println("Check gestartet...");
   channelCheck cc=new channelCheck();
   System.out.println(channelCheck.start_check(cl,tl));
   varCheck vc=new varCheck();
   System.out.println(varCheck.start_check(vl,tl));
   stateCheck sc=new stateCheck();
   System.out.println(stateCheck.start_check(al,tl));
 }



}
