package absynt;
import java.io.Serializable;


/**
 * Konstante Werte (Integers und Boolesche Werte)
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Constval.java,v 1.3 2000-06-04 07:39:43 unix01 Exp $
 */


public class Constval  extends Absyn implements Serializable { 
  /**
     Als konstante Werte sind Integers und boolesche Werte.
     Diese uebernehmen wir direkt aus Java.
   */

  public Object val;
  
  /**
   * 2 uberladene Konstruktoren. Gespeichert wird der Wert
   * als Objekt. 
   */
  public Constval (boolean v) {
    val = new Boolean (v);}


  public Constval (int v) {
    val = new Integer (v);}
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Constval.java,v 1.3 2000-06-04 07:39:43 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/05/28 12:57:09  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:23  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

