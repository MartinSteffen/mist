
/**
 * Title:        <p>
 * Description:  Diese Klasse fuehrt Tests mit Zustaenden durch
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package checks1;
import absynt.*;

public class stateCheck {
static checkWindow anzeige=new checkWindow("State-Check\n");
  public stateCheck() {
  anzeige.setLocation(500,0);
  anzeige.show();
  }

public static boolean start_check(AstateList al,TransitionList tl){
  boolean fehler=true;  //Fehlerflag = kein Fehler
  String error="State-Check:\n";
  anzeige.text.append(error);
  TransitionList tl_dummy=tl;
  while(tl_dummy.hasMoreElements())  {
    Astate s_dummy=tl_dummy.head.source;
    if(!inList(al,s_dummy))
          {
          anzeige.text.append("(Source) "+s_dummy.name+" ist nicht in State-Liste..\n");
          fehler=false;} //Fehlerflag setzen
    s_dummy=tl_dummy.head.target;
    if(!inList(al,s_dummy))
          {anzeige.text.append("(Target) "+s_dummy.name+" ist nicht in State-Liste..\n");
          fehler=false;}  //Fehlerflag setzen
    tl_dummy=(TransitionList)tl_dummy.nextElement();
  }
  //Sonderbehandlung fuer letztes Listenelement
  if(tl_dummy.nextElement()==null)
  {
   Astate s_dummy=tl_dummy.head.source;
   if(!inList(al,s_dummy))
           {anzeige.text.append("(Source) "+s_dummy.name+" ist nicht in State-Liste..\n");
           fehler=false;} //Fehlerflag setzen
    s_dummy=tl_dummy.head.target;
    if(!inList(al,s_dummy))
          {anzeige.text.append("(Target) "+s_dummy.name+" ist nicht in State-Liste..\n");
          fehler=false;}  //Fehlerflag setzen
  }
 anzeige.text.append(""+fehler);
 return fehler;
}

private static boolean inList(AstateList al,Astate s){
 boolean state=true;
 AstateList al_dummy=al;
 while(al_dummy.hasMoreElements())
  {if(s==al_dummy.head)
      state=false;
    al_dummy=(AstateList)al_dummy.nextElement();
  }
 //Sonderbehandlung fuer letztes Listenelement
 if(al_dummy.nextElement()==null)
  if(s==al_dummy.head)
      state=false;

  return !state;
}//Ende inList



}