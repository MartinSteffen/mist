package absynt;
import java.io.Serializable;


/**
 * Liste fuer Kanal-Deklarationen.
 * Vergleiche auch die Kommentare zur Klasse VardecList.
 * @author Initially provided by Martin Steffen.
 * @version $Id: ChandecList.java,v 1.2 2000-06-26 13:11:44 unix01 Exp $
 */


public class ChandecList extends Absyn implements java.util.Enumeration { 
  public ChandecList next;
  public Chandec     head;

  /**
   * Ein Konstruktor mit Location is hier ueberfluessig
   */
  public ChandecList(Chandec d, ChandecList dl) {
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
//	$Id: ChandecList.java,v 1.2 2000-06-26 13:11:44 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/05/28 15:11:16  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

