
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

 public static String start_check(ChandecList cl,TransitionList tl) {
 /* Diese Methode testet, ob die in I/O-Actions verwendeten Channels auch
 in der Channel-Liste sind*/
  String error="Channel-Check:\n ";
  anzeige.text.append(error);
  TransitionList dummy=tl;
  while (dummy.hasMoreElements())
    {
    Transition tr=dummy.head;
    if (tr.lab.act instanceof Input_action)
      { Input_action dumy2=(Input_action)tr.lab.act;
         if(!inList(cl,dumy2.chan))
          anzeige.text.append("(Input) "+dumy2.chan.name+" ist nicht in Channel-Liste..\n");

      }
    if (tr.lab.act instanceof Output_action)
      { Output_action dumy3=(Output_action)tr.lab.act;
        if(!inList(cl,dumy3.chan))
          anzeige.text.append("(Output) "+dumy3.chan.name+" ist nicht in Channel-Liste..\n");
      }
    dummy=(TransitionList)dummy.nextElement();
    }
   //Sonderbehandlung fuer letztes Listenelement
   if(dummy.nextElement()==null)
    {   Transition tr=dummy.head;
        if (tr.lab.act instanceof Input_action)
          { Input_action dumy2=(Input_action)tr.lab.act;
            if(!inList(cl,dumy2.chan))
              anzeige.text.append("(Input) "+dumy2.chan.name+" ist nicht in Channel-Liste..\n");
          }
        if (tr.lab.act instanceof Output_action)
          { Output_action dumy3=(Output_action)tr.lab.act;
            if(!inList(cl,dumy3.chan))
            anzeige.text.append("(Output) "+dumy3.chan.name+" ist nicht in Channel-Liste..\n");
          }
     }
  return error;
}//Ende ChannelCheck()
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