/**
 * Title:        *M*I*S*T*<p>
 * Description:  Diese Version gibt in start_check() eine boolean Variable als Ergebnis zurueck. Bei TRUE trat kein Fehler auf, bei FALSE trat irgendwo ein Fehler auf. Welcher Fehler genau das ist, wird in den einzelnen Checks abgefragt und in einem Fenster ausgegeben
	18.7.2000
 * Copyright:    Copyright (c) AndrÈ Nitsche<p>
 * Company:      <p>
 * @author AndrÈ Nitsche
 * @version $Id: checks.java,v 1.5 2000-07-19 11:59:09 unix01 Exp $
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

//----------------------------------------------------------------------
//	Checker for Mist Programs
//	------------------------------------
//
//	$Id: checks.java,v 1.5 2000-07-19 11:59:09 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  2000/07/19 11:48:55  unix01
//	Version, wie von AndrÅÈ zugemailt. Ich habe keinen
//	Test durchgefÅ¸hrt. [Steffen]
//	
//	
//	
//---------------------------------------------------------------------
















