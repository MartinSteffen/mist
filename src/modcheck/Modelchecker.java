package modcheck;

import absynt.*;

import absynt.Process;


/**
 * Modelchecker-Klasse.
 *
 * @author Initially provided by Frank Neumann, Aneta Kuemper, Eike Schulz
 * @version $Id: Modelchecker.java,v 1.6 2000-07-17 13:01:25 unix01 Exp $
 */




/**
 * Modelchecker-Klasse.
 * es besteht aus u.a. Anfangszustand -- Initstate
 *    --"--            Liste der Prozesse  -- ProcessList
 *    --"--            Liste uebergebenen, erreichten Zustaende -- AstateList
 *    --"--            Liste   zu betrachtenden Zustaende  -- ModstateList 
 *    --"--            gerade zu betrachtende Transition  -- Transition
 *    --"--            gerade zu betrachtendes Label -- Label
 */

public class Modelchecker {

       private Initstate init;                            //Anfangszustand
       private ProcessList pl;                            // Liste der Prozesse
       private Process pr;
       private VardecList vars;
       private boolean ok_l, in, checkok,ok_pr, ok_p;
       private TransitionList steps, akt;  
       private AstateList states;                         // Liste uebergebenen, erreichten
       private ModstateList modstates, next, start;       // und zu betrachtenden Zustaende
       private Modstate  nextstate,modstate;
       private Transition step;		                  // gerade zu betrachtende Transition
       private Label label;			          // gerade zu betrachtendes Label  

  
/**     
 * Methode zum Starten des Modelcheckers
 * @param Program p
 * ueberprüft, ob Transition benutzt werden kann
 */

  private boolean check_label(Label _l, Modstate _m) {

          return LabelHandler.checkExpr(_l, _m);          // Rueckgabewert.

  }



   private VardecList after_trans(Label l, Modstate m){
           VardecList var;
            var=m.vars;
   try {
        var= LabelHandler.accomplishAction(l,m);
   }    catch (NumberFormatException nfr){
        throw nfr;}
   
        return var;
  }

  private boolean in_list(ModstateList ml, Modstate m) {
          boolean in;
          Modstate mstate, liststate;
          ModstateList mlist;

          System.out.println("begin inlist");
          in=false;
            if (ml!=null)  {
                 mlist=ml;
                  mstate=m;
               if (mlist.head !=null) {
                   liststate=mlist.head;

          System.out.println("liststate=mlist.head");
  
             if (liststate.vars==mstate.vars && liststate.state==mstate.state)  {
                 in=true;}
  
          System.out.println("in=true");
  }

          while (mlist.hasMoreElements()==true && in==false)  {
                 mlist=mlist.next;
                  liststate=mlist.head;
          if (liststate.vars==mstate.vars && liststate.state==mstate.state)  { 
              in=true;
     }
   }
  }

          else System.out.println("Liste leer");
            if (in==false) System.out.println("Zustand nicht in Liste vorhanden");
               else System.out.println("Zustand in Liste vorhanden");
          return in;
  }

  private boolean check_state(Modstate _m ) { 
          Label _l;
          _l=new Label(_m.state.assert, null);
          return LabelHandler.checkExpr(_l, _m); 
          // return true;
  }
  

