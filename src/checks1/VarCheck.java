
/**
 * Title:        <p>
 * Description:  Diese Klasse fuehrt Checks ueber die Variablen eines Programmes
 * durch
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package checks1;
import absynt.*;

public class VarCheck {
  static checkWindow anzeige=new checkWindow("Variablen-Check");

  public VarCheck() {
  anzeige.setLocation(250,0);
  anzeige.show();
  }

  public static boolean start_check(VardecList vl,TransitionList tl) {
  /* Diese Methode testet, ob Alle in I/O-Actions genutzten Variablen deklariert
  wurden*/
  boolean fehler=true;  //Fehlerflag = kein Fehler
  String error="Var-Check:\n "; //Testergebnis
  anzeige.text.append(error);
  TransitionList tl_dummy=tl;  //Transitionsliste zwischenspeichern
  while (tl_dummy.hasMoreElements())  //solange nicht Ende der Transitionsliste
    {
    Transition tr=tl_dummy.head;  //extrahiere Transition
    if (tr.lab.act instanceof Input_action) /*ist Input_action enthalten, dann
      extrahiere darin benutzte Variablen und pruefe, ob sie in VardecList sind*/
      { Input_action ia_dummy2=(Input_action)tr.lab.act;
         if(!inList(vl,ia_dummy2.var))
          {anzeige.text.append("(Input) "+ia_dummy2.var.name+" ist nicht in Var-Liste..\n");
          fehler=false;} //Fehlerflag setzen
      }
      tl_dummy=(TransitionList)tl_dummy.nextElement();
    }
  //Sonderbehandlung fuer letztes Element
   if(tl_dummy.nextElement()==null)
    {
    Transition tr=tl_dummy.head;  //extrahiere Transition
    if (tr.lab.act instanceof Input_action)
      { Input_action ia_dummy2=(Input_action)tr.lab.act;
         if(!inList(vl,ia_dummy2.var))
          {anzeige.text.append("(Input) "+ia_dummy2.var.name+" ist nicht in Var-Liste..\n");
          fehler=false;} //Fehlerflag setzen
      }
     }
  anzeige.text.append(""+fehler);
      return fehler;
  }//Ende start_check

 private static boolean inList(VardecList vl,Variable x){
 /*private Methode zum Scannen einer VardecList nach der Variablen x*/
 boolean state=true;
 VardecList dummy=vl;
 while(dummy.hasMoreElements())
  {if(x==dummy.head.var)
      state=false;
    dummy=(VardecList)dummy.nextElement();
  }
 if(dummy.nextElement()==null)
  if(x==dummy.head.var)
      state=false;
  return !state;
}//Ende inList

}
