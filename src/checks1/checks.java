
/**
 * Title:        *M*I*S*T*<p>
 * Description:  <p>
 * Copyright:    Copyright (c) André Nitsche<p>
 * Company:      <p>
 * @author André Nitsche
 * @version 1.0
 */
package Checks1;
import absynt.*;

public class checks {

  public checks() {
  }

  public String ChannelCheck(Program p) {
    /* Diese Methode testet, ob die im Programm p benutzten Channels deklariert
    sind, d.h. in einer ChandecList enthalten sind*/

    //noch nicht implementiert
    return "Es sind keine Fehler aufgetreten\n";
    }

  public String TransitionCheck(Program p) {
  /* Diese Methode testet, ob alle im Programm p vorkommenden Transitionen definiert
  sind.  noch nicht implementiert! */
  return "Es sind keine Fehler aufgetreten\n";}

  public String StateCheck(Program p){
  /* diese Methode testet, ob alle im Programm vorkommenden Zustände auch wirklich
  vorhanden sind. Diese Methode soll also Fehler finden, die evtl. beim Löschen
  von Transitionen entstehen können. Noch nicht implementiert!*/
  return "Es sind keine Fehler aufgetreten\n";}
}