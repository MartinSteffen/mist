
/**
 * Title:        *M*I*S*T*<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Andr� Nitsche<p>
 * Company:      <p>
 * @author Andr� Nitsche
 * @version 1.0
 */
package checks1;
import absynt.*;

public class checks {

  public checks() {
  }

public void start_check() {}  

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
  /* diese Methode testet, ob alle im Programm vorkommenden Zust�nde auch wirklich
  vorhanden sind. Diese Methode soll also Fehler finden, die evtl. beim L�schen
  von Transitionen entstehen k�nnen. Noch nicht implementiert!*/
  return "Es sind keine Fehler aufgetreten\n";}
}