  private boolean check_process  (Process pr) throws MCheckException {
          boolean ok;
           Astate fstate[]= new Astate[1];
            int zaehler;
             Process p;
              zaehler=0;
              ok=true;
               p=pr;
                init=p.init; 

          System.out.println("InitState übergeben");
          vars =p.vars;
           steps=p.steps;
            states=p.states;

          modstate=new Modstate(init,vars);

          modstates = new ModstateList(modstate,null);
           next=modstates;
            start=modstates;
             modstate=next.head;
              checkok=check_state(modstate); 

          System.out.println("1. Zustand gecheckt");
          System.out.println(modstate.state.name);
          if (checkok==false) {
          fstate[0]=modstate.state;

          // Behandlung falls der Zustand nicht korrekt ist  
          throw new MCheckException("Assertion verletzt", fstate);
 }
          else System.out.println("Zustand ok");
            if (steps==null) System.out.println("akt==null");
                akt=steps;
                    if( akt.head==null) System.out.println("akt.head==null");
                        step=akt.head;   
           // Betrachte Zustand und zugehoerige Transitionen

           if(step !=null && modstate.state==step.source) 
           System.out.println("1. Transition");  { 
           label=step.lab; 
           ok_l=check_label(label,modstate);

           // Behandlung des Labels
           if (ok_l==true)  {

           System.out.println("Label ok");
           vars=after_trans(label,modstate);

           System.out.println("after_trans ok");
           nextstate=new Modstate(step.target,vars);
           in=in_list(start,nextstate);

           System.out.println("inlist ok");
           if(in==false)  {
              modstates.next= new ModstateList(nextstate,null);
               modstates=modstates.next;

           System.out.println("in Modstatelist eingefügt");
   }
  }
 } //Ende if



          while (akt.hasMoreElements()==true)  {

          System.out.println("while");
          akt=akt.next;
          step=akt.head;
          if(modstate.state==step.source)  {
             label=step.lab;  
          // Zustand und zug. Trans.
          ok_l=check_label(label, modstate);
          // Behandlung des Labels

          if(ok_l==true)  {
             vars=after_trans(label,modstate);
              nextstate=new Modstate(step.target,vars);
               in=in_list(start,nextstate);
          if(in==false)  {
             modstates.next= new ModstateList(nextstate,null);
              modstates=modstates.next;

          System.out.println("while in ModstateListe eingefügt");
     }
    }
   } //Ende if

 }


         while (((next.hasMoreElements())==true) && (zaehler <20))  {

         System.out.println("Neuer Zustand betrachtet");
         next=next.next;
         modstate=next.head;
         System.out.println(modstate.state.name);
         zaehler++;
         checkok=check_state(modstate);
         if (checkok==false)  {
         // Behandlung falls Modstate nicht korrekt
         fstate[0]=modstate.state;
         throw new MCheckException("Assertion verletzt", fstate);

  }
         else System.out.println("Zustand ok");

         akt=steps;
         step=akt.head;   

         // Betrachte Zustand und zugehoerige Transitionen

         if(step !=null && modstate.state==step.source)  { 

         System.out.println("Transition vorhanden");
         label=step.lab; 
         ok_l=check_label(label, modstate);

         // Behandlung des Labels

         if(ok_l==true)  {

         System.out.println("Label ok");
         vars=after_trans(label,modstate);
         nextstate=new Modstate(step.target,vars);
         in=in_list(start,nextstate);
         if(in==false)  {
         modstates.next=new ModstateList(nextstate,null);
         modstates=modstates.next;
         System.out.println(" neuer Modstate in Liste eingefügt");
   }
  }
 } //Ende if

         else System.out.println("ausgehende Transition hat nicht Modstate als begin");


         while (akt.hasMoreElements()==true)  {

         System.out.println("Transition vorhanden");
         akt=akt.next;
         step=akt.head;
         if(modstate.state==step.source)  { 
         label=step.lab;  

         // Zustand und zug. Trans. 

         System.out.println("ausgehende Transition hat modstate als begin");
         ok_l=check_label(label, modstate);

         // Behandlung des Labels

         if (ok_l==true)  {
         vars=after_trans(label,modstate);
         nextstate=new Modstate(step.target,vars);
         in=in_list(start,nextstate);
         if(in==false)  {
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

  private boolean check_program(Program prog) throws MCheckException{
          boolean ok;
          Program p;
          Astate error;
          ok=true;
          p=prog;
          pl=p.procs;
          pr=pl.head;
      try{
          ok=check_process(pr);
         }
      catch (MCheckException m) {
             throw m;
  }

    // checke die weiteren Processe

         while (pl.hasMoreElements()==true && ok==true)  {

         System.out.println("Neuer Process betrachtet");
         pl=pl.next;
         pr=pl.head;
         ok=false;
     try{
         ok=check_process(pr);
        }
     catch (MCheckException m) {
     throw m;
 } 
 
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


//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
















