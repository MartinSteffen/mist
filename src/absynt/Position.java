package absynt;
import java.io.Serializable;


/**
 * Position, fuer die Graphische Darstellung. Momentan x/y Koordinaten
 *
 * @author Initially provided by Martin Steffen.
 * @version $Id: Position.java,v 1.4 2000-07-16 20:59:00 unix01 Exp $
 */


public class Position implements Serializable{
  public float x;
  public float y;

  public Position( float x, float y ) {
    this.x=x; this.y=y;
  }

  public Position() {
  }

  /**
  * tests, if the two positions are near each other
  * 
  * @param otherPosition the other position
  */

  public boolean equalsn ( Position otherPosition ) {
    return ( java.lang.Math.abs(otherPosition.x-x)<0.001f && java.lang.Math.abs(otherPosition.y-y)<0.001f );
  }

}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Position.java,v 1.4 2000-07-16 20:59:00 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  2000/07/10 08:39:05  unix01
//	Ich kann diese Konstruktoren gebrauchen. Sollte sonst keine Probleme geben.
//
//	Revision 1.2  2000/07/02 15:52:30  unix01
//	Helge Kraas: es fehlte: implements Serializable, kein Problem, hab ich nachgeholt
//
//	Revision 1.1  2000/06/19 17:06:44  unix01
//	Die abstrakte Syntax angepa"st (gem"a"s den heutigen Entscheidungen)
//
//
//		1) Positionen f"ur Zust"ande eingef"uhrt
//		2) Zustandsliste + Initialer Zustand in Prozessen extra gespeichert.
//
//
//---------------------------------------------------------------------

