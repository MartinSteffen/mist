
/**
 * Title:        <p>
 * Description:  Diese Klasse fuehrt Checks ueber die Channels eines Programmes
 * durch.
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package checks1;
import absynt.*;

public class channelCheck {
  static checkWindow anzeige=new checkWindow("Channel-Check");  //Message-Frame

  public channelCheck() {
    anzeige.show();
    anzeige.setLocation(0,0);
  }

 public static boolean start_check(ChandecList cl, TransitionList tl)
 {
  //Test, welche deklarierten Channels nicht verwendet werden
  ChandecList dum_cl=cl;
  while(dum_cl.hasMoreElements())
  {
    if(inTList(dum_cl.head.chan,tl))
      anzeige.text.append("(Input)"+dum_cl.head.chan.name+" Wird nicht verwendet..\n");
    dum_cl=(ChandecList)dum_cl.nextElement();
        }
  //Test auf Deklaration
  return chan_dekl(cl,tl);
  }

 public static boolean chan_dekl(ChandecList cl,TransitionList tl) {
 /* Diese Methode testet, ob die in I/O-Actions verwendeten Channels auch
 in der Channel-Liste sind*/
  boolean fehler=true;  //kein Fehler aufgetreten
  String error="Channel-Check:\n ";
  anzeige.text.append(error);
  TransitionList dummy=tl;
  while (dummy.hasMoreElements())
    {
    Transition tr=dummy.head;
    if (tr.lab.act instanceof Input_action)
      { Input_action dumy2=(Input_action)tr.lab.act;
         if(!inList(cl,dumy2.chan))
         {
          anzeige.text.append("(Input) "+dumy2.chan.name+" ist nicht in Channel-Liste..\n");
          fehler=false;
          }
      }
    if (tr.lab.act instanceof Output_action)
      { Output_action dumy3=(Output_action)tr.lab.act;
        if(!inList(cl,dumy3.chan))
          {
          anzeige.text.append("(Output) "+dumy3.chan.name+" ist nicht in Channel-Liste..\n");
          fehler=false;
          }
      }
    dummy=(TransitionList)dummy.nextElement();
    }
   //Sonderbehandlung fuer letztes Listenelement
   if(dummy.nextElement()==null)
    {   Transition tr=dummy.head;
        if (tr.lab.act instanceof Input_action)
          { Input_action dumy2=(Input_action)tr.lab.act;
            if(!inList(cl,dumy2.chan))
              {anzeige.text.append("(Input) "+dumy2.chan.name+" ist nicht in Channel-Liste..\n");
              fehler=false;
              }
          }
        if (tr.lab.act instanceof Output_action)
          { Output_action dumy3=(Output_action)tr.lab.act;
            if(!inList(cl,dumy3.chan))
            {anzeige.text.append("(Output) "+dumy3.chan.name+" ist nicht in Channel-Liste..\n");
            fehler=false;
            }
          }
     }
  anzeige.text.append(""+fehler);
  return fehler;
}//Ende ChannelCheck()

 private static boolean inTList(Channel ch, TransitionList tl){
 boolean state=true;
 TransitionList dummy=tl;
 while (dummy.hasMoreElements())
 { if (dummy.head.lab.act instanceof Input_action)
    { Input_action ia=(Input_action)dummy.head.lab.act;
      if(ia.chan==ch)
       state=false;
     }
    dummy=(TransitionList)dummy.nextElement();
        } //Ende if..

 return state;
 }//Ende inTList(..)

 private static boolean inList(ChandecList cl,Channel ch){
 boolean state=true;
 ChandecList dummy=cl;
 while(dummy.hasMoreElements())
  {if(ch==dummy.head.chan)
      state=false;
    dummy=(ChandecList)dummy.nextElement();
  }
 //Sonderbehandlung fuer letztes Element
 if(dummy.nextElement()==null)
   if(ch==dummy.head.chan)
      state=false;
  return !state;
}

}