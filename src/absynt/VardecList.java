package absynt;
import java.io.Serializable;


/**
 * Liste fuer Variablen-Deklarationen.
 * Zum standesgemaessen Iterieren implementiert die
 * Klasse das entsprechende Interface. (In Java.1.2 wird Iterator
 * dem Enumeration-Interface vorgezogen. Aber Loeschen in der
 * Liste brauchen wir vermutlich nicht.
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: VardecList.java,v 1.2 2000-06-04 12:11:55 unix01 Exp $
 */


public class VardecList  extends Absyn implements java.util.Enumeration { 
  VardecList next;
  Vardec     head;

  /**
   * Ein Konstruktor mit Location ist  ueberfluessig.
   */
  public VardecList(Vardec d, VardecList dl) {
    head = d;
    next = dl;
  }

    
  //---------------------------------------------
  public boolean hasMoreElements() {
    return next != null;
  };
  public Object nextElement() {
    return  next;
  };
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: VardecList.java,v 1.2 2000-06-04 12:11:55 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/05/28 15:11:17  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

