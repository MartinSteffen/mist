package absynt;
import java.io.Serializable;


/**
 * Eine Liste von Transitionen
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: TransitionList.java,v 1.4 2000-06-26 13:11:44 unix01 Exp $
 */


public class TransitionList  
  extends Absyn 
  implements java.util.Enumeration, Serializable { 
  public Transition     head;
  public TransitionList next;

  public TransitionList (Transition t, TransitionList tl) {
    head = t;
    next = tl;
  }

  //---------------------------------------

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
//	$Id: TransitionList.java,v 1.4 2000-06-26 13:11:44 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  2000/06/04 12:11:55  unix01
//	ok
//	
//	Revision 1.2  2000/05/28 15:00:33  unix01
//	ok
//	
//	Revision 1.1  2000/05/28 13:02:16  unix01
//	Zwischenzustand
//	
//	
//---------------------------------------------------------------------

