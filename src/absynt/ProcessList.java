package absynt;
import java.io.Serializable;


/**
 * Liste fuer Prozesse.
 * Vergleiche auch die Kommentare zur Klasse VardecList.
 * @author Initially provided by Martin Steffen.
 * @version $Id: ProcessList.java,v 1.1 2000-05-28 15:11:17 unix01 Exp $
 */


public class ProcessList extends Absyn implements java.util.Enumeration { 
  ProcessList next;
  Process     head;

  /**
   * Ein Konstruktor mit Location is hier ueberfluessig
   */
  public ProcessList(Process p, ProcessList pl) {
    head = p;
    next = pl;
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
//	$Id: ProcessList.java,v 1.1 2000-05-28 15:11:17 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	
//---------------------------------------------------------------------

